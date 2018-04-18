package com.example.dell.tictactoe;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;
    TextView textViewPlayer1,textViewPlayer2,playerTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.settings);

        // using sharedPreference to retrive data
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });
        textViewPlayer1 = findViewById(R.id.player1);
        playerTurn = findViewById(R.id.playerTurn);
        textViewPlayer2 = findViewById(R.id.player2);

        if (player1Turn) {
            playerTurn.setText("Player1 turn");
        } else {
            playerTurn.setText("Player2 turn");
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!((Button) v).getText().toString().equals("")) {
                            return;
                        }

                        if (player1Turn) {
                            ((Button) v).setText("X");
                        } else {
                            ((Button) v).setText("O");
                        }

                        roundCount++;

                        if (checkForWin()) {
                            if (player1Turn) {
                                player1Wins();
                            } else {
                                player2Wins();
                            }
                        } else if (roundCount == 9) {
                            draw();
                        } else {
                            player1Turn = !player1Turn;
                        }

                    }

                    private boolean checkForWin() {
                        String[][] field = new String[3][3];

                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                field[i][j] = buttons[i][j].getText().toString();
                            }
                        }

                        for (int i = 0; i < 3; i++) {
                            if (field[i][0].equals(field[i][1])
                                    && field[i][0].equals(field[i][2])
                                    && !field[i][0].equals("")) {
                                return true;
                            }
                        }

                        for (int i = 0; i < 3; i++) {
                            if (field[0][i].equals(field[1][i])
                                    && field[0][i].equals(field[2][i])
                                    && !field[0][i].equals("")) {
                                return true;
                            }
                        }

                        if (field[0][0].equals(field[1][1])
                                && field[0][0].equals(field[2][2])
                                && !field[0][0].equals("")) {
                            return true;
                        }

                        if (field[0][2].equals(field[1][1])
                                && field[0][2].equals(field[2][0])
                                && !field[0][2].equals("")) {
                            return true;
                        }

                        return false;
                    }

                });
            }
        }

        FloatingActionButton buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }


    private void player1Wins() {
        player1Points++;
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.player1winner);
        dialog.show();
        dialog.setCancelable(true);
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.player2winner);
        dialog.show();
        dialog.setCancelable(true);
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.draw);
        dialog.show();
        dialog.setCancelable(true);
        resetBoard();
    }

    private void updatePointsText() {
        SharedPreferences sharedPreferences = getSharedPreferences("points",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user1points","player1: "+player1Points);
        editor.putString("user2points","player2: "+player2Points);
        editor.apply();
        editor.commit();

        SharedPreferences preferences = getSharedPreferences("points",MODE_PRIVATE);
        String user1PreferrenceName = preferences.getString("user1points","player1:0");
        String user2PreferrenceName = preferences.getString("user2points","player2:0");
        textViewPlayer1.setText(user1PreferrenceName);
        textViewPlayer2.setText(user2PreferrenceName);

    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        updatePointsText();
        resetBoard();
    }

}
