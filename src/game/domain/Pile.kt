package game.domain

class Pile (
    var cards: MutableList<Card> = arrayListOf()
) {
    var exampleDeck: MutableList<Card> =
        mutableListOf(
            Card("Gob", 1, 1, 1),
            Card("Min", 2, 2, 2),
            Card("Hyd", 3, 4, 3),
            Card("Gre", 4, 4, 3),
            Card("Ren", 5, 4, 4),
            Card("Bla", 6, 4, 2),
        )

    init {
        if (cards.isEmpty()) {
            cards = exampleDeck
            this.cards.shuffle()
        }
    }
    fun removeFromHand(): Card =
        this.cards.removeAt(0)

}