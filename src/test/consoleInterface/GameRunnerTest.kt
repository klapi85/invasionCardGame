package test.consoleInterface

import game.consoleInterface.GameRunner
import game.consoleInterface.GameType
import game.domain.Game
import game.domain.PhaseType
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.StringReader
import kotlin.test.assertEquals


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
}
