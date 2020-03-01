package game

import game.consoleInterface.GameRunner
import game.domain.Game

fun main() {
    val gameRunner = GameRunner()
    val game = Game(1)

    println("Start:")
    gameRunner.runGame(game)
    println("Game over")
}
