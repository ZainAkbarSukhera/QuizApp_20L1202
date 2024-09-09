package com.example.quizapp_20l1202;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    private String[] questions;
    private String[][] choices;
    private String[] correctAnswers;
    private int[] scoreChanges;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean answered = false;
    private long timerInMillis;
    private CountDownTimer countDownTimer;

    TextView questionTextView, scoreTextView, timeTextView;
    CheckBox chk1, chk2, chk3, chk4;
    Button nextButton, prevButton, showAnswerButton, endExamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questions = getResources().getStringArray(R.array.questions);
        correctAnswers = getResources().getStringArray(R.array.correctAnswers);

        choices = new String[][] {
                getResources().getStringArray(R.array.choices_q1),
                getResources().getStringArray(R.array.choices_q2),
                getResources().getStringArray(R.array.choices_q3),
                getResources().getStringArray(R.array.choices_q4),
                getResources().getStringArray(R.array.choices_q5),
                getResources().getStringArray(R.array.choices_q6),
                getResources().getStringArray(R.array.choices_q7),
                getResources().getStringArray(R.array.choices_q8),
                getResources().getStringArray(R.array.choices_q9),
                getResources().getStringArray(R.array.choices_q10),
                getResources().getStringArray(R.array.choices_q11),
                getResources().getStringArray(R.array.choices_q12),
                getResources().getStringArray(R.array.choices_q13),
                getResources().getStringArray(R.array.choices_q14),
                getResources().getStringArray(R.array.choices_q15),
                getResources().getStringArray(R.array.choices_q16),
                getResources().getStringArray(R.array.choices_q17),
                getResources().getStringArray(R.array.choices_q18),
                getResources().getStringArray(R.array.choices_q19),
                getResources().getStringArray(R.array.choices_q20)
        };

        scoreChanges = new int[questions.length];

        questionTextView = findViewById(R.id.question);
        scoreTextView = findViewById(R.id.score);
        timeTextView = findViewById(R.id.timer);

        chk1 = findViewById(R.id.checkBox1);
        chk2 = findViewById(R.id.checkBox2);
        chk3 = findViewById(R.id.checkBox3);
        chk4 = findViewById(R.id.checkBox4);

        nextButton = findViewById(R.id.nxtButton);
        prevButton = findViewById(R.id.prevButton);
        showAnswerButton = findViewById(R.id.showAnswerButton);
        endExamButton = findViewById(R.id.endButton);

        loadQuestion();

        // timer set for 3 minutes
        timerInMillis = 180000;
        startTimer();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    checkAnswer();
                    answered = true;
                }
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    loadQuestion();
                    answered = false;
                } else {
                    Toast.makeText(MainActivity.this, "Your exam has ended!", Toast.LENGTH_SHORT).show();
                    endExam();
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    removeScore();
                    loadQuestion();
                }
            }
        });

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Correct Answer: " + correctAnswers[currentQuestionIndex], Toast.LENGTH_SHORT).show();
                score--;
                scoreTextView.setText("Score: " + score);
            }
        });

        endExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endExam();
            }
        });
    }

    private void loadQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);

        chk1.setChecked(false);
        chk2.setChecked(false);
        chk3.setChecked(false);
        chk4.setChecked(false);

        chk1.setText(choices[currentQuestionIndex][0]);

        chk2.setText(choices[currentQuestionIndex][1]);

        chk3.setText(choices[currentQuestionIndex][2]);

        chk4.setText(choices[currentQuestionIndex][3]);

        scoreTextView.setText("Score: " + score);
    }

    private void checkAnswer() {
        String correctAnswer = correctAnswers[currentQuestionIndex];

        int scoreChange = calculateScore(currentQuestionIndex, correctAnswer);
        scoreChanges[currentQuestionIndex] = scoreChange;

        scoreTextView.setText("Score: " + score);
    };

    private void removeScore() {

            score -= scoreChanges[currentQuestionIndex];

            scoreChanges[currentQuestionIndex] = 0;

            scoreTextView.setText("Score: " + score);
    };


    private int calculateScore(int questionIndex, String correctAnswer) {
        int scoreChange = 0;

        if (chk1.isChecked()) {
            if (chk1.getText().toString().equals(correctAnswer)) {
                score += 5;
                scoreChange += 5;
            } else {
                score -= 1;
                scoreChange -= 1;
            }
        }

        if (chk2.isChecked()) {
            if (chk2.getText().toString().equals(correctAnswer)) {
                score += 5;
                scoreChange += 5;
            } else {
                score -= 1;
                scoreChange -= 1;
            }
        }

        if (chk3.isChecked()) {
            if (chk3.getText().toString().equals(correctAnswer)) {
                score += 5;
                scoreChange += 5;
            } else {
                score -= 1;
                scoreChange -= 1;
            }
        }

        if (chk4.isChecked()) {
            if (chk4.getText().toString().equals(correctAnswer)) {
                score += 5;
                scoreChange += 5;
            } else {
                score -= 1;
                scoreChange -= 1;
            }
        }

        return scoreChange;
    };

    private void startTimer() {
        countDownTimer = new CountDownTimer(timerInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerInMillis = millisUntilFinished;
                int minutes = (int) (timerInMillis / 1000) / 60;
                int seconds = (int) (timerInMillis / 1000) % 60;
                timeTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                endExam();
            }
        }.start();
    }

    private void endExam() {
        countDownTimer.cancel();

        new AlertDialog.Builder(this)
                .setTitle("Quiz Finished")
                .setMessage("Total Score: " + score + "\nPercentage: " + (score / (questions.length * 5.0) * 100) + "%")
                .setPositiveButton("Restart", (dialog, which) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadQuestion();
        startTimer();
    }
}
