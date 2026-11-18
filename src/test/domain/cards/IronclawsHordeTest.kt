package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.Player
import game.domain.MoveResult
import game.domain.cards.IronclawsHorde
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class IronclawsHordeTest {

    @Test
    fun shouldBePlayedOnlyInBattlefield() {
        //given
        val horde = IronclawsHorde()
        val game = Game(Player(1, Deck(mutableListOf(horde, Card("Goblin", 1, 1, 1), Card("Goblin", 1, 1, 1)))))
        game.player1.resources = 5

        //then
        assertEquals(MoveResult.BATTLEFIELD_ONLY, game.checkPutCardOnTable(0, game.player1.kingdom))
        assertEquals(MoveResult.BATTLEFIELD_ONLY, game.checkPutCardOnTable(0, game.player1.mission))

        //when
        val result = game.checkPutCardOnTable(0, game.player1.battlefield)

        //then
        assertEquals(MoveResult.OK, result)
        assertEquals(listOf<Card>(horde), game.player1.battlefield.cards)
        assertEquals(0, game.player1.resources)
    }

    @Test
    fun shouldDeal4DamageInBattle() {
        //given
        val game = Game(Player(1, Deck(mutableListOf(IronclawsHorde(), Card("Goblin", 1, 1, 1), Card("Goblin", 1, 1, 1)))))
        game.player1.resources = 5
        game.checkPutCardOnTable(0, game.player1.battlefield)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(36, game.player2.durability)
    }
}
