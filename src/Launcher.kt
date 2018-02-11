package game

import game.domain.Game
import game.consoleInterface.CardPrinter
import java.io.BufferedReader
import java.io.InputStreamReader

val printer = CardPrinter()
var isFinished = false

fun main(args: Array<String>) {
    val game = Game(1, null)
    game.prepareCards()

    println("Start:")
    runGame(game)
    println("Game over")
}

fun makeUntapMove(game: Game, cardNumber: String): Game {
    game.untapTableCard(cardNumber.toInt())
    return game
}


fun makeTapMove(game: Game, cardNumber: String): Game {
    game.tapTableCard(cardNumber.toInt())
    return game
}

fun runGame(game: Game): String {
    printer.printCards(game.table?.cards)

    if ( isFinished) {
        return ""
    } else {
        return runGame(chooseMove(game, waitForValidInput()))
    }
}

fun waitForInput(): String {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    println("Your move?")
    return reader.readLine()
}

fun waitForValidInput(): List<String> {
    val input = waitForInput()
    val cells = input.split(" ")
    return if (isValidAction(cells[0]) and isValidNumber(cells[1])) cells else waitForValidInput()
}

fun isValidAction(input: String): Boolean = arrayOf("q", "u", "t").contains(input)

fun isValidNumber(input: String): Boolean = arrayOf("0", "1", "2").contains(input)

fun chooseMove(game: Game, options: List<String>): Game = when (options[0]) {
    "u" -> makeUntapMove(game, options[1])
    "t" -> makeTapMove(game, options[1])
    "q" -> escapeGame(game)
    else -> throw IllegalArgumentException("Actions: t,u,q space and number eg. t 2. For exit q 1")
}

fun escapeGame(game: Game): Game {
    isFinished = true
    return game
}
