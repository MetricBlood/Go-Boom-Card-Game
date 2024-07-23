import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class RunGame extends Menu {
    private Game game;
    private List<String> playerNames;
    private Scanner scanner;

    public RunGame(List<String> playerNames) {
        this.playerNames = playerNames;
        this.game = new Game(playerNames);
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        displayMenu();
        int choice = getUserChoice();
    
        switch (choice) {
            case 1:
                System.out.println("\n1. Load game");
                System.out.println("2. New game");
    
                int loadChoice = 0;
                boolean validLoadChoice = false;
    
                while (!validLoadChoice) {
                    try {
                        System.out.print("Enter your choice: ");
                        loadChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
    
                        if (loadChoice == 1 || loadChoice == 2) {
                            validLoadChoice = true;
                        } else {
                            System.out.println("Invalid choice. Please enter 1 or 2.\n");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid choice. Please try again.\n");
                        scanner.nextLine(); // Clear the input buffer
                    }
                }
    
                if (loadChoice == 1) {
                    game = Game.loadGame();
                    if (game != null) {
                        System.out.println("Loaded saved game.");
                    } else {
                        System.out.println("No saved game found. Starting a new game.");
                        game = new Game(playerNames);
                        game.initializeGame();
                    }
                } else if (loadChoice == 2) {
                    System.out.println("Starting a new game...");
                    game = new Game(playerNames);
                    game.initializeGame();
                }
    
                game.playGame();
                break;
            case 2:
                System.out.println("Exiting the game. Goodbye!");
                break;
            default:
                startGame();
                break;
        }
    }
    public static void main(String[] args) {
        List<String> playerNames = List.of("Player 1", "Player 2", "Player 3", "Player 4");
        RunGame runGame = new RunGame(playerNames);
        runGame.startGame();
    }
}
