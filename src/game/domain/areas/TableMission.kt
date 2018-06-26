package game.domain.areas

import game.domain.Card

class TableMission(
    override var cards: MutableList<Card>
): Area()