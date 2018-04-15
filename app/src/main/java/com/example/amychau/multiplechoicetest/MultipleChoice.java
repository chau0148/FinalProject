package com.example.amychau.multiplechoicetest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.List;

/**
 * NOTE: This is just to test if the M/C works. I will be replacing the questions and code later on
 * Questions will be on XML and will be read off from there.
 */
public class MultipleChoice extends AppCompatActivity {

    private Button submit;
    private EditText question, optionOne, optionTwo, optionThree, optionFour, answer;
    private List<String> questionList;
    private List<String> optionList;
    private List<String> answerList;
    private DatabaseHelper dbHelper;
    private final String ACTIVITY_NAME = "MultipleChoice";

    QuestionDataSource dataSource;
    OptionDataSource oDataSource;
    AnswerDataSource aDataSource;

    SQLiteDatabase db;
 //   ArrayAdapter<QuestionModel> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        dataSource = new QuestionDataSource(this);
        oDataSource = new OptionDataSource(this);
        aDataSource = new AnswerDataSource(this);
        dataSource.open();
        oDataSource.open();
        aDataSource.open();
        Log.i(ACTIVITY_NAME, "In onCreate()");

        submit = findViewById(R.id.submit);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answerHere);
        optionOne = findViewById(R.id.choice1);
        optionTwo = findViewById(R.id.choice2);
        optionThree = findViewById(R.id.choice3);
        optionFour = findViewById(R.id.choice4);
        questionList = new ArrayList<>();
        optionList = new ArrayList<>();
        answerList = new ArrayList<>();

        submit.setOnClickListener((view) -> {
            Log.i("Submit Button", "Pressed");
            questionList.add(question.getText().toString());

            optionList.add(optionOne.getText().toString());
            optionList.add(optionTwo.getText().toString());
            optionList.add(optionThree.getText().toString());
            optionList.add(optionFour.getText().toString());

            answerList.add(answer.getText().toString());

            QuestionModel questionModel = new QuestionModel();
            questionModel.setQuestion(question.getText().toString());
            questionModel.setType("m");
            questionModel = dataSource.create(questionModel);
            long id = questionModel.getQuestionID();

//            OptionModel optionModel = new OptionModel();
//            long oId = optionModel.getId();
            insertAnswer(answer.getText().toString(), id);

            insertOptions(optionOne.getText().toString(), id);
            insertOptions(optionTwo.getText().toString(), id);
            insertOptions(optionThree.getText().toString(), id);
            insertOptions(optionFour.getText().toString(), id);

            question.setText("");
            optionOne.setText("");
            optionTwo.setText("");
            optionThree.setText("");
            optionFour.setText("");
            answer.setText("");
        });
    }

    public void insertOptions(String option, long quesId){
        OptionModel optionModel = new OptionModel();
        optionModel.setOptions(option);
        optionModel.setQuestionID(quesId);
        oDataSource.create(optionModel);
    }

    public void insertAnswer(String answer, long quesId){
        AnswerModel answerModel = new AnswerModel();
        answerModel.setAnswer(answer);
        answerModel.setQuestionID(quesId);
        aDataSource.create(answerModel);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }
}
