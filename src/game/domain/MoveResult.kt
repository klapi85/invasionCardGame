package game.domain

enum class MoveResult {
    OK,
    NOT_ENOUGH_RESOURCES,
    ONCE_PER_TURN_LIMIT_USED,
    DEVELOPMENT_ALREADY_ADDED
}
