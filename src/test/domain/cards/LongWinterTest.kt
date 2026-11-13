package test.domain.cards

import game.domain.Card
import game.domain.Deck
import game.domain.Game
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
        val game = Game(1, deck = Deck(simpleDeck))
        game.checkAddNewDevelopment(0, game.kingdom)

        assertEquals(1, game.kingdom.getDevelopmentsCounter())
        assertEquals(2, game.hand.cards.size)

        //when
        game.checkPutCardOnTable(0, game.battlefield)

        //then
        assertEquals(0, game.kingdom.getDevelopmentsCounter())
        assertEquals(0, game.battlefield.cards.size)
        assertEquals(2, game.hand.cards.size)
        assertTrue(game.hand.cards.contains(development))
        assertEquals(listOf<Card>(longWinter), game.graveyard.cards)
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
        val game = Game(1, deck = Deck(simpleDeck))

        //when
        game.checkPutCardOnTable(0, game.kingdom)

        //then
        assertEquals(2, game.hand.cards.size)
        assertEquals(0, game.kingdom.cards.size)
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
        val game = Game(1, deck = Deck(simpleDeck))
        game.kingdom.developments.add(kingdomDevelopment)
        game.mission.developments.add(missionDevelopment)

        //when
        game.checkPutCardOnTable(0, game.kingdom)

        //then
        assertEquals(1, game.kingdom.getDevelopmentsCounter() + game.mission.getDevelopmentsCounter())
        assertEquals(3, game.hand.cards.size)
        assertTrue(game.hand.cards.contains(kingdomDevelopment) xor game.hand.cards.contains(missionDevelopment))
    }
}
