package com.example.amychau.multiplechoicetest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class TrueFalseActivity extends AppCompatActivity {

    private Button trueBtn, falseBtn;
    private List<String> questionList;
    private List<String> answerList;
    private List<String> optionList;
    private DatabaseHelper dbHelper;
    private long qid = 0;
    private EditText question;

    private final String ACTIVITY_NAME = "TrueFalse";

    QuestionDataSource dataSource;
    OptionDataSource oDataSource;
    AnswerDataSource aDataSource;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.true_false);

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
        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);
        questionList = new ArrayList<String>();
        optionList = new ArrayList<String>();
        answerList = new ArrayList<String>();

        optionList.add("true");
        optionList.add("false");

        trueBtn.setOnClickListener((view -> {
            Log.i("True Button", "Pressed");
            insertQuestion(question.getText().toString());
            answerList.add("true");

            insertAnswer("true", qid);
            question.setText("");
        }));

        falseBtn.setOnClickListener((view -> {
            Log.i("False Button", "Pressed");
            insertQuestion(question.getText().toString());
            answerList.add("false");

            insertAnswer("false", qid);
            question.setText("");
        }));

    }

    public long insertQuestion(String question){

        questionList.add(question);

        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestion(question);
        questionModel.setType("tf");
        questionModel = dataSource.create(questionModel);
        qid = questionModel.getQuestionID();

        insertOptions("true", qid);
        insertOptions("false", qid);

        return qid;
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
}
