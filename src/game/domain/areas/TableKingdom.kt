package game.domain.areas;

import game.domain.Card

class TableKingdom(
    override var cards: MutableList<Card>
): Area() {
    var resources: Int = 3

    fun addResources() {
        // at the begining of your turn
        this.resources = 3 + this.getAreaCardsStrength()
    }
}
