import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public void displayMenu() {
        System.out.println("\n                     ╔════════════════════════╗");
        System.out.println("                     ║       Main Menu        ║");
        System.out.println("                     ╠════════════════════════╣");
        System.out.println("                     ║ (1) Start game         ║");
        System.out.println("                     ║ (2) Exit the game      ║");
        System.out.println("                     ╚════════════════════════╝");
    }

    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean validChoice = false;
    
        while (!validChoice) {
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
    
                if (choice == 1 || choice == 2) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.\n");
                }
    
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please try again.\n");
                scanner.nextLine(); // Clear the input buffer
            }
        }
        return choice;
    }
}    
