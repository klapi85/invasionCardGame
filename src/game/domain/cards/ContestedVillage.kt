package game.domain.cards

import game.domain.Card
import game.domain.CardType

// Support: only one can be played per turn
class ContestedVillage : Card(
        "Contested Village",
        1,
        cost = 1,
        type = CardType.SUPPORT,
        isOncePerTurnLimited = true,
        website = "https://deckbox.org/whi/Contested%20Village"
)
