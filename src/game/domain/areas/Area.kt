package game.domain.areas

import game.domain.Card
import game.domain.Game

abstract class Area()
{
    abstract var cards: MutableList<Card>

    fun putNewCardOnTable(newCard: Card, game: Game): Boolean {
        this.cards.add(newCard)
        if (newCard.isOncePerTurnLimited) game.phase.isOncePerTurnLimitUsed = true
        return true
    }

    fun removeFromTable(number: Int): Card =
            this.cards.removeAt(number)

    fun tapCard(number: Int): Int {
        this.cards[number].tap()
        return 1
    }

    fun untapCard(number: Int): Int {
        this.cards[number].untap()
        return 1
    }

    fun increaseCardPower(number: Int): Int {
        this.cards[number].increasePower(1)
        return 1
    }

    fun increaseCardDefence(number: Int): Int {
        this.cards[number].increaseDefence(1)
        return 1
    }

    fun getAreaCardsPower(): Int =
            this.cards.sumBy { it.power }

}
