package game.consoleInterface

import game.domain.Card
import game.domain.CardStatus
import game.domain.CardType

class CardPrinter() {
    var printerVariant: PrinterType = PrinterType.NORMAL

    fun printCards(cardSet: List<Card>?): Int {
        println(getCards(cardSet))
        return 1
    }

    private fun getCards(cardSet: List<Card>?): String {
        return when (this.printerVariant) {
            PrinterType.FULL -> printFullInfo(cardSet)
            PrinterType.NORMAL -> printNormalInfo(cardSet)
        }
    }

    private fun printFullInfo(cardSet: List<Card>?) : String {
        var result = "   "

        cardSet?.forEach { card ->
            result += card.id + " " + card.status + " " +
                    card.type + " " +
                    card.power + "P(" + card.startingPower + "P) " +
                    card.defence + "D(" + card.startingDefence + "D)" +
                    card.cost + "$ | "
        }
        return result
    }

    private fun printNormalInfo(cardSet: List<Card>?) : String {
        var result = "   "

        cardSet?.forEach { card ->
            result += printSpecialCardInfo(card)
        }
        return result
    }

    private fun printSpecialCardInfo(card: Card): String {
        return when (card.type) {
            CardType.SUPPORT -> printSupportCardInfo(card)
            CardType.UNIT -> printUnitCardInfo(card)
            else -> printUnitCardInfo(card)
        }
    }

    private fun printSupportCardInfo(card: Card): String {
        return card.id + " " + translateCardStatus(card.status) + " " +
            card.type + " " + card.power + "P " + card.cost + "$ | "
    }

    private fun printUnitCardInfo(card: Card): String {
        return card.id + " " + translateCardStatus(card.status) + " " +
                card.type + " " + card.power + "P " + card.defence + "D " + card.cost + "$ | "
    }

    private fun translateCardStatus(status: CardStatus): String {
        return when (status) {
            CardStatus.TAPPED -> "T"
            CardStatus.UNTAPPED -> "UT"
            else -> "_"
        }
    }
}

enum class PrinterType {
    NORMAL, FULL
}
