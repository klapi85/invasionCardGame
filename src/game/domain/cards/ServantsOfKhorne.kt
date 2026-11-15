package game.domain.cards

import game.domain.Card
import game.domain.CardType

// Unit: Battlefield only
class ServantsOfKhorne : Card(
        "Servants of Khorne",
        startingPower = 1,
        startingDefence = 1,
        cost = 1,
        type = CardType.UNIT,
        isBattlefieldOnly = true,
        website = "https://deckbox.org/whi/Servants%20of%20Khorne"
)
