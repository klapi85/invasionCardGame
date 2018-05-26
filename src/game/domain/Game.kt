package game.domain

class Game(
        val id: Int,
        var isFinished: Boolean = false
) {
    var table: Table = Table(24, 3,
        mutableListOf(
            Card("Red Goblin", 1, 1, 1)
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

    fun tapTableCard(cardNumber: Int): Int {
        this.table.tapCard(cardNumber)
        return 1
    }

    fun untapTableCard(cardNumber: Int): Int {
        this.table.untapCard(cardNumber)
        return 1
    }

    fun escapeGame(game: Game): Game {
        game.isFinished = true
        return game
    }

    fun checkPutCardOnTable(cardNumber: Int): Boolean {
        val cardCost = this.hand.cards.get(cardNumber).cost

        if (cardCost <= this.table.resources) {
            this.table.putNewCardOnTable(this.hand.removeFromHand(cardNumber))
            this.table.resources -= cardCost
            return true
        } else {
            return false
        }
    }

    fun checkTakeCardToHand(cardNumber: Int): Boolean {
        this.hand.takeCardToHand(this.table.removeFromTable(cardNumber))
        return true
    }
}