package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.cards.ContestedVillage
import game.domain.cards.SuperOrc
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class SuperOrcTest {

    @Test
    fun shouldBoostOtherUnitsInTheSameArea() {
        //given
        val goblinOnBattlefield = Card("Goblin", 1, 1, 1)
        val goblinInKingdom = Card("Goblin", 1, 1, 1)
        val superOrc = SuperOrc()
        val game = Game(1, deck = Deck(mutableListOf(goblinOnBattlefield, goblinInKingdom, superOrc)))
        game.resources = 10
        game.checkPutCardOnTable(0, game.battlefield)
        game.checkPutCardOnTable(0, game.kingdom)

        //when
        game.checkPutCardOnTable(0, game.battlefield)

        //then
        assertEquals(2, goblinOnBattlefield.power)
        assertEquals(1, goblinInKingdom.power)
        assertEquals(2, superOrc.power)
    }

    @Test
    fun shouldNotBoostSupports() {
        //given
        val village = ContestedVillage()
        val game = Game(1, deck = Deck(mutableListOf(village, SuperOrc(), Card("Goblin", 1, 1, 1))))
        game.resources = 10
        game.checkPutCardOnTable(0, game.battlefield)

        //when
        game.checkPutCardOnTable(0, game.battlefield)

        //then
        assertEquals(1, village.power)
    }

    @Test
    fun boostShouldCountInBattleAndEndWithTheTurn() {
        //given
        val goblin = Card("Goblin", 1, 1, 1)
        val superOrc = SuperOrc()
        val game = Game(1, deck = Deck(mutableListOf(goblin, superOrc, Card("Goblin", 1, 1, 1))))
        game.resources = 10
        game.checkPutCardOnTable(0, game.battlefield)
        game.checkPutCardOnTable(0, game.battlefield)
        game.battlefield.increaseCardPower(0)

        assertEquals(3, goblin.power)

        //when MIDDLE: battle
        game.phase.nextPhase(game)

        //then goblin 3 (1 + 1 permanent + 1 boost) + Super Orc 2
        assertEquals(35, game.enemyDurability)

        //when START of the next turn
        game.phase.nextPhase(game)
        game.phase.nextPhase(game)

        //then only the permanent increase stays
        assertEquals(2, goblin.power)
        assertEquals(0, goblin.temporaryPowerBoost)
        assertEquals(2, superOrc.power)
    }

    @Test
    fun boostShouldCountForResourcesAtEndOfTurn() {
        //given
        val game = Game(1, deck = Deck(mutableListOf(Card("Goblin", 1, 1, 1), SuperOrc(), Card("Goblin", 1, 1, 1))))
        game.resources = 10
        game.checkPutCardOnTable(0, game.kingdom)
        game.checkPutCardOnTable(0, game.kingdom)

        //when
        repeat(3) { game.phase.nextPhase(game) }

        //then 3 + goblin 2 (boosted) + Super Orc 2
        assertEquals(7, game.resources)
        assertEquals(3, game.kingdom.getAreaCardsPower())
    }
}
