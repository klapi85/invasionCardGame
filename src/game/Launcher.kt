package game

import game.consoleInterface.GameRunner
import game.consoleInterface.GameType
import game.domain.Game

fun main() {
    val gameRunner = GameRunner(GameType.INTERACTIVE)
    val game = Game(1)

    println("Start:")
    gameRunner.runGame(game)
    println("Game over")
}
