package game.domain.areas;

import game.domain.Card

class TableKingdom(
    override var cards: MutableList<Card>,
    override var developments: MutableList<Card>,
    override val areaName: String = "Kingdom",
    override val turnBonus: String = "3"
): Area()
