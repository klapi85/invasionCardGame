# invasionCardGame
Card game inspired by the collectible card game [Warhammer: Invasion](https://en.wikipedia.org/wiki/Warhammer:_Invasion) relesed from 2009 to 2013.

It's a duel card game where one player tries to defeat another one.
Original game is vast complicated and every single card can add new rules.
This project is just a simple demo of this and shows possibility of the full game.

This project aims to promote Warhammer: Invasion with just a small set of cards implemented.

Status: Early Work in progress.

## Interface

After lauching the game a command line interpreter is available.

Commands:
- play first card from hand to kingdom area: `p k 0`
- play second card from hand to battlefield area: `p b 1`
- quit: `q`

## Features
- cards can be tapped and untapped
- cards have power and defence; power can be increased
- putting cards on table areas from hand
- taking card to hand from table areas
- zones on the table: Battlefield, Kingdom, Mission
- development cards can be put on the table (starting with 0D)
- quit game

## Todo
- game turns
- fights
- tactics cards (in progress)

## Launching

With Gradle (no Kotlin compiler install needed):

`./gradlew run -q --console=plain`

or build a runnable jar and start it:

`./gradlew build`

`java -jar build/libs/invasion.jar`

Run the tests with `./gradlew test`.

Without Gradle:

`kotlinc $(find src/game -name '*.kt') -include-runtime -d invasion.jar`

`java -jar invasion.jar`

You should see the start of the game:
```
Start:
Turn: 1 phase: START
Resources left: 3
• Battlefield: ( + 0) 0D

• Mission: (1 + 0) 0D

• Kingdom: (3 + 0) 0D

• Hand:
   Minotaur _ UNIT 2P 2D 2$ | Sanctuary _ SUPPORT 1P 1$ | Minotaur _ UNIT 2P 2D 2$ |
Your move? (h for help)
```