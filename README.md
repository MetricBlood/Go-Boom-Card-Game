# Go Boom Card Game

An implementation of the card game Go Boom in Java, with a text menu and a full game loop.

## What it does

- Builds and shuffles a standard deck, deals hands and manages the draw pile
- Enforces the rules of play, including which cards may legally be played on a given trick
- Runs a complete game to a win condition
- Menu-driven, so a game can be started, played and restarted without relaunching

## How it is built

The classes map onto the parts of the game rather than onto the flow of the program:

| Class | Responsibility |
|---|---|
| `Card` | A single card, its suit and rank, and how it compares to others |
| `Deck` | Building, shuffling and dealing; owns the draw pile |
| `Game` | Turn order, rule enforcement and win conditions |
| `Menu` | Text interface and user input |
| `RunGame` | Entry point |

Keeping rules inside `Game` and card behaviour inside `Card` means the rules can be changed without touching how cards are represented.

## Running it

```bash
javac *.java
java RunGame
```

## Built with

Java
