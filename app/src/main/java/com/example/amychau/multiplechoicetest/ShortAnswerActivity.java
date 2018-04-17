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
 * This class will add a new Short Answer Question to the question Database
 * It asks for accuracy to know how many decimal places it should be
 * Inserts the answer into the Answer Table, for the given question
 * The Option is the answer that can also be correct (to the correct decimal place)
 */
public class ShortAnswerActivity extends AppCompatActivity {

    private EditText question, accuracy, answer;
    private Button submit;
    private List<String> questionList, answerList, optionList, accuracyList;
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

        //Add a toolbar to the activity
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

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
        questionList = new ArrayList<>();
        answerList = new ArrayList<>();
        optionList = new ArrayList<>();
        accuracyList = new ArrayList<>();

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
                AlertDialog.Builder builder = new AlertDialog.Builder(ShortAnswerActivity.this);
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
