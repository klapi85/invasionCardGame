package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.MoveResult
import game.domain.cards.IronclawsHorde
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class IronclawsHordeTest {

    @Test
    fun shouldBePlayedOnlyInBattlefield() {
        //given
        val horde = IronclawsHorde()
        val game = Game(1, deck = Deck(mutableListOf(horde, Card("Goblin", 1, 1, 1), Card("Goblin", 1, 1, 1))))
        game.resources = 5

        //then
        assertEquals(MoveResult.BATTLEFIELD_ONLY, game.checkPutCardOnTable(0, game.kingdom))
        assertEquals(MoveResult.BATTLEFIELD_ONLY, game.checkPutCardOnTable(0, game.mission))

        //when
        val result = game.checkPutCardOnTable(0, game.battlefield)

        //then
        assertEquals(MoveResult.OK, result)
        assertEquals(listOf<Card>(horde), game.battlefield.cards)
        assertEquals(0, game.resources)
    }

    @Test
    fun shouldDeal4DamageInBattle() {
        //given
        val game = Game(1, deck = Deck(mutableListOf(IronclawsHorde(), Card("Goblin", 1, 1, 1), Card("Goblin", 1, 1, 1))))
        game.resources = 5
        game.checkPutCardOnTable(0, game.battlefield)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(36, game.enemyDurability)
    }
}
