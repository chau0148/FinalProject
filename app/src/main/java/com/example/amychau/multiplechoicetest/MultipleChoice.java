package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MultipleChoice extends Activity {

    private Questions questions = new Questions();

    private TextView scoreView;
    private TextView questionView;
    private Button choiceOne;
    private Button choiceTwo;
    private Button choiceThree;
    private Button choiceFour;
    private String answer;
    private int scoreNumber = 0;
    private int questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        //Connect variables to XML widgets
        scoreView = findViewById(R.id.score);
        questionView = findViewById(R.id.question);
        choiceOne = findViewById(R.id.choice1);
        choiceTwo = findViewById(R.id.choice2);
        choiceThree = findViewById(R.id.choice3);
        choiceFour = findViewById(R.id.choice4);

        updateQuestion();

        //Clicking on Button One (Choice One)
        choiceOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choiceOne.getText()==answer){
                    scoreNumber += 1;
                    updateScore(scoreNumber);
                    updateQuestion();
                    Toast.makeText(MultipleChoice.this, "correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoice.this, "incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Clicking on Button Two (Choice Two)
        choiceTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choiceTwo.getText()==answer){
                    scoreNumber += 1;
                    updateScore(scoreNumber);
                    updateQuestion();
                    Toast.makeText(MultipleChoice.this, "correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MultipleChoice.this, "incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateQuestion(){
        questionView.setText(questions.getQuestions(questionNumber));
        choiceOne.setText(questions.getChoice1(questionNumber));
        choiceTwo.setText(questions.getChoice2(questionNumber));
        choiceThree.setText(questions.getChoice3(questionNumber));
        choiceFour.setText(questions.getChoice4(questionNumber));
        answer = questions.getCorrectAnswer(questionNumber);
        questionNumber++;
    }

    private void updateScore(int score){
        scoreNumber = score;
        scoreView.setText(""+ score);
    }
}
