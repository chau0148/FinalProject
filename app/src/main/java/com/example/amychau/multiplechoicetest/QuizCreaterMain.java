package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class QuizCreaterMain extends Activity {

    Button mcBtn;
    Button tfBtn;
    Button shortAnsBtn;
    Button scoreBtn;
    String ACTIVITY_NAME = "Table of Contents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creater_main);

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
