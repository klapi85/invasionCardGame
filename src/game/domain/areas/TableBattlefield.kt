package game.domain.areas

import game.domain.Card

class TableBattlefield(
    override var cards: MutableList<Card>
): Area()