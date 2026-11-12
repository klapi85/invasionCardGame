package game.consoleInterface

import game.domain.Game
import game.domain.MoveResult
import game.domain.areas.Area
import java.io.BufferedReader
import java.io.InputStreamReader

class GameRunner(
    var gameType: GameType,
    private val input: BufferedReader = BufferedReader(InputStreamReader(System.`in`))
) {
    private val printer : CardPrinter = CardPrinter()
    private val commands = listOf("q", "u", "t", "i", "d", "p", "h", "f", "n")

    fun runGame(game: Game): String {
        if (gameType == GameType.INTERACTIVE ) {
            var currentGame = game
            while (true) {
                printGameState(currentGame)
                if (currentGame.isFinished) return ""
                currentGame = makeMove(currentGame, waitForValidInput())
            }
        }
        return ""
    }

    private fun printGameState(game: Game) {
        println("Turn: " + game.phase.turnNumber + " phase: " + game.phase.currentPhase)
        println("Resources left: " + game.resources.toString())

        listOf(game.battlefield, game.mission, game.kingdom).forEach{ printAreaInfo(it) }

        println("• Hand:")
        printer.printCards(game.hand.cards)

        printer.printerVariant = PrinterType.NORMAL
    }

    // A wrong move is reported and the game goes on, instead of crashing
    private fun makeMove(game: Game, options: List<String>): Game {
        return try {
            chooseMove(game, options)
        } catch (e: NumberFormatException) {
            println("Wrong move: card number must be a number")
            game
        } catch (e: IllegalArgumentException) {
            println("Wrong move: " + e.message)
            game
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong move: there is no card with that number")
            game
        }
    }

    private fun printAreaInfo(area: Area) {
        println("• " + area.areaName + ": (" + area.turnBonus + " + " + area.getAreaCardsPower().toString() + ") " + area.getDevelopmentsCounter().toString() + "D")
        printer.printCards(area.cards)
    }

    // End of input (e.g. Ctrl+D or end of piped moves) quits the game
    private fun waitForInput(): String {
        println("Your move? (h for help)")
        return input.readLine() ?: "q"
    }

    private fun waitForValidInput(): List<String> {
        while (true) {
            val cells = waitForInput().trim().split(Regex("\\s+"))
            if (isValidAction(cells[0])) return cells
            println("Unknown command: " + cells[0])
        }
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
        println("eg. p k 1 means play first card in kingdom area")
        println("Press q for quit")
        println("- - - - - - - - - -")
    }

    private fun chooseThreeParamsMove(game: Game, options: List<String>) : Game {
        return when (options[0]) {
            "t" -> makeTapMove(game, options[1], options[2])
            "u" -> makeUntapMove(game, options[1], options[2])
            "p" -> playCardFromHand(game, options[1], options[2])
            "h" -> takeCardToHand(game, options[1], options[2])
            "i" -> makeIncreasePowerMove(game, options[1], options[2])
            "o" -> makeIncreaseDefenceMove(game, options[1], options[2])
            "d" -> putDevelopmentFromHand(game, options[1], options[2])
            else -> throw IllegalArgumentException("Wrong action with 3 params.")
        }
    }

    private fun makeTapMove(game: Game, areaName: String, cardNumber: String): Game {
        chooseAreaByShortcut(areaName, game).tapCard(cardNumber.toInt())
        return game
    }

    private fun makeUntapMove(game: Game, areaName: String, cardNumber: String): Game {
        chooseAreaByShortcut(areaName, game).untapCard(cardNumber.toInt())
        return game
    }

    private fun makeIncreasePowerMove(game: Game, areaName: String, cardNumber: String): Game {
        chooseAreaByShortcut(areaName, game).increaseCardPower(cardNumber.toInt())
        return game
    }

    private fun makeIncreaseDefenceMove(game: Game, areaName: String, cardNumber: String): Game {
        chooseAreaByShortcut(areaName, game).increaseCardDefence(cardNumber.toInt())
        return game
    }

    private fun playCardFromHand(game: Game, areaName: String, cardNumber: String): Game {
        printMoveResult(game.checkPutCardOnTable(cardNumber.toInt(), chooseAreaByShortcut(areaName, game)))
        return game
    }

    private fun takeCardToHand(game: Game, areaName: String, cardNumber: String): Game {
        game.checkTakeCardFromAreaToHand(cardNumber.toInt(), chooseAreaByShortcut(areaName, game))
        return game
    }

    private fun putDevelopmentFromHand(game: Game, areaName: String, cardNumber: String): Game {
        printMoveResult(game.checkAddNewDevelopment(cardNumber.toInt(), chooseAreaByShortcut(areaName, game)))
        return game
    }

    private fun printMoveResult(result: MoveResult) {
        val message = when (result) {
            MoveResult.OK -> return
            MoveResult.NOT_ENOUGH_RESOURCES -> "not enough resources to play this card"
            MoveResult.ONCE_PER_TURN_LIMIT_USED -> "this kind of card can be played only once per turn"
            MoveResult.DEVELOPMENT_ALREADY_ADDED -> "only one development can be added per turn"
        }
        println("Move not allowed: " + message)
    }

    private fun chooseAreaByShortcut(areaShortcut: String, game: Game) : Area {
        return when (areaShortcut) {
            "b" -> {
                game.battlefield
            }
            "k" -> {
                game.kingdom
            }
            "m" -> {
                game.mission
            }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }
    }
}


enum class GameType {
    INTERACTIVE, SILENT
}
