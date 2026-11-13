package game.domain

import game.domain.cards.BlackOrcSquad
import game.domain.cards.ContestedVillage
import game.domain.cards.LongWinter

class Deck(var cards: MutableList<Card> = arrayListOf()
) {
    var exampleDeck: MutableList<Card> =
        mutableListOf(
            Card("Goblin", 1, 1, 1),
            Card("Goblin", 1, 1, 1),
            Card("Goblin", 1, 1, 1),
            BlackOrcSquad(),
            BlackOrcSquad(),
            BlackOrcSquad(),
            Card("Hydra", 3, 4, 3),
            Card("Green Dragon", 4, 4, 3),
            Card("Red Dragon", 5, 4, 4),
            Card("Black Dragon", 6, 4, 5),
            ContestedVillage(),
            ContestedVillage(),
            ContestedVillage(),
            LongWinter()
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
