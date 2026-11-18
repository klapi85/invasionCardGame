package test.domain

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.PhaseType
import game.domain.Player
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class TurnTest {

    @Test
    fun playersShouldTakeTurnsInLoop() {
        //given
        val game = Game(Player(1, Deck(goblins())), Player(2, Deck(goblins())))

        assertEquals(game.player1, game.activePlayer)
        assertEquals(1, game.phase.turnNumber)

        //when END of player 1
        repeat(3) { game.phase.nextPhase(game) }

        //then START of player 2
        assertEquals(game.player2, game.activePlayer)
        assertEquals(game.player1, game.opponent)
        assertEquals(PhaseType.START, game.phase.currentPhase)
        assertEquals(1, game.phase.turnNumber)

        //when END of player 2
        repeat(3) { game.phase.nextPhase(game) }

        //then START of player 1 in the next turn
        assertEquals(game.player1, game.activePlayer)
        assertEquals(PhaseType.START, game.phase.currentPhase)
        assertEquals(2, game.phase.turnNumber)
    }

    @Test
    fun playersShouldPlayOwnCards() {
        //given
        val game = Game(Player(1, Deck(goblins())), Player(2, Deck(goblins())))

        //when
        game.checkPutCardOnTable(0, game.player1.battlefield)
        repeat(3) { game.phase.nextPhase(game) }
        game.checkPutCardOnTable(0, game.player2.kingdom)

        //then
        assertEquals(1, game.player1.battlefield.cards.size)
        assertEquals(0, game.player1.kingdom.cards.size)
        assertEquals(1, game.player2.kingdom.cards.size)
        assertEquals(0, game.player2.battlefield.cards.size)
        assertEquals(2, game.player2.resources)
    }

    @Test
    fun player2ShouldAttackPlayer1AndCanWin() {
        //given
        val game = Game(Player(1, Deck(goblins()), durability = 1), Player(2, Deck(goblins())))
        repeat(3) { game.phase.nextPhase(game) }
        game.checkPutCardOnTable(0, game.player2.battlefield)

        //when MIDDLE of player 2: battle
        game.phase.nextPhase(game)

        //then
        assertEquals(0, game.player1.durability)
        assertEquals(40, game.player2.durability)
        assertEquals(game.player2, game.winner)
        assertEquals(true, game.isFinished)
    }

    @Test
    fun onlyActivePlayerUnitsShouldAttack() {
        //given player 1 has a unit on the battlefield
        val game = Game(Player(1, Deck(goblins())), Player(2, Deck(goblins())))
        game.checkPutCardOnTable(0, game.player1.battlefield)
        repeat(3) { game.phase.nextPhase(game) }

        assertEquals(39, game.player2.durability)

        //when MIDDLE of player 2, who has no units
        game.phase.nextPhase(game)

        //then
        assertEquals(40, game.player1.durability)
    }

    @Test
    fun endOfTurnShouldPrepareOnlyThePlayerWhoseTurnEnds() {
        //given
        val game = Game(Player(1, Deck(goblins())), Player(2, Deck(goblins())))
        game.checkPutCardOnTable(0, game.player1.kingdom)

        //when END of player 1
        repeat(3) { game.phase.nextPhase(game) }

        //then player 1: 3 + 1 kingdom power, drew 1 card; player 2 untouched
        assertEquals(4, game.player1.resources)
        assertEquals(3, game.player1.hand.cards.size)
        assertEquals(3, game.player2.resources)
        assertEquals(3, game.player2.hand.cards.size)
    }

    private fun goblins(): MutableList<Card> =
            mutableListOf(
                    Card("Goblin", 1, 1, 1),
                    Card("Goblin", 1, 1, 1),
                    Card("Goblin", 1, 1, 1),
                    Card("Goblin", 1, 1, 1),
                    Card("Goblin", 1, 1, 1)
            )
}
