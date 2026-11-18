package game.domain

class PhaseManager (
        var turnNumber: Int = 1,
        var currentPhase: PhaseType = PhaseType.START
) {
    var isOncePerTurnLimitUsed = false
    var isDevelopmentAdded = false

    fun nextPhase(game: Game): Game {
        return when (this.currentPhase) {
            PhaseType.START -> finishStartPhase(game)
            PhaseType.MIDDLE -> finishMiddlePhase(game)
            PhaseType.END -> finishEndPhase(game)
            //else -> throw IllegalArgumentException("Wrong phase.")
        }
    }

    private fun finishStartPhase(game: Game): Game {
        this.currentPhase = PhaseType.MIDDLE
        game.battle()
        return game
    }

    private fun finishMiddlePhase(game: Game): Game {
        this.currentPhase = PhaseType.END
        return game
    }

    // After the END phase the other player's turn starts; the turn number grows when player 1 is back
    private fun finishEndPhase(game: Game): Game {
        this.currentPhase = PhaseType.START
        game.endTurn()
        if (game.activePlayer == game.player1) this.turnNumber++
        this.isOncePerTurnLimitUsed = false
        this.isDevelopmentAdded = false
        return game
    }
}

enum class PhaseType {
    START, MIDDLE, END
}
