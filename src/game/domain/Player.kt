package game.domain

import game.domain.areas.Area
import game.domain.areas.TableBattlefield
import game.domain.areas.TableKingdom
import game.domain.areas.TableMission

class Player(
        val id: Int,
        val deck: Deck = Deck(),
        var durability: Int = 40
) {
    val battlefield = TableBattlefield()
    val kingdom = TableKingdom()
    val mission = TableMission()
    val hand = Hand()
    val graveyard = Graveyard()
    var resources: Int = 3

    init {
        //deck
        repeat(3) {
            this.hand.takeCardToHand(deck.removeFromDeck(0))
        }
    }

    fun getAreas(): List<Area> = listOf(battlefield, kingdom, mission)

    // Untapped units on the battlefield attack; tapped units, supports and other cards don't
    fun getBattlePower(): Int =
            battlefield.cards
                    .filter { it.type == CardType.UNIT && it.status != CardStatus.TAPPED }
                    .sumOf { it.power }

    // At the end of the player's turn: resources and cards for the next turn, temporary boosts end
    fun prepareNextTurn() {
        this.resources = 3 + kingdom.getAreaCardsPower()

        for (i in 1..(1 + mission.getAreaCardsPower() ) ) {
            if (deck.cards.isEmpty()) break
            hand.takeCardToHand(deck.removeFromDeck(0))
        }
        // Temporary boosts still count above, for the next turn's resources and cards
        getAreas()
                .flatMap { it.cards }
                .forEach { it.removeTemporaryBoosts() }
    }
}
