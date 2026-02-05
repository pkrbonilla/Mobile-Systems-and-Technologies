package com.example.tictactoe; // SIGURADUHIN NA TAMA ANG PACKAGE NAME MO RITO

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private String[] board = new String[9];
    private boolean isOTurn = true; // Based on image, 'O' starts first
    private boolean gameOver = false;
    private TextView tvStatus;

    // Professional Color Palette based on your image
    private final int COLOR_O = Color.parseColor("#EC407A"); // Pink
    private final int COLOR_X = Color.parseColor("#5C6BC0"); // Blue-Purple
    private final int COLOR_WINNER = Color.parseColor("#673AB7"); // Deep Purple
    private final int COLOR_DEFAULT = Color.parseColor("#BDBDBD"); // Gray

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        Button btnReset = findViewById(R.id.btnReset);

        // Map buttons using a loop (Clean Code Approach)
        for (int i = 0; i < 9; i++) {
            String buttonID = "btn" + i; // Dapat btn0, btn1... btn8
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);

            final int index = i;
            buttons[i].setOnClickListener(v -> handleMove(index));
        }

        btnReset.setOnClickListener(v -> resetGame());
    }

    private void handleMove(int index) {
        if (!gameOver && board[index] == null) {
            if (isOTurn) {
                board[index] = "O";
                buttons[index].setText("O");
                buttons[index].setBackgroundColor(COLOR_O);
                tvStatus.setText("Player X's Turn");
                tvStatus.setTextColor(COLOR_X);
            } else {
                board[index] = "X";
                buttons[index].setText("X");
                buttons[index].setBackgroundColor(COLOR_X);
                tvStatus.setText("Player O's Turn");
                tvStatus.setTextColor(COLOR_O);
            }

            isOTurn = !isOTurn;
            checkWinner();
        }
    }

    private void checkWinner() {
        int[][] winPositions = {
                {0,1,2}, {3,4,5}, {6,7,8}, // Rows
                {0,3,6}, {1,4,7}, {2,5,8}, // Columns
                {0,4,8}, {2,4,6}           // Diagonals
        };

        for (int[] pos : winPositions) {
            if (board[pos[0]] != null &&
                    board[pos[0]].equals(board[pos[1]]) &&
                    board[pos[0]].equals(board[pos[2]])) {

                gameOver = true;
                showResult("Player " + board[pos[0]] + " Won!");
                return;
            }
        }

        // Check for Draw logic
        boolean fullBoard = true;
        for (String cell : board) {
            if (cell == null) { fullBoard = false; break; }
        }

        if (fullBoard) {
            gameOver = true;
            showResult("Draw! Try Again");
        }
    }

    private void showResult(String message) {
        tvStatus.setText(message);
        tvStatus.setTextColor(COLOR_WINNER);
        // Disable remaining empty buttons for polish
        for (Button b : buttons) {
            if (b.getText().toString().equals("")) b.setEnabled(false);
        }
    }

    private void resetGame() {
        gameOver = false;
        isOTurn = true;
        tvStatus.setText("Player O's Turn");
        tvStatus.setTextColor(COLOR_O);

        for (int i = 0; i < 9; i++) {
            board[i] = null;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackgroundColor(COLOR_DEFAULT);
        }
    }
}