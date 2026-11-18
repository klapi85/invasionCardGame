package game.domain

import game.domain.areas.Area
import game.domain.areas.TableBattlefield

class Game(
        val player1: Player = Player(1),
        val player2: Player = Player(2),
        val phase: PhaseManager = PhaseManager()
) {
    var isFinished: Boolean = false
    var winner: Player? = null

    // Moves are made by the active player; players take turns
    var activePlayer: Player = player1
        private set

    val opponent: Player
        get() = if (activePlayer == player1) player2 else player1

    fun escapeGame(): Game {
        this.isFinished = true
        return this
    }

    fun battle() {
        opponent.durability -= activePlayer.getBattlePower()
        if (opponent.durability <= 0) {
            this.winner = activePlayer
            this.isFinished = true
        }
    }

    fun endTurn() {
        activePlayer.prepareNextTurn()
        activePlayer = opponent
    }

    fun checkPutCardOnTable(cardNumber: Int, tableArea: Area): MoveResult {
        val player = activePlayer
        val card = player.hand.cards[cardNumber]
        if (phase.currentPhase != PhaseType.START) return MoveResult.WRONG_PHASE
        if (card.isBattlefieldOnly && tableArea !is TableBattlefield) return MoveResult.BATTLEFIELD_ONLY
        if (card.cost > player.resources) return MoveResult.NOT_ENOUGH_RESOURCES
        if (card.isOncePerTurnLimited && phase.isOncePerTurnLimitUsed) return MoveResult.ONCE_PER_TURN_LIMIT_USED

        tableArea.putNewCardOnTable(player.hand.removeFromHand(cardNumber), this)
        player.resources -= card.cost
        return MoveResult.OK
    }

    fun checkAddNewDevelopment(cardNumber: Int, tableArea: Area): MoveResult {
        if (phase.currentPhase != PhaseType.START) return MoveResult.WRONG_PHASE
        if (phase.isDevelopmentAdded) return MoveResult.DEVELOPMENT_ALREADY_ADDED

        tableArea.addNewDevelopment(activePlayer.hand.removeFromHand(cardNumber), this)
        return MoveResult.OK
    }

    fun checkTakeCardFromAreaToHand(cardNumber: Int, tableArea: Area): Boolean {
        activePlayer.hand.takeCardToHand(tableArea.removeFromTable(cardNumber))
        return true
    }
}
