package game.domain

class Game(
        val id: Int,
        var isFinished: Boolean = false
) {
    var battlefield: TableBattlefield = TableBattlefield(24, 3,
        mutableListOf(
            Card("Red Goblin", 1, 1, 1)
        )
    )

    var kingdom: TableKingdom = TableKingdom(
        mutableListOf(
            Card("Blue Goblin", 1, 1, 1)
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

    fun checkPutCardOnTable(cardNumber: Int): Boolean {
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


    fun checkTakeCardToHand(cardNumber: Int): Boolean {
        this.hand.takeCardToHand(this.battlefield.removeFromTable(cardNumber))
        return true
    }

    fun checkTakeCardFromKingdomToHand(cardNumber: Int): Boolean {
        this.hand.takeCardToHand(this.kingdom.removeFromTable(cardNumber))
        return true
    }
}