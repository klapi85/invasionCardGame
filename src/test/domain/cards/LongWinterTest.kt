package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
import game.domain.Player
import game.domain.cards.BlackOrcSquad
import game.domain.cards.LongWinter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class LongWinterTest {

    @Test
    fun shouldReturnDevelopmentToHand() {
        //given
        val development = Card("Goblin", 1, 1, 1)
        val longWinter = LongWinter()
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        development,
                        longWinter,
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))
        game.checkAddNewDevelopment(0, game.player1.kingdom)

        assertEquals(1, game.player1.kingdom.getDevelopmentsCounter())
        assertEquals(2, game.player1.hand.cards.size)

        //when
        game.checkPutCardOnTable(0, game.player1.battlefield)

        //then
        assertEquals(0, game.player1.kingdom.getDevelopmentsCounter())
        assertEquals(0, game.player1.battlefield.cards.size)
        assertEquals(2, game.player1.hand.cards.size)
        assertTrue(game.player1.hand.cards.contains(development))
        assertEquals(listOf<Card>(longWinter), game.player1.graveyard.cards)
    }

    @Test
    fun shouldDoNothingWithoutDevelopments() {
        //given
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        LongWinter(),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))

        //when
        game.checkPutCardOnTable(0, game.player1.kingdom)

        //then
        assertEquals(2, game.player1.hand.cards.size)
        assertEquals(0, game.player1.kingdom.cards.size)
    }

    @Test
    fun shouldReturnOnlyOneDevelopment() {
        //given
        val kingdomDevelopment = BlackOrcSquad()
        val missionDevelopment = Card("Hydra", 3, 4, 3)
        val simpleDeck: MutableList<Card> =
                mutableListOf(
                        LongWinter(),
                        Card("Goblin", 1, 1, 1),
                        Card("Goblin", 1, 1, 1)
                )
        val game = Game(Player(1, Deck(simpleDeck)))
        game.player1.kingdom.developments.add(kingdomDevelopment)
        game.player1.mission.developments.add(missionDevelopment)

        //when
        game.checkPutCardOnTable(0, game.player1.kingdom)

        //then
        assertEquals(1, game.player1.kingdom.getDevelopmentsCounter() + game.player1.mission.getDevelopmentsCounter())
        assertEquals(3, game.player1.hand.cards.size)
        assertTrue(game.player1.hand.cards.contains(kingdomDevelopment) xor game.player1.hand.cards.contains(missionDevelopment))
    }
}
