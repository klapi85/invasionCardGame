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
    val battlefield = TableBattlefield()
    val kingdom = TableKingdom()
    val mission = TableMission()
    val hand = Hand()
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
        val card = this.hand.cards[cardNumber]
        val isBlockedByTurnLimit = card.isOncePerTurnLimited && phase.isOncePerTurnLimitUsed
        return if (card.cost <= this.resources && !isBlockedByTurnLimit) {
            tableArea.putNewCardOnTable(this.hand.removeFromHand(cardNumber), this)
            this.resources -= card.cost
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

    fun checkTakeCardFromAreaToHand(cardNumber: Int, tableArea: Area): Boolean {
        hand.takeCardToHand(tableArea.removeFromTable(cardNumber))
        return true
    }
}
