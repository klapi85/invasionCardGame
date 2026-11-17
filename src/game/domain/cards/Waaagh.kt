package game.domain.cards

import game.domain.Card
import game.domain.CardType
import game.domain.Game
import game.domain.areas.Area

// Tactic: units in the chosen area get +2 power until the end of the turn
class Waaagh : Card(
        "Waaagh",
        cost = 3,
        type = CardType.TACTIC,
        website = "https://deckbox.org/whi/Waaagh%21"
) {
    override fun onEnter(game: Game, area: Area) {
        area.cards
                .filter { it.type == CardType.UNIT }
                .forEach { it.increasePowerUntilEndOfTurn(2) }
    }
}
