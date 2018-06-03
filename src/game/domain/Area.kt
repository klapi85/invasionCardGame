package game.domain

abstract class Area()
{
    abstract var cards: MutableList<Card>

    fun tapCard(number: Int): Int {
        this.cards[number].tap()
        return 1
    }

    fun untapCard(number: Int): Int {
        this.cards[number].untap()
        return 1
    }
}