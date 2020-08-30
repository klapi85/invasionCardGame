package game.domain.areas;

import game.domain.Card

class TableKingdom(
    override var cards: MutableList<Card>,
    override var developments: MutableList<Card>
): Area()
