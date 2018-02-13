package game.domain

class Table(
        val life: Int = 24,
        var cards: Array<Card>
) {
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
}