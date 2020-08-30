package game.domain.areas

import game.domain.Card

class TableMission(
    override var cards: MutableList<Card>,
    override var developments: MutableList<Card>,
    override val areaName: String = "Mission",
    override val turnBonus: String = "1"
): Area()
