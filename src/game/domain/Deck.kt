package game.domain

class Deck(var cards: MutableList<Card> = arrayListOf()
) {
    var exampleDeck: MutableList<Card> =
        mutableListOf(
            Card("Goblin", 1, 1, 1),
            Card("Goblin", 1, 1, 1),
            Card("Goblin", 1, 1, 1),
            Card("Minotaur", 2, 2, 2),
            Card("Minotaur", 2, 2, 2),
            Card("Minotaur", 2, 2, 2),
            Card("Hydra", 3, 4, 3),
            Card("Green Dragon", 4, 4, 3),
            Card("Red Dragon", 5, 4, 4),
            Card("Black Dragon", 6, 4, 5),
            Card("Sanctuary", 1, cost = 2, type = CardType.SUPPORT),
            Card("Sanctuary", 1, cost = 2, type = CardType.SUPPORT),
            Card("Sanctuary", 1, cost = 2, type = CardType.SUPPORT)
        )

    init {
        if (cards.isEmpty()) {
            cards = exampleDeck
            this.cards.shuffle()
        }
    }

    fun removeFromDeck(number: Int): Card =
            this.cards.removeAt(number)

    fun putCardOnDeck(newCard: Card): Boolean {
        this.cards.add(newCard)
        return true
    }
}
