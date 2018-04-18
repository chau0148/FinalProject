package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MovieActivity extends Activity {
    protected static final String ACTIVITY_NAME="Movie Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Log.i(ACTIVITY_NAME, "In onCreate");
        Button login_button = (Button) findViewById(R.id.start_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieActivity.this, MovieList.class);
                startActivity(intent);
            }
        });
    }
}
