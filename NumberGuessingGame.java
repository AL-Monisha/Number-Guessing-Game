import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("=== NUMBER GUESSING GAME ===");
        
        boolean playing = true;
        while (playing) {
            // Setup game
            int secretNumber = random.nextInt(100) + 1;  // 1-100
            int attemptsLeft;
            boolean guessed = false;
            
            // Improved difficulty selection with input validation
            int choice = 0;
            while (choice < 1 || choice > 3) {
                System.out.println("\nChoose difficulty:");
                System.out.println("1. Easy (10 guesses)");
                System.out.println("2. Medium (7 guesses)");
                System.out.println("3. Hard (5 guesses)");
                System.out.print("Your choice (1-3): ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    if (choice < 1 || choice > 3) {
                        System.out.println("Invalid choice! Choose a number between 1-3.");
                    }
                } else {
                    System.out.println("Please enter a number between 1-3!");
                    scanner.next(); // Clear invalid input
                }
            }
            
            // Set attempts based on choice
            if (choice == 1) {
                attemptsLeft = 10;  // Easy
            } else if (choice == 2) {
                attemptsLeft = 7;   // Medium
            } else {
                attemptsLeft = 5;   // Hard
            }
            
            System.out.println("\nGuess a number between 1 and 100");
            
            // Game loop with input validation
            while (attemptsLeft > 0 && !guessed) {
                System.out.println("\nAttempts left: " + attemptsLeft);
                System.out.print("Your guess: ");
                
                if (scanner.hasNextInt()) {
                    int guess = scanner.nextInt();
                    
                    if (guess == secretNumber) {
                        guessed = true;
                        System.out.println("CORRECT! You won!");
                    } else if (guess < secretNumber) {
                        System.out.println("Too LOW");
                    } else {
                        System.out.println("Too HIGH");
                    }
                    attemptsLeft--;
                } else {
                    System.out.println("Please enter a valid number!");
                    scanner.next(); // Clear invalid input
                }
            }
            
            // Game end
            if (!guessed) {
                System.out.println("\nYou lost! The number was: " + secretNumber);
            }
            
            // Play again with input validation
            System.out.print("\nPlay again? (1=Yes, 0=No): ");
            if (scanner.hasNextInt()) {
                playing = (scanner.nextInt() == 1);
            } else {
                playing = false;
                scanner.next(); // Clear invalid input
            }
        }
        
        System.out.println("\nThanks for playing!");
        scanner.close();
    }
}