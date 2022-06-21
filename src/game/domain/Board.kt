package game.domain

class Board(
    var cards: Array<Array<Card?>> = arrayOf(
                                arrayOf(
                                    Card("empty"),
                                    Card("empty"),
                                    Card("empty"),
                                    Card("empty"),
                                ),
                                arrayOf(
                                    Card("empty"),
                                    Card("empty"),
                                    Card("empty"),
                                    Card("empty"),
                                ),
                                arrayOf(
                                    Card("empty"),
                                    Card("empty"),
                                    Card("empty"),
                                    Card("empty"),
                                ),
                                arrayOf(
                                    Card("empty"),
                                    Card("empty"),
                                    Card("empty"),
                                    Card("empty"),
        )
    )
) {

}