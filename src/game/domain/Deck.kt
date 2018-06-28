package game.domain

class Deck(
    ) {
    var cards: MutableList<Card> =
        mutableListOf(
            Card("Goblin", 1, 1, 1),
            Card("Goblin", 1, 1, 1),
            Card("Goblin", 1, 1, 1),
            Card("Minotaur",2, 2, 2),
            Card("Hydra", 3, 4, 3),
            Card("Green Dragon",4, 4, 4),
            Card("Red Dragon",5, 4, 5),
            Card("Red Dragon",5, 4, 5),
            Card("Black Dragon",6, 4, 6)
        )

    init {
        this.cards.shuffle()
        // shuffle requires Kotlin v.1.2
    }

    fun removeFromDeck(number: Int): Card =
        this.cards.removeAt(number)

    fun putCardOnDeck(newCard: Card): Boolean {
        this.cards.add(newCard)
        return true
    }
}