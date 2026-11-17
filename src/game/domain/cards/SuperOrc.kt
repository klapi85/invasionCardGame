package game.domain.cards

import game.domain.Card
import game.domain.CardType
import game.domain.Game
import game.domain.areas.Area

// Unit: when played, other units in the same area get +1 power until the end of the turn
class SuperOrc : Card(
        "Super Orc",
        startingPower = 2,
        startingDefence = 2,
        cost = 3,
        type = CardType.UNIT
) {
    override fun onEnter(game: Game, area: Area) {
        area.cards
                .filter { it.type == CardType.UNIT }
                .forEach { it.increasePowerUntilEndOfTurn(1) }
    }
}
