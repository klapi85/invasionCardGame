package test

import game.consoleInterface.GameRunner
import game.consoleInterface.GameType
import game.domain.Card
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
}
