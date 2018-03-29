package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainPage extends Activity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    String ACTIVITY_NAME = "Test Creator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Log.i(ACTIVITY_NAME, "In OnCreate()");

        btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(MainPage.this, QuizCreaterMain.class);
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
