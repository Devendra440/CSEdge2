import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3]; // 3x3 grid of buttons
    private boolean playerXTurn = true; // Keep track of whose turn it is
    private JLabel statusLabel; // Status label to show messages

    public TicTacToeGame() {
        // Set up the main frame
        setTitle("Tic-Tac-Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the 3x3 grid
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));

        // Initialize the buttons and add them to the panel
        Font buttonFont = new Font("SansSerif", Font.BOLD, 60);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttonFont);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                gridPanel.add(buttons[i][j]);
            }
        }

        // Create a status label to show the current game status
        statusLabel = new JLabel("Player X's Turn", JLabel.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        
        // Add components to the frame
        add(statusLabel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);

        setVisible(true); // Make the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        // If the button is already clicked, do nothing
        if (!clickedButton.getText().equals("")) {
            return;
        }

        // Set the button's text to 'X' or 'O' depending on the current turn
        if (playerXTurn) {
            clickedButton.setText("X");
            statusLabel.setText("Player O's Turn");
        } else {
            clickedButton.setText("O");
            statusLabel.setText("Player X's Turn");
        }

        // Check for a winner or if the game is a draw
        if (checkForWinner()) {
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + " Wins!");
            disableButtons(); // Disable all buttons since the game is over
        } else if (isBoardFull()) {
            statusLabel.setText("It's a Draw!");
        } else {
            playerXTurn = !playerXTurn; // Switch turns
        }
    }

    // Check if there's a winner
    private boolean checkForWinner() {
        // Check rows, columns, and diagonals for a winner
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2])) return true; // Rows
            if (checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) return true; // Columns
        }
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2])) return true; // Diagonal 1
        if (checkLine(buttons[0][2], buttons[1][1], buttons[2][0])) return true; // Diagonal 2
        return false;
    }

    // Helper method to check if three buttons in a line are the same and not empty
    private boolean checkLine(JButton b1, JButton b2, JButton b3) {
        return b1.getText().equals(b2.getText()) &&
               b2.getText().equals(b3.getText()) &&
               !b1.getText().equals("");
    }

    // Check if all buttons have been clicked (i.e., the board is full)
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    // Disable all buttons once the game is over
    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        new TicTacToeGame();
    }
}
