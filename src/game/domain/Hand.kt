package game.domain

class Hand(
        var cards: MutableList<Card>
    ): Area() {

    fun removeFromHand(number: Int): Card =
        this.cards.removeAt(number)

}