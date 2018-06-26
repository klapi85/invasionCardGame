package game.domain.areas

import game.domain.Card

class TableBattlefield(
    var resources: Int = 3,
    override var cards: MutableList<Card>
): Area()