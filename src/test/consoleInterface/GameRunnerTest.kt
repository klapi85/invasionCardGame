package test.consoleInterface

import game.consoleInterface.GameRunner
import game.consoleInterface.GameType
import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.Player
import game.domain.PhaseType
import game.domain.cards.ServantsOfKhorne
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.io.StringReader
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class GameRunnerTest {

    @Test
    fun silentGameShouldNotChangeGame() {
        //given
        val gameRunner = GameRunner(GameType.SILENT)
        val game = Game()

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(false, game.isFinished)
        assertEquals(PhaseType.START, game.phase.currentPhase)
        assertEquals(3, game.player1.hand.cards.size)
    }

    @Test
    fun wrongMovesShouldNotCrashGame() {
        //given
        val moves = listOf("x", "", "p x 0", "p k 9", "p k a", "p k", "n", "q").joinToString("\n")
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader(moves)))
        val game = Game()

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(true, game.isFinished)
        assertEquals(PhaseType.MIDDLE, game.phase.currentPhase)
        assertEquals(3, game.player1.hand.cards.size)
    }

    @Test
    fun endOfInputShouldFinishGame() {
        //given
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader("n\n")))
        val game = Game()

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(true, game.isFinished)
    }

    @Test
    fun notAllowedMoveShouldPrintReason() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Black Dragon", 6, 4, 5),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader("p k 0\nq\n")))

        //when
        val output = captureOutput { gameRunner.runGame(game) }

        //then
        assertTrue(output.contains("Move not allowed: not enough resources to play this card"))
        assertEquals(3, game.player1.hand.cards.size)
    }

    @Test
    fun battleShouldPrintDamageAndWin() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)), Player(2, durability = 1))
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader("p b 0\nn\n")))

        //when
        val output = captureOutput { gameRunner.runGame(game) }

        //then
        assertTrue(output.contains("Durability: Player 1: 40, Player 2: 1"))
        assertTrue(output.contains("Battle! Player 1 units dealt 1 damage to Player 2."))
        assertTrue(output.contains("Player 1 won! Player 2 durability reached 0."))
        assertEquals(game.player1, game.winner)
    }

    @Test
    fun battlefieldOnlyCardShouldPrintReason() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        ServantsOfKhorne(),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader("p k 0\nq\n")))

        //when
        val output = captureOutput { gameRunner.runGame(game) }

        //then
        assertTrue(output.contains("Move not allowed: this card can be played only in the Battlefield area"))
    }

    private fun captureOutput(block: () -> Unit): String {
        val originalOut = System.out
        val buffer = ByteArrayOutputStream()
        System.setOut(PrintStream(buffer))
        try {
            block()
        } finally {
            System.setOut(originalOut)
        }
        return buffer.toString()
    }
}
