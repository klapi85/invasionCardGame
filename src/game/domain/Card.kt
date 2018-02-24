package game.domain

class Card(
        val id: String,
        val startingPower: Int = 0,
        val startingDefence: Int = 0,
        val cost: Int = 1,
        var status: CardStatus = CardStatus.UNTAPPED,
        val type: CardType = CardType.SUPPORT,
        var power: Int = 0,
        var defence: Int = 0
) {
    init {
        this.power = this.startingPower
        this.defence = this.startingDefence
    }

    fun tap() {
        this.status = CardStatus.TAPPED
    }

    fun untap() {
        this.status = CardStatus.UNTAPPED
    }

    fun increasePower(howMany: Int) {
        this.power += howMany
    }

    fun increaseDefence(howMany: Int) {
        this.defence += howMany
    }
}

enum class CardStatus {
    TAPPED, UNTAPPED, ON_HAND, IN_DECK, REMOVED
}

enum class CardType {
    UNIT, SUPPORT, TACTIC, DEVELOPMENT
}