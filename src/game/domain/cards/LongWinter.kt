package game.domain.cards

import game.domain.Card
import game.domain.CardType
import game.domain.Game

// Tactic: returns a random development card from the table to the hand
class LongWinter : Card(
        "Long Winter",
        1,
        cost = 1,
        type = CardType.TACTIC,
        website = "https://deckbox.org/whi/Long%20Winter"
) {
    override fun onEnter(game: Game) {
        val developmentsOnTable = listOf(game.battlefield, game.kingdom, game.mission)
                .flatMap { area -> area.developments.map { development -> area to development } }
        if (developmentsOnTable.isEmpty()) return

        val (area, development) = developmentsOnTable.random()
        area.developments.remove(development)
        game.hand.takeCardToHand(development)
    }
}
