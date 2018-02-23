package game.domain

class Hand(
        var cards: MutableList<Card>
    ): Area() {

    fun putOnTable(number: Int): Card {
        val result = cards[number]
        this.cards.removeAt(number)
        return result
    }
}