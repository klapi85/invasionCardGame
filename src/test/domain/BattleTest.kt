package test.domain

import game.domain.Card
import game.domain.CardStatus
import game.domain.Deck
import game.domain.Game
import game.domain.Player
import game.domain.MoveResult
import game.domain.PhaseType
import game.domain.cards.BlackOrcSquad
import game.domain.cards.ContestedVillage
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class BattleTest {

    @Test
    fun enemyShouldStartWith40Durability() {
        //when
        val game = Game()

        //then
        assertEquals(40, game.player2.durability)
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
        val game = Game(Player(1, Deck(simpleDeck)))
        repeat(3) { game.checkPutCardOnTable(0, game.player1.battlefield) }

        assertEquals(3, game.player1.battlefield.cards.size)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(PhaseType.MIDDLE, game.phase.currentPhase)
        assertEquals(38, game.player2.durability)
        assertEquals(null, game.winner)
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
        val game = Game(Player(1, Deck(simpleDeck)))
        game.checkPutCardOnTable(0, game.player1.kingdom)
        game.checkPutCardOnTable(0, game.player1.mission)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(40, game.player2.durability)
    }

    @Test
    fun tappedUnitsShouldNotAttack() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        BlackOrcSquad(),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))
        game.player1.resources = 4
        game.checkPutCardOnTable(0, game.player1.battlefield)
        game.checkPutCardOnTable(0, game.player1.battlefield)
        game.player1.battlefield.tapCard(1)

        assertEquals(2, game.player1.battlefield.cards.size)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(39, game.player2.durability)
        assertEquals(CardStatus.TAPPED, game.player1.battlefield.cards[1].status)
        assertEquals(CardStatus.UNDEF, game.player1.battlefield.cards[0].status)
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
        val game = Game(Player(1, Deck(simpleDeck)), Player(2, Deck(goblins())))

        //when MIDDLE
        game.phase.nextPhase(game)

        //then
        assertEquals(MoveResult.WRONG_PHASE, game.checkPutCardOnTable(0, game.player1.battlefield))
        assertEquals(MoveResult.WRONG_PHASE, game.checkAddNewDevelopment(0, game.player1.kingdom))

        //when END
        game.phase.nextPhase(game)

        //then
        assertEquals(MoveResult.WRONG_PHASE, game.checkPutCardOnTable(0, game.player1.battlefield))
        assertEquals(3, game.player1.hand.cards.size)

        //when START of player 2's turn
        game.phase.nextPhase(game)

        //then
        assertEquals(MoveResult.OK, game.checkPutCardOnTable(0, game.player2.battlefield))
        assertEquals(1, game.player2.battlefield.cards.size)
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
        val game = Game(Player(1, Deck(simpleDeck)), Player(2, durability = 2))
        game.checkPutCardOnTable(0, game.player1.battlefield)
        game.checkPutCardOnTable(0, game.player1.battlefield)

        //when
        game.phase.nextPhase(game)

        //then
        assertEquals(0, game.player2.durability)
        assertEquals(game.player1, game.winner)
        assertEquals(true, game.isFinished)
    }

    private fun goblins(): MutableList<Card> =
            mutableListOf(
                    Card("Goblin", 1, 1, 1),
                    Card("Goblin", 1, 1, 1),
                    Card("Goblin", 1, 1, 1),
                    Card("Goblin", 1, 1, 1)
            )
}
