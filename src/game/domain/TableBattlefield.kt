package game.domain

class TableBattlefield(
    var life: Int = 24,
    var resources: Int = 3,
    override var cards: MutableList<Card>
): Area()