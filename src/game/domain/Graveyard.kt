package game.domain

// Pile of used cards, e.g. tactics after their effect
class Graveyard {
    val cards: MutableList<Card> = mutableListOf()

    fun putCard(card: Card) {
        this.cards.add(card)
    }
}
