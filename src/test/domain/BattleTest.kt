package test.domain

import game.domain.Card
import game.domain.CardStatus
import game.domain.Deck
import game.domain.Game
import game.domain.MoveResult
import game.domain.PhaseType
import game.domain.cards.ContestedVillage
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class BattleTest {

    @Test
    fun enemyShouldStartWith40Durability() {
        //when
        val game = Game(1)

        //then
        assertEquals(40, game.enemyDurability)
    }

    @Test
    fun battleShouldDamageEnemyWithUnitsOnBattlefield() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        ContestedVillage()
                )
        val game = Game(1, deck = Deck(simpleDeck))
        repeat(3) { game.checkPutCardOnTable(0, game.battlefield) }

        assertEquals(3, game.battlefield.cards.size)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(PhaseType.MIDDLE, game.phase.currentPhase)
        assertEquals(38, game.enemyDurability)
        assertEquals(false, game.isWon)
        assertEquals(false, game.isFinished)
    }

    @Test
    fun unitsOutsideBattlefieldShouldNotAttack() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))
        game.checkPutCardOnTable(0, game.kingdom)
        game.checkPutCardOnTable(0, game.mission)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(40, game.enemyDurability)
    }

    @Test
    fun tappedUnitsShouldNotAttack() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Minotaur", 2, 2, 2),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))
        game.checkPutCardOnTable(0, game.battlefield)
        game.checkPutCardOnTable(0, game.battlefield)
        game.battlefield.tapCard(1)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(39, game.enemyDurability)
        assertEquals(CardStatus.TAPPED, game.battlefield.cards[1].status)
        assertEquals(CardStatus.UNDEF, game.battlefield.cards[0].status)
    }

    @Test
    fun cardsCanBePlayedOnlyInStartPhase() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        //when MIDDLE
        game.phase.nextPhase(game)

        //then
        assertEquals(MoveResult.WRONG_PHASE, game.checkPutCardOnTable(0, game.battlefield))
        assertEquals(MoveResult.WRONG_PHASE, game.checkAddNewDevelopment(0, game.kingdom))

        //when END
        game.phase.nextPhase(game)

        //then
        assertEquals(MoveResult.WRONG_PHASE, game.checkPutCardOnTable(0, game.battlefield))
        assertEquals(3, game.hand.cards.size)

        //when START of the next turn
        game.phase.nextPhase(game)

        //then
        assertEquals(MoveResult.OK, game.checkPutCardOnTable(0, game.battlefield))
    }

    @Test
    fun gameShouldBeWonWhenEnemyDurabilityReachesZero() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck), enemyDurability = 2)
        game.checkPutCardOnTable(0, game.battlefield)
        game.checkPutCardOnTable(0, game.battlefield)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(0, game.enemyDurability)
        assertEquals(true, game.isWon)
        assertEquals(true, game.isFinished)
    }
}
