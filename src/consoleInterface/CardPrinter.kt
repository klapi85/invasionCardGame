package game.consoleInterface

import game.domain.Card

class CardPrinter() {
    fun printCards(cardSet: Array<Card>?): Int {
        println(getCards(cardSet))
        return 1
    }

    fun getCards(cardSet: Array<Card>?): String {
        var result = ""

        for (card in cardSet!!) {
            result += card.id + " " + card.status + " " + card.type + " | "
        }
        return result
    }
}
