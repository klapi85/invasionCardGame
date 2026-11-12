package game.domain

class Hand(
        var cards: MutableList<Card> = mutableListOf()
    ) {

    fun removeFromHand(number: Int): Card =
        this.cards.removeAt(number)

    fun takeCardToHand(newCard: Card): Boolean {
        this.cards.add(newCard)
        return true
    }
}