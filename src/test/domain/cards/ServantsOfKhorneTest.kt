package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.MoveResult
import game.domain.cards.ServantsOfKhorne
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class ServantsOfKhorneTest {

    @Test
    fun shouldBePlayedOnlyInBattlefield() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        ServantsOfKhorne(),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        //then
        assertEquals(MoveResult.BATTLEFIELD_ONLY, game.checkPutCardOnTable(0, game.kingdom))
        assertEquals(MoveResult.BATTLEFIELD_ONLY, game.checkPutCardOnTable(0, game.mission))
        assertEquals(3, game.hand.cards.size)
        assertEquals(3, game.resources)

        //when
        val result = game.checkPutCardOnTable(0, game.battlefield)

        //then
        assertEquals(MoveResult.OK, result)
        assertEquals(1, game.battlefield.cards.size)
        assertEquals(2, game.resources)
    }

    @Test
    fun shouldBeAllowedAsDevelopmentInAnyArea() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        ServantsOfKhorne(),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        //when
        val result = game.checkAddNewDevelopment(0, game.kingdom)

        //then
        assertEquals(MoveResult.OK, result)
        assertEquals(1, game.kingdom.getDevelopmentsCounter())
    }

    @Test
    fun otherCardsShouldNotBeBattlefieldOnly() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        //then
        assertEquals(MoveResult.OK, game.checkPutCardOnTable(0, game.kingdom))
    }
}
