package game.domain.areas;

import game.domain.Card

class TableKingdom(
    override var cards: MutableList<Card>
): Area() {
    var resources: Int = 3

    fun addResources() {
        // at the begining og your turn
        this.resources += 5 // TODO: stub +5
    }
}
