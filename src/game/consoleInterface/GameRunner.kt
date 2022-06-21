package game.consoleInterface

import game.domain.Card
import game.domain.Game
import game.domain.Pile
import java.io.BufferedReader
import java.io.InputStreamReader

class GameRunner(var gameType: GameType) {
    private val printer : CardPrinter = CardPrinter()
    private val commands = listOf("q", "u", "t", "i", "d", "p", "h", "f", "n")

    fun runGame(game: Game): String {
        if (gameType == GameType.INTERACTIVE ) {
            println("Turn: " + game.phase.turnNumber + " phase: " + game.phase.currentPhase)
            println("Resources left: " + game.resources.toString())

            println(" ┌──────┬──────┬──────┬──────┐")
            printBoardInfo(game.board.cards[0])
            println(" │      │      │      │      │")
            println(" ├──────┼──────┼──────┼──────┤")
            printBoardInfo(game.board.cards[1])
            println(" │      │      │      │      │")
            println(" ├──────┼──────┼──────┼──────┤")
            printBoardInfo(game.board.cards[2])
            println(" │      │      │      │      │")
            println(" ├──────┼──────┼──────┼──────┤")
            printBoardInfo(game.board.cards[3])
            println(" │      │      │      │      │")
            println(" └──────┴──────┴──────┴──────┘")

            game.piles.forEach{ printPileInfo(it) }

            printer.printerVariant = PrinterType.NORMAL

            return if (game.isFinished) {
                ""
            } else {
                runGame(chooseMove(game, waitForValidInput()))
            }
        }
        return ""
    }

    private fun printBoardInfo(cardArray: Array<Card?>) {
        printer.printCards(cardArray)
    }

    private fun printPileInfo(pile: Pile) {
        println("• " + pile.cards[0].id )
    }

    private fun waitForInput(): String {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        println("Your move? (h for help)")
        return reader.readLine()
    }

    private fun waitForValidInput(): List<String> {
        val input = waitForInput()
        val cells = input.split(" ")
        return if (isValidAction(cells[0])) cells else waitForValidInput()
    }

    private fun isValidAction(input: String): Boolean = commands.contains(input)

    private fun chooseMove(game: Game, options: List<String>) : Game {
        return when (options.size) {
            1 -> chooseOneParamMove(game, options[0])
            // 2 -> chooseTwoParamsMove(game, options)
            3 -> chooseThreeParamsMove(game, options)
            else -> throw IllegalArgumentException("Action not allowed.")
        }
    }

    private fun chooseOneParamMove(game: Game, option: String) : Game {
        return when (option) {
            "h" -> { printHelp()
                     game
                   }
            "f" -> { this.printer.printerVariant = PrinterType.FULL
                     game
                   }
            "n" -> game.phase.nextPhase(game)
            "q" -> game.escapeGame(game)
            else -> throw IllegalArgumentException("Wrong action with 1 param.")
        }
    }

    //private fun chooseTwoParamsMove(game: Game, options: List<String>) : Game {
    //    return when (options[0]) {
    //        else -> throw IllegalArgumentException("Wrong action with 2 params.")
    //    }
    //}

    private fun printHelp() {
        println("HELP: Allowed commands are:")
        println(commands)
        println("eg. p 1 1 means play first card")
        println("Press q for quit")
        println("- - - - - - - - - -")
    }

    private fun chooseThreeParamsMove(game: Game, options: List<String>) : Game {
        return when (options[0]) {
            "p" -> playCardFromHand(game, options[1], options[2])
            else -> throw IllegalArgumentException("Wrong action with 3 params.")
        }
    }

    private fun playCardFromHand(game: Game, areaName: String, cardNumber: String): Game {
        game.checkPutCardOnBoard(areaName.toInt(), cardNumber.toInt(), cardNumber.toInt())
        return game
    }
}

enum class GameType {
    INTERACTIVE, SILENT
}
