package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.Player
import game.domain.MoveResult
import game.domain.cards.ContestedVillage
import game.domain.cards.Waaagh
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class WaaaghTest {

    @Test
    fun shouldBoostUnitsInChosenAreaAndGoToGraveyard() {
        //given
        val goblinOnBattlefield = Card("Goblin", 1, 1, 1)
        val goblinInKingdom = Card("Goblin", 1, 1, 1)
        val waaagh = Waaagh()
        val game = Game(Player(1, Deck(mutableListOf(goblinOnBattlefield, goblinInKingdom, waaagh))))
        game.player1.resources = 10
        game.checkPutCardOnTable(0, game.player1.battlefield)
        game.checkPutCardOnTable(0, game.player1.kingdom)

        //when
        val result = game.checkPutCardOnTable(0, game.player1.battlefield)

        //then
        assertEquals(MoveResult.OK, result)
        assertEquals(3, goblinOnBattlefield.power)
        assertEquals(1, goblinInKingdom.power)
        assertEquals(1, game.player1.battlefield.cards.size)
        assertEquals(listOf<Card>(waaagh), game.player1.graveyard.cards)
        assertEquals(5, game.player1.resources)
    }

    @Test
    fun shouldNotBoostSupports() {
        //given
        val village = ContestedVillage()
        val game = Game(Player(1, Deck(mutableListOf(village, Waaagh(), Card("Goblin", 1, 1, 1)))))
        game.player1.resources = 10
        game.checkPutCardOnTable(0, game.player1.battlefield)

        //when
        game.checkPutCardOnTable(0, game.player1.battlefield)

        //then
        assertEquals(1, village.power)
    }

    @Test
    fun boostShouldCountInBattleAndEndWithTheTurn() {
        //given
        val goblin = Card("Goblin", 1, 1, 1)
        val game = Game(Player(1, Deck(mutableListOf(goblin, Waaagh(), Card("Goblin", 1, 1, 1)))))
        game.player1.resources = 10
        game.checkPutCardOnTable(0, game.player1.battlefield)
        game.checkPutCardOnTable(0, game.player1.battlefield)

        //when MIDDLE: battle
        game.phase.nextPhase(game)

        //then
        assertEquals(37, game.player2.durability)

        //when START of the next turn
        game.phase.nextPhase(game)
        game.phase.nextPhase(game)

        //then
        assertEquals(1, goblin.power)
    }
}
