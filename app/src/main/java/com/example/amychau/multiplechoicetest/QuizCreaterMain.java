package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This class is the main page of the Quiz Creator
 * Users can choose to create question, view the question list, or import XML file
 */
public class QuizCreaterMain extends Activity implements AppCompatCallback {

    Button createQuestion, questionList, importXml, stats;
    String ACTIVITY_NAME = "Quiz Creator";
    private AppCompatDelegate delagate;

    /**
     * While creating the activity, it will load a toolbar
     * where users can switch between the different activities
     * and four buttons that users can choose to add different types of questions
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Add toolbar to activity
        delagate = AppCompatDelegate.create(this, this);
        delagate.installViewFactory();
        super.onCreate(savedInstanceState);
        delagate.onCreate(savedInstanceState);
        delagate.setContentView(R.layout.activity_quiz_creater_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        delagate.setSupportActionBar(toolbar);

        Log.i(ACTIVITY_NAME, "In OnCreate()");

        createQuestion = findViewById(R.id.mc);
        questionList = findViewById(R.id.questionList);
        importXml = findViewById(R.id.importQuestion);
        stats = findViewById(R.id.stats);

        //Start the activity once the respective buttons are pressed
        createQuestion.setOnClickListener(
                (view1) -> {
                    Intent intent = new Intent(QuizCreaterMain.this, quizCreator.class);
                    startActivity(intent);
                }
        );

        questionList.setOnClickListener(
                (view4) -> {
                    Intent intent = new Intent(QuizCreaterMain.this, Questions.class);
                    startActivity(intent);
                }
        );

        importXml.setOnClickListener(
                (view5) -> {
                    Intent intent = new Intent(QuizCreaterMain.this, importQuestion.class);
                    startActivity(intent);
                }
        );

        stats.setOnClickListener(
                (view6) -> {
                    Intent intent = new Intent(QuizCreaterMain.this, statistics.class);
                    startActivity(intent);
                }
        );

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
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizCreaterMain.this);
               builder.setTitle(R.string.help_menu)
                       .setMessage(R.string.help_info)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               Log.d("User clicked", "OK");
                           }
                       }).show();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In OnResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In OnStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In OnPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In OnStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In OnDestroy()");
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
