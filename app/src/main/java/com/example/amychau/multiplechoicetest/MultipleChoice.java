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
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a multiple choice question and adds it to the question list
 * Creates the options for given question and adds it into the option list
 * Creates the answer for given question and adds it into the answer list
 */
public class MultipleChoice extends AppCompatActivity {

    private EditText question, optionOne, optionTwo, optionThree, optionFour, answer;
    private List<String> questionList, optionList, answerList;
    private final String ACTIVITY_NAME = "MultipleChoice";
    private Toolbar toolbar;

    QuestionDataSource dataSource;
    OptionDataSource oDataSource;
    AnswerDataSource aDataSource;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        //Add a toolbar to the activity
        toolbar = findViewById(R.id.main_toolbar);
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

        Button submit = findViewById(R.id.submit);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MultipleChoice.this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
