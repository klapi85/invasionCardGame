package game.domain

class Card(
        val id: String,
        val startingPower: Int = 0,
        val startingDefence: Int = 100,
        val cost: Int = 1,
        var status: CardStatus = CardStatus.UNDEF,
        val type: CardType = CardType.UNIT,
        val isOncePerTurnLimited: Boolean = false
) {
    init {

    }

    fun tap() {
        this.status = CardStatus.TAPPED
    }

    fun untap() {
        this.status = CardStatus.UNTAPPED
    }

}

enum class CardStatus {
    TAPPED, UNTAPPED, UNDEF
}

enum class CardType {
    UNIT, SUPPORT, TACTIC, DEVELOPMENT
}
