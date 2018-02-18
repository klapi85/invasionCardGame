package game

import game.consoleInterface.GameRunner
import game.domain.Game

fun main(args: Array<String>) {
    val gameRunner = GameRunner()
    val game = Game(1)

    println("Start:")
    gameRunner.runGame(game)
    println("Game over")
}
