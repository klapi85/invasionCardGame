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
        println("Your move?")
        return reader.readLine()
    }

    private fun waitForValidInput(): List<String> {
        val input = waitForInput()
        val cells = input.split(" ")
        return if (isValidAction(cells[0]) and isValidNumber(cells[1])) cells else waitForValidInput()
    }

    private fun isValidAction(input: String): Boolean = arrayOf("q", "u", "t", "i", "d", "p", "h", "l", "b").contains(input)

    private fun isValidNumber(input: String): Boolean = arrayOf("0", "1", "2").contains(input)

    private fun chooseMove(game: Game, options: List<String>) : Game {
        return when (options[0]) {
            "u" -> makeUntapMove(game, options[1])
            "t" -> makeTapMove(game, options[1])
            "i" -> makeIncreasePowerMove(game, options[1])
            "d" -> makeIncreaseDefenceMove(game, options[1])
            "p" -> playCardFromHand(game, options[1])
            "l" -> playCardFromHandToKingdom(game, options[1])
            "h" -> takeCardToHand(game, options[1])
            "b" -> takeCardFromKingdomToHand(game, options[1])
            "q" -> game.escapeGame(game)
            else -> throw IllegalArgumentException("Actions: t,u,q,i,d,p,h space and number eg. t 2. For exit q 1")
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

    private fun playCardFromHand(game: Game, cardNumber: String): Game {
        game.checkPutCardOnTable(cardNumber.toInt())
        return game
    }

    private fun playCardFromHandToKingdom(game: Game, cardNumber: String): Game {
        game.checkPutCardOnTableKingdom(cardNumber.toInt())
        return game
    }

    private fun takeCardToHand(game: Game, cardNumber: String): Game {
        game.checkTakeCardToHand(cardNumber.toInt())
        return game
    }

    private fun takeCardFromKingdomToHand(game: Game, cardNumber: String): Game {
        game.checkTakeCardFromKingdomToHand(cardNumber.toInt())
        return game
    }
}