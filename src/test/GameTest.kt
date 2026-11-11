package test

import game.consoleInterface.GameRunner
import game.consoleInterface.GameType
import game.domain.Card
import game.domain.CardType
import game.domain.Deck
import game.domain.Game
import game.domain.PhaseType
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.StringReader
import kotlin.test.assertEquals


class GameTest {

    @Test
    fun gameShouldStart() {
        //given
        val gameRunner = GameRunner(GameType.SILENT)
        val game = Game(1)

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(3, game.hand.cards.size)
    }


    @Test
    fun gameShouldPlayCards() {
        //given
        val gameRunner = GameRunner(GameType.SILENT)

        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(3, game.hand.cards.size)
        assertEquals(1, game.hand.cards[1].startingPower)
        assertEquals("Goblin", game.hand.cards[1].id)
    }

    @Test
    fun isOncePerTurnLimitedShouldWork() {
        //given
        val gameRunner = GameRunner(GameType.SILENT)

        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        //when
       gameRunner.runGame(game)

        //then
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
        val gameRunner = GameRunner(GameType.SILENT)

        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Sanctuary", 1, cost = 1, type = CardType.SUPPORT, isOncePerTurnLimited = true),
                        Card("Sanctuary", 1, cost = 1, type = CardType.SUPPORT, isOncePerTurnLimited = true),
                        Card("Sanctuary", 1, cost = 1, type = CardType.SUPPORT, isOncePerTurnLimited = true)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        assertEquals(3, game.hand.cards.size)

        //when
        gameRunner.runGame(game)
        game.checkPutCardOnTable(1, game.kingdom)

        //then
        assertEquals(2, game.hand.cards.size)

        //when
        game.checkPutCardOnTable(1, game.kingdom)

        //then
        assertEquals(2, game.hand.cards.size)

    }

    @Test
    fun wrongMovesShouldNotCrashGame() {
        //given
        val moves = listOf("x", "", "p x 0", "p k 9", "p k a", "p k", "n", "q").joinToString("\n")
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader(moves)))
        val game = Game(1)

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(true, game.isFinished)
        assertEquals(PhaseType.MIDDLE, game.phase.currentPhase)
        assertEquals(3, game.hand.cards.size)
    }

    @Test
    fun endOfInputShouldFinishGame() {
        //given
        val gameRunner = GameRunner(GameType.INTERACTIVE, BufferedReader(StringReader("n\n")))
        val game = Game(1)

        //when
        gameRunner.runGame(game)

        //then
        assertEquals(true, game.isFinished)
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
        val gameRunner = GameRunner(GameType.SILENT)

        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        Card("Glorious Strength", 1, cost = 1, type = CardType.TACTIC, hasOnEnterEffect = true),
                        Card("Glorious Strength", 1, cost = 1, type = CardType.TACTIC, hasOnEnterEffect = true),
                        Card("Glorious Strength", 1, cost = 1, type = CardType.TACTIC, hasOnEnterEffect = true)
                )
        val game = Game(1, deck = Deck(simpleDeck))

        assertEquals(3, game.hand.cards.size)

        //when
        gameRunner.runGame(game)
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
