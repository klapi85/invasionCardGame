package game.domain

class Game(
        val id: Int,
        var table: Table = Table(24, arrayOf()),
        var isFinished: Boolean = false
) {
    var hand: Hand = Hand(arrayOf())

    init {
        this.table = Table(24,
                arrayOf(
                        Card("Goblin", CardStatus.UNTAPPED, CardType.UNIT, 1, 1),
                        Card("Minotaur", CardStatus.UNTAPPED, CardType.UNIT, 2, 2),
                        Card("Hydra", CardStatus.UNTAPPED, CardType.UNIT, 3, 4)
                )
        )

        this.hand = Hand(
                arrayOf(
                        Card("Dragon", CardStatus.UNTAPPED, CardType.UNIT, 5, 4)
                )
        )
    }

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
}