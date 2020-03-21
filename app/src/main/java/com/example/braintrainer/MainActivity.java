package com.example.braintrainer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView secondTextView;
    TextView questionTextView;
    TextView scoreTextView;
    GridLayout gridLayout;
    TextView checkTextView;
    Button playAgainButton;


    private boolean isPlaying;
    private int questionCounter;
    private int correctAnswerCounter;


    public void play(View view) {
        isPlaying = true;
        handleUI();
        timeout(30);
        generateQuestion();
    }

    public void playAgain(View view) {
        isPlaying = true;
        questionCounter = 0;
        correctAnswerCounter = 0;

        handleUI();
        timeout(30);

        generateQuestion();

    }

    public void handleAnswer(View view) {
        // getting the clicked button and check the answer
        Button button = (Button) view;
        if (questionTextView.getTag() == button.getTag()) {
            correctAnswerCounter++;
            checkTextView.setText("Correct");
        } else {
            checkTextView.setText("Wrong");
        }

        // then generating new question
        generateQuestion();

    }

    public void handleScore() {
        scoreTextView.setText(correctAnswerCounter + "/" + questionCounter);
    }

    public void generateQuestion() {
        handleScore();

        Random random = new Random();
        int num1 = random.nextInt(98) + 1;
        int num2  = random.nextInt(98) + 1;
        int correctAnswer = num1 + num2;
        generateAnswers(correctAnswer);

        questionTextView.setText(num1 + " + " + num2);
        questionTextView.setTag(correctAnswer);
        questionCounter++;
    }

    public void generateAnswers(int correctAnswer) {
        Random random = new Random();

        // randomly choosing a button and put the correct answer
        int locationOfCorrectAnswer = random.nextInt(4);

        for (int i=0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            if (i == locationOfCorrectAnswer) {
                button.setText(String.valueOf(correctAnswer));
                button.setTag(correctAnswer);
            } else {
                int salt = random.nextInt(correctAnswer);
                int wrongAnswer = correctAnswer - salt;
                while (wrongAnswer == correctAnswer) {
                    wrongAnswer = (int) ((Math.random() * (correctAnswer+salt)) + correctAnswer);
                }
                button.setText(String.valueOf(correctAnswer - salt));
                button.setTag(correctAnswer - salt);
            }
        }
    }

    public void handleUI() {
        buttonsClickable(true);
        startButton.setVisibility(View.INVISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
        secondTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        checkTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        checkTextView.setText("");
    }

    public void timeout(final int seconds) {
        CountDownTimer countDownTimer = new CountDownTimer(((seconds * 1000)+100), 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                secondTextView.setText((millisUntilFinished /1000) + "s");
            }

            @Override
            public void onFinish() {
                isPlaying = false;
                checkTextView.setText("Your score: " + scoreTextView.getText());
                playAgainButton.setVisibility(View.VISIBLE);
                buttonsClickable(false);
            }
        }.start();
    }

    public void buttonsClickable(boolean clickable) {
        for (int i=0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setClickable(clickable);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // handling defaults
        startButton = (Button) findViewById(R.id.startButton);
        secondTextView = (TextView) findViewById(R.id.secondTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        checkTextView = (TextView) findViewById(R.id.checkTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        scoreTextView.setText("0/0");

        isPlaying = false;
        questionCounter = 0;
        correctAnswerCounter = 0;


    }
}
