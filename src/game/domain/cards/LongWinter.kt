package game.domain.cards

import game.domain.Card
import game.domain.CardType
import game.domain.Game
import game.domain.areas.Area

// Tactic: returns a random development card of the player from the table to the hand
class LongWinter : Card(
        "Long Winter",
        1,
        cost = 1,
        type = CardType.TACTIC,
        website = "https://deckbox.org/whi/Long%20Winter"
) {
    override fun onEnter(game: Game, area: Area) {
        val developmentsOnTable = game.activePlayer.getAreas()
                .flatMap { tableArea -> tableArea.developments.map { development -> tableArea to development } }
        if (developmentsOnTable.isEmpty()) return

        val (tableArea, development) = developmentsOnTable.random()
        tableArea.developments.remove(development)
        game.activePlayer.hand.takeCardToHand(development)
    }
}
