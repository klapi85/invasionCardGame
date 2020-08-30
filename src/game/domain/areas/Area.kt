package game.domain.areas

import game.domain.Card
import game.domain.Game

abstract class Area()
{
    abstract var cards: MutableList<Card>
    abstract var developments: MutableList<Card>
    abstract val areaName: String
    abstract val turnBonus: String

    fun putNewCardOnTable(newCard: Card, game: Game): Boolean {
        this.cards.add(newCard)
        if (newCard.isOncePerTurnLimited) game.phase.isOncePerTurnLimitUsed = true
        return true
    }

    fun removeFromTable(number: Int): Card =
            this.cards.removeAt(number)

    fun addNewDevelopment(newCard: Card, game: Game): Boolean {
        this.developments.add(newCard)
        game.phase.isDevelopmentAdded = true
        return true
    }

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

    fun getDevelopmentsCounter(): Int =
            this.developments.size
}
