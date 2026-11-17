package game.domain.cards

import game.domain.Card
import game.domain.CardType
import game.domain.Game
import game.domain.areas.Area

// Tactic: returns a random development card from the table to the hand
class LongWinter : Card(
        "Long Winter",
        1,
        cost = 1,
        type = CardType.TACTIC,
        website = "https://deckbox.org/whi/Long%20Winter"
) {
    override fun onEnter(game: Game, area: Area) {
        val developmentsOnTable = listOf(game.battlefield, game.kingdom, game.mission)
                .flatMap { tableArea -> tableArea.developments.map { development -> tableArea to development } }
        if (developmentsOnTable.isEmpty()) return

        val (tableArea, development) = developmentsOnTable.random()
        tableArea.developments.remove(development)
        game.hand.takeCardToHand(development)
    }
}
