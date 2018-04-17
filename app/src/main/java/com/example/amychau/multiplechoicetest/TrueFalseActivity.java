package com.example.amychau.multiplechoicetest;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a true or false question and adds it to the question list
 * Creates the options for given question and adds it into the option list
 * Creates the answer for given question and adds it into the answer list
 */
public class TrueFalseActivity extends AppCompatActivity {

    private List<String> questionList, answerList, optionList;
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

        //Add a toolbar to the activity
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        dataSource = new QuestionDataSource(this);
        oDataSource = new OptionDataSource(this);
        aDataSource = new AnswerDataSource(this);
        dataSource.open();
        oDataSource.open();
        aDataSource.open();
        Log.i(ACTIVITY_NAME, "In onCreate()");

        question = findViewById(R.id.question);
        Button trueBtn = findViewById(R.id.trueBtn);
        Button falseBtn = findViewById(R.id.falseBtn);
        questionList = new ArrayList<>();
        optionList = new ArrayList<>();
        answerList = new ArrayList<>();

        optionList.add("true");
        optionList.add("false");

        trueBtn.setOnClickListener((view -> {
            Log.i("True Button", "Pressed");
            insertQuestion(question.getText().toString());
            answerList.add("true");

            insertAnswer("true", qid);
            question.setText("");
            Toast.makeText(this, "Question Added", Toast.LENGTH_LONG).show();
        }));

        falseBtn.setOnClickListener((view -> {
            Log.i("False Button", "Pressed");
            insertQuestion(question.getText().toString());
            answerList.add("false");

            insertAnswer("false", qid);
            question.setText("");
            Toast.makeText(this, "Question Added", Toast.LENGTH_LONG).show();
        }));

    }

    public void insertQuestion(String question){

        questionList.add(question);

        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestion(question);
        questionModel.setType("tf");
        questionModel = dataSource.create(questionModel);
        qid = questionModel.getQuestionID();

        insertOptions("true", qid);
        insertOptions("false", qid);
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
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        switch(mi.getItemId()){
            case R.id.patient:
                Log.d("Patient Form", "Selected");
                //Go to Patient Form Activity
                break;
            case R.id.movie:
                Log.d("Movie Information", "Selected");
                //Go to Movie Information Activity
                break;
            case R.id.transpo:
                Log.d("OC Transpo Information", "Selected");
                //Go to OC Transpo Activity
                break;
            case R.id.help:
                Log.d("Help Box", "Selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(TrueFalseActivity.this);
                builder.setTitle(R.string.help_menu)
                        .setMessage(R.string.help_info)
                        .setPositiveButton("OK", (dialogInterface, i) -> Log.d("User clicked", "OK")).show();
                break;
        }
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
}
