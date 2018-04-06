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
import android.widget.Button;

public class QuizCreaterMain extends Activity implements AppCompatCallback {

    Button mcBtn;
    Button tfBtn;
    Button shortAnsBtn;
    Button scoreBtn;
    String ACTIVITY_NAME = "Table of Contents";
    private AppCompatDelegate delagate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        delagate = AppCompatDelegate.create(this, this);
        delagate.installViewFactory();

        super.onCreate(savedInstanceState);

        delagate.onCreate(savedInstanceState);
        delagate.setContentView(R.layout.activity_quiz_creater_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        delagate.setSupportActionBar(toolbar);

        Log.i(ACTIVITY_NAME, "In OnCreate()");

        mcBtn = findViewById(R.id.mc);
        tfBtn = findViewById(R.id.tf);
        shortAnsBtn = findViewById(R.id.shortAns);
        scoreBtn = findViewById(R.id.highScore);

        mcBtn.setOnClickListener(
                (view1) -> {
                    Intent intent = new Intent(QuizCreaterMain.this, MultipleChoice.class);
                    startActivity(intent);
                }
        );

        tfBtn.setOnClickListener(
                (view2) -> {
                    Intent intent = new Intent(QuizCreaterMain.this, TrueFalseActivity.class);
                    startActivity(intent);
                }
        );

        shortAnsBtn.setOnClickListener(
                (view3) -> {
                    Intent intent = new Intent(QuizCreaterMain.this, ShortAnswerActivity.class);
                    startActivity(intent);
                }
        );

        scoreBtn.setOnClickListener(
                (view4) -> {
                    Intent intent = new Intent(QuizCreaterMain.this, HighScore.class);
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
