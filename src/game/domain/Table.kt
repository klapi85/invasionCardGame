package game.domain

class Table(
        var life: Int = 24,
        var resources: Int = 3,
        var cards: MutableList<Card>
): Area() {
    fun tapCard(number: Int): Int {
        this.cards[number].tap()
        return 1
    }

    fun untapCard(number: Int): Int {
        this.cards[number].untap()
        return 1
    }

    fun increaseCardPower(number: Int): Int {
        this.cards[number].increasePower(1)
        return 1
    }

    fun putNewCardOnTable(newCard: Card): Boolean {
        this.cards.add(newCard)
        return true
    }

    fun removeFromTable(number: Int): Card =
        this.cards.removeAt(number)

    fun increaseCardDefence(number: Int): Int {
        this.cards[number].increaseDefence(1)
        return 1
    }
}