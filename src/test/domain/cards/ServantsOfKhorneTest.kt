package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.Player
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
        val game = Game(Player(1, Deck(simpleDeck)))

        //then
        assertEquals(MoveResult.BATTLEFIELD_ONLY, game.checkPutCardOnTable(0, game.player1.kingdom))
        assertEquals(MoveResult.BATTLEFIELD_ONLY, game.checkPutCardOnTable(0, game.player1.mission))
        assertEquals(3, game.player1.hand.cards.size)
        assertEquals(3, game.player1.resources)

        //when
        val result = game.checkPutCardOnTable(0, game.player1.battlefield)

        //then
        assertEquals(MoveResult.OK, result)
        assertEquals(1, game.player1.battlefield.cards.size)
        assertEquals(2, game.player1.resources)
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
        val game = Game(Player(1, Deck(simpleDeck)))

        //when
        val result = game.checkAddNewDevelopment(0, game.player1.kingdom)

        //then
        assertEquals(MoveResult.OK, result)
        assertEquals(1, game.player1.kingdom.getDevelopmentsCounter())
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
        val game = Game(Player(1, Deck(simpleDeck)))

        //then
        assertEquals(MoveResult.OK, game.checkPutCardOnTable(0, game.player1.kingdom))
    }
}
