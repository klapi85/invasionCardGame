package game.domain

class Game(
        val id: Int,
        var table: Table?
) {
    fun prepareCards() {
        this.table = Table(24,
                arrayOf(
                        Card("Goblin", CardStatus.UNTAPPED, CardType.UNIT),
                        Card("Minotaur", CardStatus.UNTAPPED, CardType.UNIT),
                        Card("Hydra", CardStatus.UNTAPPED, CardType.UNIT)
                )
        )
    }

    fun tapTableCard(cardNumber: Int): Int {
        this.table!!.tapCard(cardNumber)
        return 1
    }

    fun untapTableCard(cardNumber: Int): Int {
        this.table!!.untapCard(cardNumber)
        return 1
    }
}