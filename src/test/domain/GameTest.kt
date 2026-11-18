package test.domain

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.Player
import game.domain.MoveResult
import game.domain.cards.ContestedVillage
import game.domain.cards.LongWinter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class GameTest {

    @Test
    fun gameShouldStart() {
        //when
        val game = Game()

        //then
        assertEquals(3, game.player1.hand.cards.size)
    }


    @Test
    fun gameShouldPlayCards() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )

        //when
        val game = Game(Player(1, Deck(simpleDeck)))

        //then
        assertEquals(3, game.player1.hand.cards.size)
        assertEquals(1, game.player1.hand.cards[1].startingPower)
        assertEquals("Goblin", game.player1.hand.cards[1].id)
    }

    @Test
    fun isOncePerTurnLimitedShouldWork() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))

        assertEquals(3, game.player1.hand.cards.size)
        assertEquals(0, game.player1.kingdom.getDevelopmentsCounter())

        //when
        game.checkAddNewDevelopment(0, game.player1.kingdom)

        //then
        assertEquals(2, game.player1.hand.cards.size)
        assertEquals(1, game.player1.kingdom.getDevelopmentsCounter())

        //when
        game.checkAddNewDevelopment(0, game.player1.kingdom)

        //then
        assertEquals(2, game.player1.hand.cards.size)
        assertEquals(1, game.player1.kingdom.getDevelopmentsCounter())

    }

    @Test
    fun isDevelopmentLimitedOncePerTurn() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        ContestedVillage(),
                        ContestedVillage(),
                        ContestedVillage()
                )
        val game = Game(Player(1, Deck(simpleDeck)))

        assertEquals(3, game.player1.hand.cards.size)

        //when
        game.checkPutCardOnTable(1, game.player1.kingdom)

        //then
        assertEquals(2, game.player1.hand.cards.size)

        //when
        game.checkPutCardOnTable(1, game.player1.kingdom)

        //then
        assertEquals(2, game.player1.hand.cards.size)

    }

    @Test
    fun moveResultShouldExplainWhyMoveIsNotAllowed() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Black Dragon", 6, 4, 5),
                        ContestedVillage(),
                        ContestedVillage(),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))
        game.player1.hand.takeCardToHand(game.player1.deck.removeFromDeck(0))
        game.player1.hand.takeCardToHand(game.player1.deck.removeFromDeck(0))

        //then
        assertEquals(MoveResult.NOT_ENOUGH_RESOURCES, game.checkPutCardOnTable(0, game.player1.kingdom))
        assertEquals(MoveResult.OK, game.checkPutCardOnTable(1, game.player1.kingdom))
        assertEquals(MoveResult.ONCE_PER_TURN_LIMIT_USED, game.checkPutCardOnTable(1, game.player1.kingdom))
        assertEquals(MoveResult.OK, game.checkAddNewDevelopment(1, game.player1.mission))
        assertEquals(MoveResult.DEVELOPMENT_ALREADY_ADDED, game.checkAddNewDevelopment(1, game.player1.mission))
        assertEquals(3, game.player1.hand.cards.size)
    }

    @Test
    fun onceLimitedCardShouldNotBlockOtherCards() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        ContestedVillage(),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))

        //when
        game.checkPutCardOnTable(0, game.player1.kingdom)
        game.checkPutCardOnTable(0, game.player1.battlefield)

        //then
        assertEquals(1, game.player1.hand.cards.size)
        assertEquals(1, game.player1.kingdom.cards.size)
        assertEquals(1, game.player1.battlefield.cards.size)
    }

    @Test
    fun tacticsCardShouldNotBePlacedOnTable() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        LongWinter(),
                        LongWinter(),
                        LongWinter()
                )
        val game = Game(Player(1, Deck(simpleDeck)))

        assertEquals(3, game.player1.hand.cards.size)

        //when
        game.checkPutCardOnTable(1, game.player1.kingdom)

        //then
        assertEquals(2, game.player1.hand.cards.size)
        assertEquals(0, game.player1.kingdom.cards.size)
        assertEquals(1, game.player1.graveyard.cards.size)

    }

    @Test
    fun unitCardShouldNotGoToGraveyard() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))

        //when
        game.checkPutCardOnTable(0, game.player1.kingdom)

        //then
        assertEquals(1, game.player1.kingdom.cards.size)
        assertEquals(0, game.player1.graveyard.cards.size)
    }

    @Test
    fun endPhaseShouldNotDrawFromEmptyDeck() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))

        assertEquals(0, game.player1.deck.cards.size)

        //when
        repeat(3) { game.phase.nextPhase(game) }

        //then player 2's turn starts, player 1 drew nothing
        assertEquals(game.player2, game.activePlayer)
        assertEquals(3, game.player1.hand.cards.size)
    }
}
