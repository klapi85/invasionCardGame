package game.domain

class Card(
        val id: String,
        var status: CardStatus = CardStatus.TAPPED,
        val type: CardType = CardType.SUPPORT,
        val power: Int? = 0,
        val defence: Int? = 0
) {
    fun tap() {
        this.status = CardStatus.TAPPED
    }

    fun untap() {
        this.status = CardStatus.UNTAPPED
    }
}

enum class CardStatus {
    TAPPED, UNTAPPED, ON_HAND, IN_DECK, REMOVED
}

enum class CardType {
    UNIT, SUPPORT, TACTIC, DEVELOPMENT
}