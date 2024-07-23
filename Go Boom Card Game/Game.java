import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Game implements Serializable {
    private Deck deck;
    private List<Card> currentCards;
    private List<List<Card>> hands;
    private List<String> playerNames;
    private List<Integer> scores;
    private Card winningCard;
    private int winningPlayer = -1;
    private int currentPlayerIndex = 0;
    private int turnIteration = 0;
    private int trickIteration = 1;
    private boolean exitGame = false;

    public Game(List<String> playerNames) {
        this.currentCards = new ArrayList<>();
        this.hands = new ArrayList<>();
        this.playerNames = playerNames;
        this.scores = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            scores.add(0);
        }
    }

    public void initializeGame() {
        deck = new Deck();
        deck.shuffle();

        for (int i = 0; i < playerNames.size(); i++) {
            List<Card> hand = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                hand.add(deck.drawCard());
            } // deal 7 cards to each player
            hands.add(hand);
        }

        currentCards.clear();  // Clear the currentCards list
        currentCards.add(deck.drawCard());  // Add a card to the currentCards list
        determineFirstPlayer();
    }

    private void determineFirstPlayer() {
        if (!currentCards.isEmpty()) {
            Card firstLeadCard = currentCards.get(currentCards.size() - 1);
            String rank = firstLeadCard.getRank();
            int firstPlayerIndex;

            if (rank.equals("A") || rank.equals("5") || rank.equals("9") || rank.equals("K")) {
                firstPlayerIndex = 0;
            } else if (rank.equals("2") || rank.equals("6") || rank.equals("10")) {
                firstPlayerIndex = 1;
            } else if (rank.equals("3") || rank.equals("7") || rank.equals("J")) {
                firstPlayerIndex = 2;
            } else if (rank.equals("4") || rank.equals("8") || rank.equals("Q")) {
                firstPlayerIndex = 3;
            } else {
                firstPlayerIndex = 0;
            }
            currentPlayerIndex = firstPlayerIndex;
        } else {
            currentPlayerIndex = 0;  // Set the first player index to 0 when currentCards is empty
        }
    }


    // check whether card played matches the suit or rank of lead card
    public boolean isValidMove(Card card) {
        Card currentCard = currentCards.get(0);
        return card.getSuit().equals(currentCard.getSuit()) || card.getRank().equals(currentCard.getRank());
    }

    public boolean playerNoValidMoves(int currentPlayer) {
        int card = 0;
        for (Card j : hands.get(currentPlayer)) {
            if (isValidMove(hands.get(currentPlayer).get(card)) == false) {
                card++;
            }
        }
        if (card == hands.get(currentPlayer).size()) {
            return true;
        } else
            return false;
    }

    // if draw deck is empty and player cannot play, skip
    public void skipPlayerTurn(int currentPlayer) {
        currentPlayerIndex++;
        turnIteration++;
        System.out.println(playerNames.get(currentPlayer) + " does not have any playable cards!");
        System.out.println("Skipping turn...");
    }

    int countCardScore(int currentPlayer) {
        int card = 0, totalScore = 0;
        for (Card i : hands.get(currentPlayer)) {
            totalScore = totalScore + hands.get(currentPlayer).get(card).getCardScore();
            card++;
        }
        return totalScore;
    }

    public void resetGame() {
        scores.clear();
        for (int i = 0; i < playerNames.size(); i++) {
            scores.add(0);
        }

        trickIteration = 1;
        turnIteration = 0;
        currentCards.clear();
        hands.clear();
        initializeGame();
        playGame();
        System.out.println("\n\n\n--------------------------------------------------------------------\n");
    }

    public void mapPlayerNames(Function<String, String> mapper) {
        List<String> newPlayerNames = new ArrayList<>();
        for (String playerName : playerNames) {
            newPlayerNames.add(mapper.apply(playerName));
        }
        playerNames = newPlayerNames;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n---------------------------- Game Start ----------------------------\n");

        determineFirstPlayer();

        // gameplay loop
        while (!exitGame) {
            int currentPlayer = currentPlayerIndex % playerNames.size();

            // display info of current player
            System.out.println(
                    "\n----------------------------- Trick #" + trickIteration + " -----------------------------");
            System.out.print("Center            : ");
            for (int i = 0; i < currentCards.size(); i++) {
                System.out.print(currentCards.get(i) + " ");
            }
            System.out.println(" ");
            for (int i = 0; i < playerNames.size(); i++) {
                if (i != currentPlayer)
                    System.out.println("    " + playerNames.get(i) + " hand : " + hands.get(i));
                else
                    System.out.println(" -> " + playerNames.get(i) + " hand : " + hands.get(i));
            }
            System.out.println("Score             : " + scores);
            System.out.print("Deck              : ");
            deck.showDeck();
            System.out.println(" ");
            System.out.println("Game Commands     : (r) reset game, (x) exit game, (s) save game\n");

            // turn loop
            boolean validMove = false;

            while (!validMove) {

                // if deck empty, check if player has valid moves
                if (deck.getDeckSize() == 0 && !currentCards.isEmpty()) {
                    if (playerNoValidMoves(currentPlayer) == true) {
                        skipPlayerTurn(currentPlayer);
                        validMove = false;
                        break;
                    }
                }

                System.out.println(playerNames.get(currentPlayer) + "'s turn");
                System.out.print("Enter a card to play (or 'd' to draw a card): ");
                String input = scanner.nextLine();

                // draw cards
                if (input.equalsIgnoreCase("d")) {
                    Card drawnCard = deck.drawCard();
                    if (drawnCard != null) {
                        hands.get(currentPlayer).add(drawnCard);
                        System.out.println("You drew a card: " + drawnCard);
                        validMove = true;
                    } else {
                        System.out.println("No cards left in the deck!");
                        validMove = false;
                    }

                    // quit game
                } else if (input.equalsIgnoreCase("x")) {
                    exitGame = true;
                    System.out.println("Exiting the game. Goodbye!");
                    break;

                    // reset game
                } else if (input.equalsIgnoreCase("r")) {
                    resetGame();
                }
                // save game
                else if (input.equalsIgnoreCase("s")) {
                    saveGame();
                    validMove = true;
                }
                // other inputs / player plays a card
                else {
                    Card selectedCard = null;
                    for (Card card : hands.get(currentPlayer)) {
                        if (card.toString().equalsIgnoreCase(input)) {
                            selectedCard = card;
                            break;
                        }
                    }

                    // if center is empty
                    if (currentCards.isEmpty()) {
                        if (selectedCard != null) {
                            hands.get(currentPlayer).remove(selectedCard);
                            currentCards.add(selectedCard);
                            System.out.println(playerNames.get(currentPlayer) + " played: " + selectedCard);
                            winningPlayer = currentPlayer;
                            winningCard = selectedCard;
                            currentPlayerIndex++;
                            validMove = true;
                            turnIteration++;

                        } else {
                            System.out.println("Invalid move! Try again.\n");
                        }

                        // if the suit or rank is the same as the first lead card
                    } else if ((selectedCard != null && isValidMove(selectedCard)
                            && selectedCard.getSuit().equals(currentCards.get(0).getSuit())
                            || (selectedCard != null && isValidMove(selectedCard)
                            && selectedCard.getRank().equals(currentCards.get(0).getRank())))) {
                        hands.get(currentPlayer).remove(selectedCard);
                        currentCards.add(selectedCard);
                        System.out.println(playerNames.get(currentPlayer) + " played: " + selectedCard);
                        // comparison to see if cards larger than the lead card/winning card
                        if (selectedCard.getSuit().equals(currentCards.get(0).getSuit())) {
                            if (winningPlayer == -1 || selectedCard.getCardValue() > winningCard.getCardValue()) {
                                winningPlayer = currentPlayer;
                                winningCard = selectedCard;
                            }
                        }

                        validMove = true;

                        currentPlayerIndex++;
                        turnIteration++;

                        // turn iteration counting
                        if (turnIteration >= playerNames.size()) {
                            currentCards.clear();
                            System.out.println(
                                    "\n" + playerNames.get(winningPlayer) + " has won trick #" + trickIteration + " !");
                            turnIteration = 0;
                            trickIteration++;
                            currentPlayerIndex = winningPlayer;
                        }

                    } else {
                        System.out.println("Invalid move! Try again.\n");
                    }

                    // If the player's hand is empty, they win the round, update the scores,
                    // break the game loop, and reinitialize game
                    if (hands.get(currentPlayer).size() == 0) {
                        System.out.println(playerNames.get(currentPlayer) + " has won the game!");
                        for (int i = 0; i < playerNames.size(); i++) {
                            if (currentPlayer == i) {
                                scores.set(i, scores.get(i) + 0);
                            } else {
                                scores.set(i, scores.get(i) + countCardScore(i));
                            }
                        }
                        turnIteration = 0;
                        trickIteration = 1;
                        currentCards.clear();
                        hands.clear();
                        initializeGame();
                        System.out.println("\n\n\n------------------------New Round-------------------------------\n");

                        break;

                    }

                }
            }
        }
        scanner.close();
    }

    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game.ser"))) {
            out.writeObject(this);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public static Game loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("game.ser"))) {
            Game game = (Game) in.readObject();
            System.out.println("Game loaded successfully.");
            return game;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    public int getPlayerCount() {
        return playerNames.size();
    }
}
