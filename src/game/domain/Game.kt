package game.domain

class Game(
        val id: Int,
        var isFinished: Boolean = false,
        val phase: PhaseManager = PhaseManager(),
) {
    val piles: Array<Pile> = arrayOf(Pile(), Pile(), Pile())
    val board = Board()
    var resources: Int = 3

    fun escapeGame(game: Game): Game {
        game.isFinished = true
        return game
    }

    fun checkPutCardOnBoard(pileNumber: Int, i: Int, j: Int): Boolean {
        return board.cards[i][j]?.let {
            board.cards[i][j] = this.piles[pileNumber].removeFromHand()
            true
        } ?: run{
            false
        }
    }

}
