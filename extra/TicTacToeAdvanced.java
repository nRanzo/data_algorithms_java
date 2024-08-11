import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeAdvanced {
    public static final int X = 1, O = -1;  // identify the gamers
    public static final int EMPTY = 0;      // identify a free position
    private int[][] board;                  // board is now dynamic
    private int player;                     // which player has to play
    private int size;                       // size of the board
    private JButton[][] buttons;            // buttons for the GUI
    private JFrame frame;                   // main frame

    public TicTacToeAdvanced(int size) {
        this.size = size;
        board = new int[size][size];
        buttons = new JButton[size][size];
        clearBoard();
        createAndShowGUI();
    }

    public void clearBoard() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = EMPTY;
        player = X; // arbitrary choice
    }

    public void putMark(int i, int j) throws IllegalArgumentException {
        if (i < 0 || i >= size || j < 0 || j >= size)
            throw new IllegalArgumentException("Not a valid position");
        if (board[i][j] != EMPTY)
            throw new IllegalArgumentException("Board position already occupied");
        board[i][j] = player;
        buttons[i][j].setText(player == X ? "X" : "O");
        buttons[i][j].setEnabled(false);
        if (isWinner(player)) {
            JOptionPane.showMessageDialog(null, (player == X ? "X" : "O") + " wins!");
            resetGame();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(null, "It's a tie!");
            resetGame();
        } else {
            player = -player; // switch player
        }
    }

    public boolean isWinner(int mark) {
        int winCondition = mark * size;

        // Check rows and columns
        for (int i = 0; i < size; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += board[i][j];
                colSum += board[j][i];
            }
            if (rowSum == winCondition || colSum == winCondition)
                return true;
        }

        // Check diagonals
        int diag1Sum = 0, diag2Sum = 0;
        for (int i = 0; i < size; i++) {
            diag1Sum += board[i][i];
            diag2Sum += board[i][size - 1 - i];
        }
        return diag1Sum == winCondition || diag2Sum == winCondition;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == EMPTY)
                    return false;
        return true;
    }

    private void resetGame() {
        clearBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton("");
                final int row = i;
                final int col = j;
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            putMark(row, col);
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(frame, ex.getMessage());
                        }
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }

        // Add the board panel to the center
        frame.add(boardPanel, BorderLayout.CENTER);

        // Create and add a panel for color selection buttons
        JPanel colorPanel = new JPanel(new FlowLayout());
        String[] colors = {"White", "Black", "Light Blue", "Light Green"};
        for (String color : colors) {
            JButton colorButton = new JButton(color);
            colorButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    changeBackgroundColor(color, boardPanel);
                }
            });
            colorPanel.add(colorButton);
        }

        // Add the color panel to the bottom
        frame.add(colorPanel, BorderLayout.SOUTH);

        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    private void changeBackgroundColor(String colorName, JPanel boardPanel) {
        Color color;
        switch (colorName) {
            case "Black":
                color = Color.BLACK;
                break;
            case "Light Blue":
                color = Color.CYAN;
                break;
            case "Light Green":
                color = Color.GREEN;
                break;
            default:
                color = Color.WHITE;
                break;
        }
        boardPanel.setBackground(color);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setBackground(color);
            }
        }
    }

    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Enter the size of the TicTacToe board:");
        int size = Integer.parseInt(input);
        if (size < 3) {
            JOptionPane.showMessageDialog(null, "Size must be at least 3!");
            return;
        }
        new TicTacToeAdvanced(size);
    }
}
