package game.consoleInterface

import game.domain.Card

class CardPrinter() {
    var printerVariant: PrinterType = PrinterType.NORMAL

    fun printCards(cardSet: Array<Card?>): Int {
        println(getCards(cardSet))
        return 1
    }

    private fun getCards(cardSet: Array<Card?>): String {
        return when (this.printerVariant) {
            PrinterType.FULL -> printFullInfo(cardSet)
            PrinterType.NORMAL -> printNormalInfo(cardSet)
        }
    }

    private fun printFullInfo(cardSet: Array<Card?>) : String {
        val result = "   "
        return result
    }

    private fun printNormalInfo(cardSet: Array<Card?>) : String {
        var result = " │"
        for (i in 0..3) {
            if (cardSet[i]?.id == "empty") {
                result += "    "
            } else {
                result += " " + cardSet[i]?.id
            }
            result += "  │"
        }
        return result
    }
}

enum class PrinterType {
    NORMAL, FULL
}
