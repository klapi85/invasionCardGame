package test.consoleInterface

import game.consoleInterface.GameRunner
import game.consoleInterface.GameType
import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.PhaseType
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
        val game = Game(1)

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(false, game.isFinished)
        assertEquals(PhaseType.START, game.phase.currentPhase)
        assertEquals(3, game.hand.cards.size)
    }

    @Test
    fun wrongMovesShouldNotCrashGame() {
        //given
        val moves = listOf("x", "", "p x 0", "p k 9", "p k a", "p k", "n", "q").joinToString("\n")
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader(moves)))
        val game = Game(1)

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(true, game.isFinished)
        assertEquals(PhaseType.MIDDLE, game.phase.currentPhase)
        assertEquals(3, game.hand.cards.size)
    }

    @Test
    fun endOfInputShouldFinishGame() {
        //given
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader("n\n")))
        val game = Game(1)

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
        val game = Game(1, deck = Deck(simpleDeck))
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader("p k 0\nq\n")))

        //when
        val output = captureOutput { gameRunner.runGame(game) }

        //then
        assertTrue(output.contains("Move not allowed: not enough resources to play this card"))
        assertEquals(3, game.hand.cards.size)
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
