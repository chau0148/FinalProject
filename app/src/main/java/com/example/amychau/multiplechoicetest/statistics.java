package com.example.amychau.multiplechoicetest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amychau on 4/14/2018.
 */

public class statistics extends AppCompatActivity{

    private TextView num, longQ, shortQ, avgQ;
    ArrayList<Integer> counter = new ArrayList<>();
    ArrayList<String> ar = new ArrayList<>();
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    String ACTIVITY_NAME = "Statistics";
    private AppCompatDelegate delagate;

    /**
     * While creating the activity, it will load a toolbar
     * where users can switch between the different activities
     * and four buttons that users can choose to add different types of questions
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        //Add toolbar to activity
//        delagate = AppCompatDelegate.create(this, this);
//        delagate.installViewFactory();
//        super.onCreate(savedInstanceState);
//        delagate.onCreate(savedInstanceState);
//        delagate.setContentView(R.layout.statistics_activity);
//        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.my_toolbar);
//        delagate.setSupportActionBar(toolbar);


        Log.i(ACTIVITY_NAME, "In OnCreate()");

        num = findViewById(R.id.numberResult);
        longQ = findViewById(R.id.longResult);
        shortQ = findViewById(R.id.shortResult);
        avgQ = findViewById(R.id.avgResult);

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        QuestionDataSource dataSource = new QuestionDataSource(this);
        OptionDataSource oDataSource = new OptionDataSource(this);
        AnswerDataSource aDataSource = new AnswerDataSource(this);
        dataSource.open();
        oDataSource.open();
        aDataSource.open();

        List<QuestionModel> list = dataSource.findQuestion();
        for (int i = 0; i < list.size(); i++) {
            ar.add(list.get(i).getQuestion());
        }
        for (String x : ar) {
            count(x);
        }

        int totalQ = list.size();
        num.setText(Integer.toString(totalQ));

        int max = Collections.max(counter);
        longQ.setText(Integer.toString(max));

        int min = Collections.min(counter);
        shortQ.setText(Integer.toString(min));

        int total = 0;
        for(Integer i: counter) {
            total += i;
        }

        int average = 0;
        if (!(counter.size() == 0)) {
            average = total / counter.size();
        } else
            average = 0;

        avgQ.setText(Integer.toString(average));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(statistics.this);
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

    public List count(String x){
        char[] ch = x.toCharArray();
        int count = 0;
        int other = 0;

        for(int i=0; i<x.length(); i++){
            if(Character.isLetter(ch[i]) || Character.isDigit(ch[i]) || Character.isSpaceChar(ch[i])){
                count++;
            } else
                count++;
        }
        counter.add(count);
        return counter;
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
        super.onSupportActionModeStarted(mode);
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        super.onSupportActionModeFinished(mode);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
