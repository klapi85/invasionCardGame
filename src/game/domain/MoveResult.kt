package game.domain

enum class MoveResult {
    OK,
    WRONG_PHASE,
    NOT_ENOUGH_RESOURCES,
    ONCE_PER_TURN_LIMIT_USED,
    DEVELOPMENT_ALREADY_ADDED
}
