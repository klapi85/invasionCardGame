package game.domain

class PhaseManager (
        var turnNumber: Int = 1,
        var currentPhase: PhaseType = PhaseType.START
) {
    var isOncePerTurnLimitUsed = false

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
        return game
    }

    private fun finishMiddlePhase(game: Game): Game {
        this.currentPhase = PhaseType.END
        return game
    }

    private fun finishEndPhase(game: Game): Game {
        this.currentPhase = PhaseType.START
        game.resources = 3 + game.kingdom.getAreaCardsPower()

        for (i in 1..(1 + game.mission.getAreaCardsPower() ) ) {
            game.hand.takeCardToHand(game.deck.removeFromDeck(0))
        }
        this.turnNumber++
        this.isOncePerTurnLimitUsed = false
        return game
    }
}

enum class PhaseType {
    START, MIDDLE, END
}
