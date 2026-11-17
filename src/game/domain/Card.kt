package game.domain

import game.domain.areas.Area

open class Card(
        val id: String,
        val startingPower: Int = 0,
        val startingDefence: Int = 100,
        val cost: Int = 1,
        var status: CardStatus = CardStatus.UNDEF,
        val type: CardType = CardType.UNIT,
        var power: Int = 0,
        var defence: Int = 0,
        val isOncePerTurnLimited: Boolean = false,
        val isBattlefieldOnly: Boolean = false,
        val website: String = ""
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

    // Part of power that lasts only until the end of the turn
    var temporaryPowerBoost: Int = 0
        private set

    fun increasePowerUntilEndOfTurn(howMany: Int) {
        this.power += howMany
        this.temporaryPowerBoost += howMany
    }

    fun removeTemporaryBoosts() {
        this.power -= this.temporaryPowerBoost
        this.temporaryPowerBoost = 0
    }

    fun increaseDefence(howMany: Int) {
        this.defence += howMany
    }

    // Called when the card is played, before it is put in the area; cards with special effects
    // (e.g. tactics in game.domain.cards) override it
    open fun onEnter(game: Game, area: Area) {}
}

enum class CardStatus {
    TAPPED, UNTAPPED, UNDEF
}

enum class CardType {
    UNIT, SUPPORT, TACTIC, DEVELOPMENT
}
