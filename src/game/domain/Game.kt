package game.domain

import game.domain.areas.Area
import game.domain.areas.TableBattlefield
import game.domain.areas.TableKingdom
import game.domain.areas.TableMission

class Game(
        val id: Int,
        var isFinished: Boolean = false,
        val phase: PhaseManager = PhaseManager(),
        val deck: Deck = Deck()
) {
    val battlefield = TableBattlefield(mutableListOf(), mutableListOf())
    val kingdom = TableKingdom(mutableListOf(), mutableListOf())
    val mission = TableMission(mutableListOf(), mutableListOf())
    val hand = Hand(mutableListOf())
    var resources: Int = 3

    init {
        //deck
        repeat(3) {
            this.hand.takeCardToHand(deck.removeFromDeck(0))
        }
    }

    fun escapeGame(game: Game): Game {
        game.isFinished = true
        return game
    }

    fun checkPutCardOnTable(cardNumber: Int, tableArea: Area): Boolean {
        val cardCost = this.hand.cards[cardNumber].cost
        return if (cardCost <= this.resources && !phase.isOncePerTurnLimitUsed) {
            tableArea.putNewCardOnTable(this.hand.removeFromHand(cardNumber), this)
            this.resources -= cardCost
            true
        } else {
            false
        }
    }

    fun checkAddNewDevelopment(cardNumber: Int, tableArea: Area): Boolean {
        return if (!phase.isDevelopmentAdded) {
            tableArea.addNewDevelopment(this.hand.removeFromHand(cardNumber), this)
            true
        } else {
            false
        }
    }

    fun checkTakeCardFromBattlefieldToHand(cardNumber: Int): Boolean {
        this.hand.takeCardToHand(this.battlefield.removeFromTable(cardNumber))
        return true
    }

    fun checkTakeCardFromKingdomToHand(cardNumber: Int): Boolean {
        this.hand.takeCardToHand(this.kingdom.removeFromTable(cardNumber))
        return true
    }
    fun checkTakeCardFromMissionToHand(cardNumber: Int): Boolean {
        this.hand.takeCardToHand(this.mission.removeFromTable(cardNumber))
        return true
    }
}
