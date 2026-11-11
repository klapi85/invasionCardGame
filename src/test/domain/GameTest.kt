package test.domain

import game.domain.Card
import game.domain.CardType
import game.domain.Deck
import game.domain.Game
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class GameTest {

    @Test
    fun gameShouldStart() {
        //when
        val game = Game(1)

        //then
        assertEquals(3, game.hand.cards.size)
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
        val game = Game(1, deck = Deck(simpleDeck))

        //then
        assertEquals(3, game.hand.cards.size)
        assertEquals(1, game.hand.cards[1].startingPower)
        assertEquals("Goblin", game.hand.cards[1].id)
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
        val game = Game(1, deck = Deck(simpleDeck))

        assertEquals(3, game.hand.cards.size)
        assertEquals(0, game.kingdom.getDevelopmentsCounter())

        //when
        game.checkAddNewDevelopment(0, game.kingdom)

        //then
        assertEquals(2, game.hand.cards.size)
        assertEquals(1, game.kingdom.getDevelopmentsCounter())

        //when
        game.checkAddNewDevelopment(0, game.kingdom)

        //then
        assertEquals(2, game.hand.cards.size)
        assertEquals(1, game.kingdom.getDevelopmentsCounter())

    }

    @Test
    fun isDevelopmentLimitedOncePerTurn() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Sanctuary", 1, cost = 1, type = CardType.SUPPORT, isOncePerTurnLimited = true),
                        Card("Sanctuary", 1, cost = 1, type = CardType.SUPPORT, isOncePerTurnLimited = true),
                        Card("Sanctuary", 1, cost = 1, type = CardType.SUPPORT, isOncePerTurnLimited = true)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        assertEquals(3, game.hand.cards.size)

        //when
        game.checkPutCardOnTable(1, game.kingdom)

        //then
        assertEquals(2, game.hand.cards.size)

        //when
        game.checkPutCardOnTable(1, game.kingdom)

        //then
        assertEquals(2, game.hand.cards.size)

    }

    @Test
    fun onceLimitedCardShouldNotBlockOtherCards() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Sanctuary", 1, cost = 1, type = CardType.SUPPORT, isOncePerTurnLimited = true),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        //when
        game.checkPutCardOnTable(0, game.kingdom)
        game.checkPutCardOnTable(0, game.battlefield)

        //then
        assertEquals(1, game.hand.cards.size)
        assertEquals(1, game.kingdom.cards.size)
        assertEquals(1, game.battlefield.cards.size)
    }

    @Test
    fun tacticsCardShouldNotBePlacedOnTable() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Glorious Strength", 1, cost = 1, type = CardType.TACTIC, hasOnEnterEffect = true),
                        Card("Glorious Strength", 1, cost = 1, type = CardType.TACTIC, hasOnEnterEffect = true),
                        Card("Glorious Strength", 1, cost = 1, type = CardType.TACTIC, hasOnEnterEffect = true)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        assertEquals(3, game.hand.cards.size)

        //when
        game.checkPutCardOnTable(1, game.kingdom)

        //then
        assertEquals(2, game.hand.cards.size)
        assertEquals(0, game.kingdom.cards.size)

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
        val game = Game(1, deck = Deck(simpleDeck))

        assertEquals(0, game.deck.cards.size)

        //when
        repeat(3) { game.phase.nextPhase(game) }

        //then
        assertEquals(2, game.phase.turnNumber)
        assertEquals(3, game.hand.cards.size)
    }
}
