package test

import game.consoleInterface.GameRunner
import game.consoleInterface.GameType
import game.domain.Card
import game.domain.CardType
import game.domain.Deck
import game.domain.Game
import org.junit.jupiter.api.Test
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
}
