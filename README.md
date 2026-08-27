# Go Boom Card Game

An implementation of the card game Go Boom in Java, with rule enforcement, scoring across tricks, save and load, and a menu-driven game loop.

## What it does

- Builds and shuffles a standard 52-card deck, deals hands and manages the draw pile
- **Enforces the rules of play** — a move is checked for legality before it is accepted, and a player with no legal move has their turn skipped automatically
- Tracks scores across tricks and plays through to a win condition
- **Save and load** — a game in progress is serialised to disk and can be resumed later
- Menu-driven, so a game can be started, loaded or restarted without relaunching

## How it is built

The classes map onto the parts of the game rather than onto the flow of the program:

| Class | Responsibility |
|---|---|
| `Card` | Suit, rank and value, and comparison between cards |
| `Deck` | Builds all 52 cards, shuffles, deals and draws; owns the draw pile |
| `Game` | Turn order, legal-move checking, trick resolution, scoring, win conditions, and save/load |
| `Menu` | Text interface and validated user input |
| `RunGame` | Entry point; extends `Menu` and drives the new-game and load-game flow |

Keeping rule logic inside `Game` and card behaviour inside `Card` means the rules can change without touching how cards are represented.

`Card`, `Deck` and `Game` all implement `Serializable`, which is what makes the save feature a few lines rather than a bespoke file format.

## Running it

```bash
javac *.java
java RunGame
```

## Built with

Java, Java serialisation
