package com.example.amychau.multiplechoicetest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ShortAnswerActivity extends AppCompatActivity {

    private EditText question, accuracy, answer;
    private Button submit;
    private List<String> questionList;
    private List<String> answerList;
    private List<String> optionList;
    private List<String> accuracyList;
    private DatabaseHelper dbHelper;
    private final String ACTIVITY_NAME = "Numeric Answer";


    QuestionDataSource dataSource;
    OptionDataSource oDataSource;
    AnswerDataSource aDataSource;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.short_answer);

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        dataSource = new QuestionDataSource(this);
        oDataSource = new OptionDataSource(this);
        aDataSource = new AnswerDataSource(this);
        dataSource.open();
        oDataSource.open();
        aDataSource.open();
        Log.i(ACTIVITY_NAME, "In onCreate()");

        question = findViewById(R.id.question);
        accuracy = findViewById(R.id.accuracy);
        submit = findViewById(R.id.submit);
        answer = findViewById(R.id.answer);
        questionList = new ArrayList<String>();
        answerList = new ArrayList<String>();
        optionList = new ArrayList<String>();
        accuracyList = new ArrayList<String>();

        submit.setOnClickListener((view -> {
            Log.i("Submit Button", "Pressed");
            questionList.add(question.getText().toString());
            answerList.add(answer.getText().toString());
            optionList.add(answer.getText().toString());
            accuracyList.add(accuracy.getText().toString());

            QuestionModel questionModel = new QuestionModel();
            questionModel.setQuestion(question.getText().toString());
            questionModel.setType("num");
            questionModel = dataSource.create(questionModel);
            long id = questionModel.getQuestionID();
            String acc = accuracy.getText().toString();

            insertOptions(answer.getText().toString(), id);

            insertAnswer(answer.getText().toString(), id, acc);
            question.setText("");
            answer.setText("");
            accuracy.setText("");
        }));

    }

    public void insertOptions(String option, long quesId){
        OptionModel optionModel = new OptionModel();
        optionModel.setOptions(option);
        optionModel.setQuestionID(quesId);
        oDataSource.create(optionModel);
    }

    public void insertAnswer(String answer, long quesId, String acc){
        AnswerModel answerModel = new AnswerModel();
        answerModel.setAnswer(answer);
        answerModel.setQuestionID(quesId);
        answerModel.setAccuracy(acc);
        aDataSource.create(answerModel);
    }
}
