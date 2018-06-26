package game.domain

import game.domain.areas.TableBattlefield
import game.domain.areas.TableKingdom

class Game(
        val id: Int,
        var isFinished: Boolean = false
) {
    val battlefield: TableBattlefield = TableBattlefield( 3,
            mutableListOf(
                    Card("Red Goblin", 1, 1, 1)
            )
    )

    val kingdom: TableKingdom = TableKingdom(
            mutableListOf(
                    Card("Blue Goblin", 1, 1, 1)
            )
    )

    val mission: TableKingdom = TableKingdom(
            mutableListOf(
                    Card("Pink Goblin", 1, 1, 1)
            )
    )

    var hand: Hand = Hand(
        mutableListOf(
            Card("Dragon",5, 4, 5),
            Card("Goblin", 1, 1, 1),
            Card("Minotaur",2, 2, 2),
            Card("Hydra", 3, 4, 3)
        )
    )

    fun escapeGame(game: Game): Game {
        game.isFinished = true
        return game
    }

    // TODO: refactor 3 -> 1
    fun checkPutCardOnTableBattlefield(cardNumber: Int): Boolean {
        val cardCost = this.hand.cards.get(cardNumber).cost

        if (cardCost <= this.battlefield.resources) {
            this.battlefield.putNewCardOnTable(this.hand.removeFromHand(cardNumber))
            this.battlefield.resources -= cardCost
            return true
        } else {
            return false
        }
    }

    fun checkPutCardOnTableKingdom(cardNumber: Int): Boolean {
        val cardCost = this.hand.cards.get(cardNumber).cost

        if (cardCost <= this.battlefield.resources) {
            this.kingdom.putNewCardOnTable(this.hand.removeFromHand(cardNumber))
            this.battlefield.resources -= cardCost
            return true
        } else {
            return false
        }
    }

    fun checkPutCardOnTableMission(cardNumber: Int): Boolean {
        val cardCost = this.hand.cards.get(cardNumber).cost

        if (cardCost <= this.battlefield.resources) {
            this.mission.putNewCardOnTable(this.hand.removeFromHand(cardNumber))
            this.battlefield.resources -= cardCost
            return true
        } else {
            return false
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