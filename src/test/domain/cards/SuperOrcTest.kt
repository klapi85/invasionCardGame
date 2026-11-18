package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.Player
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
        val game = Game(Player(1, Deck(mutableListOf(goblinOnBattlefield, goblinInKingdom, superOrc))))
        game.player1.resources = 10
        game.checkPutCardOnTable(0, game.player1.battlefield)
        game.checkPutCardOnTable(0, game.player1.kingdom)

        //when
        game.checkPutCardOnTable(0, game.player1.battlefield)

        //then
        assertEquals(2, goblinOnBattlefield.power)
        assertEquals(1, goblinInKingdom.power)
        assertEquals(2, superOrc.power)
    }

    @Test
    fun shouldNotBoostSupports() {
        //given
        val village = ContestedVillage()
        val game = Game(Player(1, Deck(mutableListOf(village, SuperOrc(), Card("Goblin", 1, 1, 1)))))
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
        val superOrc = SuperOrc()
        val game = Game(Player(1, Deck(mutableListOf(goblin, superOrc, Card("Goblin", 1, 1, 1)))))
        game.player1.resources = 10
        game.checkPutCardOnTable(0, game.player1.battlefield)
        game.checkPutCardOnTable(0, game.player1.battlefield)
        game.player1.battlefield.increaseCardPower(0)

        assertEquals(3, goblin.power)

        //when MIDDLE: battle
        game.phase.nextPhase(game)

        //then goblin 3 (1 + 1 permanent + 1 boost) + Super Orc 2
        assertEquals(35, game.player2.durability)

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
        val game = Game(Player(1, Deck(mutableListOf(Card("Goblin", 1, 1, 1), SuperOrc(), Card("Goblin", 1, 1, 1)))))
        game.player1.resources = 10
        game.checkPutCardOnTable(0, game.player1.kingdom)
        game.checkPutCardOnTable(0, game.player1.kingdom)

        //when
        repeat(3) { game.phase.nextPhase(game) }

        //then 3 + goblin 2 (boosted) + Super Orc 2
        assertEquals(7, game.player1.resources)
        assertEquals(3, game.player1.kingdom.getAreaCardsPower())
    }
}
