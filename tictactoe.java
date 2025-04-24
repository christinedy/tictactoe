import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//Tic Tac Toe is fun!
public class tictactoe implements ActionListener {
    private JFrame frame;
    private JPanel gamePanel;
    private JLabel statusLabel;
    private JButton[] buttons = new JButton[9];
    private JButton resetButton;
    private boolean xTurn = true;

    public tictactoe() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 520);
        frame.setLocationRelativeTo(null); 

        // top status label
        statusLabel = new JLabel("X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        statusLabel.setForeground(Color.BLACK);
        frame.add(statusLabel, BorderLayout.NORTH);

        // main 3x3 button grid
        gamePanel = new JPanel(new GridLayout(3, 3, 5, 5));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Segoe UI", Font.BOLD, 48));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            gamePanel.add(buttons[i]);
        }
        frame.add(gamePanel, BorderLayout.CENTER);

        // new game button
        resetButton = new JButton("New Game");
        resetButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        resetButton.addActionListener(e -> resetGame());
        frame.add(resetButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (!btn.getText().equals("")) return;

        btn.setText(xTurn ? "X" : "O");
        btn.setEnabled(false);
        if (checkForWinner()) {
            statusLabel.setText((xTurn ? "X" : "O") + " Wins!");
            disableAllButtons();
        } else if (isBoardFull()) {
            statusLabel.setText("It's a Tie!");
        } else {
            xTurn = !xTurn;
            statusLabel.setText((xTurn ? "X" : "O") + "'s Turn");
        }
    }

    private boolean checkForWinner() {
        int[][] combos = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] combo : combos) {
            String a = buttons[combo[0]].getText();
            String b = buttons[combo[1]].getText();
            String c = buttons[combo[2]].getText();
            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                // winning buttons highlights
                buttons[combo[0]].setBackground(Color.GREEN);
                buttons[combo[1]].setBackground(Color.GREEN);
                buttons[combo[2]].setBackground(Color.GREEN);
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (JButton btn : buttons) {
            if (btn.getText().equals("")) return false;
        }
        return true;
    }

    private void disableAllButtons() {
        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }
    }

    private void resetGame() {
        for (JButton btn : buttons) {
            btn.setText("");
            btn.setEnabled(true);
            btn.setBackground(null);
        }
        xTurn = true;
        statusLabel.setText("X's Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(tictactoe::new);
    }
}
