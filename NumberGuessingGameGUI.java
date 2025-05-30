import java.awt.*;
import java.util.Random;
import javax.swing.*;

// 🟢 Parent Class: Handles GUI setup
class GameFrameSetup {

    protected JFrame frame;
    protected JTextField guessField;
    protected JLabel messageLabel;
    protected JLabel attemptsLabel;
    protected JButton guessButton;
    protected JButton playAgainButton;
    protected JPanel mainPanel;

    public GameFrameSetup() {
        createGUI(); // GUI creation
    }

    protected void createGUI() {
        frame = new JFrame("Number Guessing Game");
        frame.setSize(550, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        frame.setContentPane(mainPanel);

        // Title
        JLabel titleLabel = new JLabel("Guess a number between 1 and 100");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Input field
        guessField = new JTextField(10);
        guessField.setMaximumSize(new Dimension(200, 30));
        guessField.setFont(new Font("Arial", Font.PLAIN, 16));
        guessField.setBackground(Color.BLACK);
        guessField.setForeground(Color.WHITE);
        guessField.setCaretColor(Color.WHITE);
        guessField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        guessField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(guessField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Guess button
        guessButton = new JButton("Guess");
        styleButton(guessButton);
        mainPanel.add(guessButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Message label
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Attempts label
        attemptsLabel = new JLabel(" ");
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        attemptsLabel.setForeground(Color.CYAN);
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(attemptsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Play Again button
        playAgainButton = new JButton("Play Again");
        styleButton(playAgainButton);
        playAgainButton.setVisible(false);
        mainPanel.add(playAgainButton);

        frame.setVisible(true);
    }

    protected void styleButton(JButton button) {
        button.setFont(new Font("Arial Black", Font.BOLD, 16));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

// 🟡 Child Class: Handles game logic
public class NumberGuessingGameGUI extends GameFrameSetup {

    private int secretNumber;
    private int attemptsLeft;

    public NumberGuessingGameGUI() {
        super(); // Call parent constructor to set up GUI

        // Button actions
        guessButton.addActionListener(e -> checkGuess());
        playAgainButton.addActionListener(e -> startNewGame());

        startNewGame(); // Start first game
    }

    private void startNewGame() {
        Random random = new Random();
        secretNumber = random.nextInt(100) + 1;

        // Show difficulty dialog
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI Emoji", Font.BOLD, 20));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI Emoji", Font.PLAIN, 18));
        UIManager.put("OptionPane.background", Color.BLACK);

        String[] options = {"Easy (10 tries)😀", "Medium (7 tries)😁", "Hard (5 tries)😎"};
        int choice = JOptionPane.showOptionDialog(frame,
                "Choose difficulty level:",
                "🎮 Difficulty Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        UIManager.put("OptionPane.messageFont", UIManager.getLookAndFeelDefaults().getFont("OptionPane.messageFont"));
        UIManager.put("OptionPane.buttonFont", UIManager.getLookAndFeelDefaults().getFont("OptionPane.buttonFont"));

        if (choice == 0) {
            attemptsLeft = 10;
        } else if (choice == 1) {
            attemptsLeft = 7;
        } else {
            attemptsLeft = 5;
        }

        messageLabel.setText("🎯 New game started! Make your guess.");
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        guessField.setText("");
        guessButton.setEnabled(true);
        playAgainButton.setVisible(false);
        guessField.requestFocusInWindow();
    }

    private void checkGuess() {
        String input = guessField.getText().trim();

        try {
            int guess = Integer.parseInt(input);
            int difference = Math.abs(secretNumber - guess);

            if (guess == secretNumber) {
                messageLabel.setText("🎉 Correct! You won! 🎯");
                guessButton.setEnabled(false);
                playAgainButton.setVisible(true);
            } else if (difference == 1) {
                messageLabel.setText("😬 Too Close!");
            } else if (difference <= 5) {
                messageLabel.setText("🙂 Almost There!");
            } else if (guess < secretNumber) {
                messageLabel.setText("⬆️ Too Low!");
            } else {
                messageLabel.setText("⬇️ Too High!");
            }

            attemptsLeft--;
            attemptsLabel.setText("Attempts left: " + attemptsLeft);

            if (attemptsLeft == 0 && guess != secretNumber) {
                messageLabel.setText("😢 You lost! Number was: " + secretNumber);
                guessButton.setEnabled(false);
                playAgainButton.setVisible(true);
            }

            guessField.setText("");
            guessField.requestFocusInWindow();
        } catch (NumberFormatException e) {
            messageLabel.setText("⚠️ Enter a valid number!");
        }
    }

    public static void main(String[] args) {
        // Set cross-platform look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        new NumberGuessingGameGUI(); // Run the game
    }
}

