package game.consoleInterface

import game.domain.Game
import java.io.BufferedReader
import java.io.InputStreamReader

class GameRunner(var gameType: GameType) {
    private val printer : CardPrinter = CardPrinter()
    private val commands = listOf("q", "u", "t", "i", "d", "p", "h", "f", "n")

    fun runGame(game: Game): String {
        if (gameType == GameType.INTERACTIVE ) {
            println("Turn: " + game.phase.turnNumber + " phase: " + game.phase.currentPhase)
            println("Resources left: " + game.kingdom.resources.toString())
            println("• Battlefield: (" + game.battlefield.getAreaCardsStrength().toString() + ")")
            printer.printCards(game.battlefield.cards)
            println("• Kingdom: (3 + " + game.kingdom.getAreaCardsStrength().toString() + ")")
            printer.printCards(game.kingdom.cards)
            println("• Mission: (1 + " + game.mission.getAreaCardsStrength().toString() + ")")
            printer.printCards(game.mission.cards)
            println("• Hand:")
            printer.printCards(game.hand.cards)

            printer.printerVariant = PrinterType.NORMAL

            return if (game.isFinished) {
                ""
            } else {
                runGame(chooseMove(game, waitForValidInput()))
            }
        }
        return ""
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
            "d" -> makeIncreaseDefenceMove(game, options[1], options[2])
            else -> throw IllegalArgumentException("Wrong action with 3 params.")
        }
    }

    private fun makeTapMove(game: Game, areaName: String, cardNumber: String): Game {
        return when (areaName) {
            "b" -> {
                game.battlefield.tapCard(cardNumber.toInt())
                game
            }
            "k" -> {
                game.kingdom.tapCard(cardNumber.toInt())
                game
            }
            "m" -> {
                game.mission.tapCard(cardNumber.toInt())
                game
            }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }
    }

    private fun makeUntapMove(game: Game, areaName: String, cardNumber: String): Game {
        return when (areaName) {
            "b" -> {
                game.battlefield.untapCard(cardNumber.toInt())
                game
            }
            "k" -> {
                game.kingdom.untapCard(cardNumber.toInt())
                game
            }
            "m" -> {
                game.mission.untapCard(cardNumber.toInt())
                game
            }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }
    }

    private fun makeIncreasePowerMove(game: Game, areaName: String, cardNumber: String): Game {
        return when (areaName) {
            "b" -> {
                game.battlefield.increaseCardPower(cardNumber.toInt())
                game
            }
            "k" -> {
                game.kingdom.increaseCardPower(cardNumber.toInt())
                game
            }
            "m" -> {
                game.mission.increaseCardPower(cardNumber.toInt())
                game
            }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }
    }

    private fun makeIncreaseDefenceMove(game: Game, areaName: String, cardNumber: String): Game {
        return when (areaName) {
            "b" -> {
                game.battlefield.increaseCardDefence(cardNumber.toInt())
                game
            }
            "k" -> {
                game.kingdom.increaseCardDefence(cardNumber.toInt())
                game
            }
            "m" -> {
                game.mission.increaseCardDefence(cardNumber.toInt())
                game
            }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }
    }

    private fun playCardFromHand(game: Game, areaName: String, cardNumber: String): Game {
        return when (areaName) {
            "b" -> {
                game.checkPutCardOnTable(cardNumber.toInt(), game.battlefield)
                game
            }
            "k" -> {
                game.checkPutCardOnTable(cardNumber.toInt(), game.kingdom)
                game
            }
            "m" -> {
                game.checkPutCardOnTable(cardNumber.toInt(), game.mission)
                game
            }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }

    }

    private fun takeCardToHand(game: Game, areaName: String, cardNumber: String): Game {
        return when (areaName) {
            "b" -> {
                game.checkTakeCardFromBattlefieldToHand(cardNumber.toInt())
                game
            }
            "k" -> {
                game.checkTakeCardFromKingdomToHand(cardNumber.toInt())
                game
            }
            "m" -> {
                game.checkTakeCardFromMissionToHand(cardNumber.toInt())
                game
            }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }
    }
}

enum class GameType {
    INTERACTIVE, SILENT
}
