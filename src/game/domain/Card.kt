package game.domain

class Card(
        val id: String,
        val startingPower: Int = 0,
        val startingDefence: Int = 100,
        val cost: Int = 1,
        var status: CardStatus = CardStatus.UNDEF,
        val type: CardType = CardType.UNIT,
        var power: Int = 0,
        var defence: Int = 0,
        val isOncePerTurnLimited: Boolean = false
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
    TAPPED, UNTAPPED, UNDEF
}

enum class CardType {
    UNIT, SUPPORT, TACTIC, DEVELOPMENT
}
