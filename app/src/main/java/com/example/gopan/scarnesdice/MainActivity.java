package com.example.gopan.scarnesdice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int userScore,userTurnScore,computerScore,computerTurnScore;

    private ImageView diceImageView,winnerImageView;
    private Random random;
    private boolean userTurn=true;

    private Button rollButton,holdButton,resetButton;
    private TextView statusTextView, scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userScore = 0;
        userTurnScore = 0;
        computerScore = 0;
        computerTurnScore =0;

        random = new Random();

        rollButton = (Button) findViewById(R.id.button_roll);
        holdButton = (Button) findViewById(R.id.button_hold);
        resetButton = (Button) findViewById(R.id.button_reset);

        scoreTextView = (TextView) findViewById(R.id.text_view_score);
        statusTextView = (TextView) findViewById(R.id.text_view_status);

        diceImageView = (ImageView) findViewById(R.id.image_view_dice);
        winnerImageView = (ImageView) findViewById(R.id.winner_image);


        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusTextView.setText("");
                int num = rollDice();
                holdButton.setEnabled(true);
                if (num==1)
                {
                    userTurnScore = 0;
                    userTurn = false;
                    displayScore("You rolled a 1! Computer's Turn\n");
                    setComputerWinnerImage();
                    computerTurn();
                }
                else
                {
                    userTurnScore+=num;
                    displayScore("User's Turn\n");
                    setUserWinningImage();
                }


            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusTextView.setText("");
                userScore += userTurnScore;
                userTurn=false;
                displayScore("User Holds! Computer's Turn\n");
                userTurnScore = 0;
                computerTurn();

            }
        });

        reset(null);

    }
    private void computerTurn()
    {
        //check if computer's turn
        if(userTurn)return;
        //Disable user turn
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        //when computer starts rolling
        final Handler h = new Handler();
        computerTurnScore=0;
        Runnable r = new Runnable() {
            @Override
            public void run() {

                if(computerTurnScore<=20)
                {
                    int num=rollDice();
                    if(num!=1) //Reroll dice
                    {
                        computerTurnScore+=num;
                        displayScore("Computer's  turn\n");
                        setComputerWinnerImage();
                        h.postDelayed(this,1000);
                    }
                    else
                    {
                        computerTurnScore=0;
                        displayScore("Computer rolled a one! User's Turn\n");
                        setUserWinningImage();
                        userTurn=true;
                        rollButton.setEnabled(true);
                        holdButton.setEnabled(false);
                    }
                }
                else
                {
                    computerScore+=computerTurnScore;
                    userTurn=true;
                    displayScore("Computer holds,User's turn\n");
                    setUserWinningImage();
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(false);
                }


            }
        };
        h.postDelayed(r,1000);
    }

    public void reset(View view) {

        userTurnScore = 0;
        userScore = 0;
        computerTurnScore = 0;
        computerScore = 0;
        setNeutralImage();
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        userTurn = random.nextBoolean();
        if (userTurn){
            rollButton.setEnabled(true);
            displayScore("User's Turn\n");
        }
        else {
            displayScore("Computer's Turn\n");
            computerTurn();
        }

    }

    private void displayScore(String turnLabel) {

        if(userTurn == true)
        {

            scoreTextView.setText("User Score : " + userScore + " Computer Score : " + computerScore + " Your Turn Score : " + userTurnScore);
            statusTextView.setText(turnLabel);

        }
        else
        {

            scoreTextView.setText("User Score : " + userScore + " Computer Score : " + computerScore + " Computer Turn Score : " + computerTurnScore);
            statusTextView.setText(turnLabel);
        }
        if (userScore >=100)
        {
            turnLabel = "Game over!! You win";
            statusTextView.setText(turnLabel);
            scoreTextView.setText("");
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            setUserWinningImage();
        }
        else if(computerScore >= 100)
        {
            turnLabel = "Game Over!! Computer Wins";
            statusTextView.setText(turnLabel);
            scoreTextView.setText("");
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            setComputerWinnerImage();

        }
    }


    private int rollDice()
    {
        int number = random.nextInt(6)+1;
        setDice(number);
        Log.d("Roll Value:",Integer.toString(number));
        return number;
    }

    private void setUserWinningImage(){
        int drawableId;
        drawableId = R.drawable.happy;
        winnerImageView.setImageResource(drawableId);

    }

    private void setComputerWinnerImage(){
        int drawableId;
        drawableId = R.drawable.sad;
        winnerImageView.setImageResource(drawableId);
    }

    private void setNeutralImage(){
        int drawableId;
        drawableId = R.drawable.neutral;
        winnerImageView.setImageResource(drawableId);
    }


    public void setDice(int roll)
    {
        int diceImageID;
        switch(roll)
        {
            case 1:
                diceImageID = R.drawable.dice1;
                break;
            case 2:
                diceImageID = R.drawable.dice2;
                break;
            case 3:
                diceImageID = R.drawable.dice3;
                break;
            case 4:
                diceImageID = R.drawable.dice4;
                break;
            case 5:
                diceImageID = R.drawable.dice5;
                break;
            case 6:
                diceImageID = R.drawable.dice6;
                break;
            default:
                diceImageID = R.drawable.dice1;
        }

        diceImageView.setImageResource(diceImageID);

    }

}