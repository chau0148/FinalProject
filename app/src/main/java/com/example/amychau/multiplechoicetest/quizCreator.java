package com.example.amychau.multiplechoicetest;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/**
 * This class creates the questions for the quiz
 */
public class quizCreator extends AppCompatActivity {

    Button mcBtn;
    Button tfBtn;
    Button shortAnsBtn;
    String ACTIVITY_NAME = "Quiz Creator";
    private Toolbar toolbar;

    /**
     * While creating the activity, it will load a toolbar
     * where users can switch between the different activities
     * and four buttons that users can choose to add different types of questions
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_creator);

        //Add a toolbar to the activity
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        Log.i(ACTIVITY_NAME, "In OnCreate()");

        mcBtn = findViewById(R.id.mc);
        tfBtn = findViewById(R.id.tf);
        shortAnsBtn = findViewById(R.id.shortAns);

        //Start the activity once the respective buttons are pressed
        mcBtn.setOnClickListener(
                (view1) -> {
                    Intent intent = new Intent(quizCreator.this, MultipleChoice.class);
                    startActivity(intent);
                }
        );

        tfBtn.setOnClickListener(
                (view2) -> {
                    Intent intent = new Intent(quizCreator.this, TrueFalseActivity.class);
                    startActivity(intent);
                }
        );

        shortAnsBtn.setOnClickListener(
                (view3) -> {
                    Intent intent = new Intent(quizCreator.this, ShortAnswerActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(quizCreator.this);
                builder.setTitle(R.string.help_menu)
                        .setMessage(R.string.help_info)
                        .setPositiveButton("OK", (dialogInterface, i) -> Log.d("User clicked", "OK")).show();
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

}
