package game.domain.cards

import game.domain.Card
import game.domain.CardType

// Unit: Battlefield only
class IronclawsHorde : Card(
        "Ironclaw's Horde",
        startingPower = 4,
        startingDefence = 2,
        cost = 5,
        type = CardType.UNIT,
        isBattlefieldOnly = true,
        website = "https://deckbox.org/whi/Ironclaw%27s%20Horde"
)
