package game.domain.cards

import game.domain.Card
import game.domain.CardType

// Unit
class BlackOrcSquad : Card(
        "Black Orc Squad",
        startingPower = 1,
        startingDefence = 3,
        cost = 3,
        type = CardType.UNIT,
        website = "https://deckbox.org/whi/Black%20Orc%20Squad"
)
