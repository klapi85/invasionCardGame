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

    fun checkPutCardOnTable(cardNumber: Int, tableArea: Area): MoveResult {
        val card = this.hand.cards[cardNumber]
        if (card.cost > this.resources) return MoveResult.NOT_ENOUGH_RESOURCES
        if (card.isOncePerTurnLimited && phase.isOncePerTurnLimitUsed) return MoveResult.ONCE_PER_TURN_LIMIT_USED

        tableArea.putNewCardOnTable(this.hand.removeFromHand(cardNumber), this)
        this.resources -= card.cost
        return MoveResult.OK
    }

    fun checkAddNewDevelopment(cardNumber: Int, tableArea: Area): MoveResult {
        if (phase.isDevelopmentAdded) return MoveResult.DEVELOPMENT_ALREADY_ADDED

        tableArea.addNewDevelopment(this.hand.removeFromHand(cardNumber), this)
        return MoveResult.OK
    }

    fun checkTakeCardFromAreaToHand(cardNumber: Int, tableArea: Area): Boolean {
        hand.takeCardToHand(tableArea.removeFromTable(cardNumber))
        return true
    }
}
