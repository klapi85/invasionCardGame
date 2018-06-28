package game.domain

class Deck(
    ) {
    var cards: MutableList<Card> =
        mutableListOf(
            Card("Pink Goblin", 1, 1, 1),
            Card("Dragon",5, 4, 5),
            Card("Goblin", 1, 1, 1),
            Card("Minotaur",2, 2, 2),
            Card("Hydra", 3, 4, 3),
            Card("Red Dragon",4, 5, 5),
            Card("Dragon",5, 4, 5),
            Card("Dragon",5, 4, 5),
            Card("Black Dragon",6, 4, 6)

        )

    fun removeFromDeck(number: Int): Card =
        this.cards.removeAt(number)

    fun putCardOnDeck(newCard: Card): Boolean {
        this.cards.add(newCard)
        return true
    }
}