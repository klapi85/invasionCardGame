package game.consoleInterface

import game.domain.Game
import java.io.BufferedReader
import java.io.InputStreamReader

class GameRunner {
    private val printer : CardPrinter = CardPrinter()

    fun runGame(game: Game): String {
        println("Resources left: " + game.battlefield.resources.toString())
        println("Battlefield:")
        printer.printCards(game.battlefield.cards)
        println("Kingdom:")
        printer.printCards(game.kingdom.cards)
        println("Hand:")
        printer.printCards(game.hand.cards)

        return if (game.isFinished) {
            ""
        } else {
            runGame(chooseMove(game, waitForValidInput()))
        }
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

    private fun isValidAction(input: String): Boolean = arrayOf("q", "u", "t", "i", "d", "p", "h", "l", "b").contains(input)

    private fun isValidNumber(input: String): Boolean = arrayOf("0", "1", "2").contains(input)

    private fun chooseMove(game: Game, options: List<String>) : Game {
        return when (options.size) {
            1 -> chooseOneParamMove(game, options[0])
            2 -> chooseTwoParamsMove(game, options)
            3 -> chooseThreeParamsMove(game, options)
            else -> throw IllegalArgumentException("Actions: t,u,q,i,d,p,h space and number eg. t 2. For exit q 1")
        }
    }

    private fun chooseOneParamMove(game: Game, option: String) : Game {
        return when (option) {
            "h" -> game.escapeGame(game) // TODO
            "q" -> game.escapeGame(game)
            else -> throw IllegalArgumentException("Wrong Actions. For exit q")
        }
    }

    private fun chooseTwoParamsMove(game: Game, options: List<String>) : Game {
        return when (options[0]) {
            "u" -> makeUntapMove(game, options[1])
            "t" -> makeTapMove(game, options[1])
            "i" -> makeIncreasePowerMove(game, options[1])
            "d" -> makeIncreaseDefenceMove(game, options[1])
            else -> throw IllegalArgumentException("Actions: t,u,i,d space and number eg. t 2.")
        }
    }

    private fun chooseThreeParamsMove(game: Game, options: List<String>) : Game {
        return when (options[0]) {
            "p" -> playCardFromHand(game, options[1], options[2])
            "h" -> takeCardToHand(game, options[1], options[2])
            else -> throw IllegalArgumentException("Actions: p,h space and area [k,b]and number eg. t 2. For exit q 1")
        }
    }


    private fun makeUntapMove(game: Game, cardNumber: String): Game {
        game.untapTableCard(cardNumber.toInt())
        return game
    }

    private fun makeTapMove(game: Game, cardNumber: String): Game {
        game.tapTableCard(cardNumber.toInt())
        return game
    }

    private fun makeIncreasePowerMove(game: Game, cardNumber: String): Game {
        game.battlefield.increaseCardPower(cardNumber.toInt())
        return game
    }

    private fun makeIncreaseDefenceMove(game: Game, cardNumber: String): Game {
        game.battlefield.increaseCardDefence(cardNumber.toInt())
        return game
    }

    private fun playCardFromHand(game: Game, areaName: String, cardNumber: String): Game {
        when (areaName) {
            "b" -> {
                    game.checkPutCardOnTable(cardNumber.toInt())
                    return game
                   }
            "k" -> {
                    game.checkPutCardOnTableKingdom(cardNumber.toInt())
                    return game
                   }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }

    }

    private fun takeCardToHand(game: Game, areaName: String, cardNumber: String): Game {
        when (areaName) {
            "b" -> {
                game.checkTakeCardToHand(cardNumber.toInt())
                return game
            }
            "k" -> {
                game.checkTakeCardFromKingdomToHand(cardNumber.toInt())
                return game
            }
            else -> throw IllegalArgumentException("Please use proper area to play card")
        }
    }
}
