package com.example.amychau.multiplechoicetest;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


/**
 * This class displays the questions in a listView
 * When user clicks the question it will bring it to a new activity to edit the question
 * If in landscape mode, the fragment should be on the right hand side
 */
public class Questions extends AppCompatActivity {

    ListView listView;
    ArrayList<String> ar = new ArrayList<>();
    ArrayAdapter adapter;
    Boolean checkLayout;
    protected static final String ACTIVITY_NAME = "questionList";

    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_activity);

        //Add a toolbar to the activity
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        QuestionDataSource dataSource = new QuestionDataSource(this);
        OptionDataSource oDataSource = new OptionDataSource(this);
        AnswerDataSource aDataSource = new AnswerDataSource(this);
        dataSource.open();
        oDataSource.open();
        aDataSource.open();

        View frameLayout = findViewById(R.id.question_land);
        if (frameLayout != null){
            checkLayout = true;
            Log.i(ACTIVITY_NAME, "Fragment Loaded");
        } else {
            checkLayout = false;
            Log.i(ACTIVITY_NAME, "Fragment Not Loaded");
        }

      // Questions in the chat stored in an Arraylist
        adapter = new Adapter(this, ar);
        listView = findViewById(R.id.questionList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.e("pos",l+"");
            String chosenQuestion = String.valueOf(adapterView.getItemAtPosition(i));
            Log.e("clicked on ", chosenQuestion);

            if(checkLayout){
                final FragmentManager manager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("question", chosenQuestion);
                bundle.putLong("id", l);
                QuestionFragment qFragment = new QuestionFragment();
                qFragment.setArguments(bundle);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.question_land, qFragment);
                transaction.commit();
            } else {
                Intent intent = new Intent(Questions.this, QuestionDetails.class);
                intent.putExtra("question", chosenQuestion);
                intent.putExtra("id", l);
                startActivity(intent);
            }});
    }

        private class Adapter extends ArrayAdapter<String> {

          Adapter(Context context, List<String> questions){
              super(context, R.layout.list_of_question, questions);
          }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View result = inflater.inflate(R.layout.list_of_question, parent, false);

            String question = getItem(position);
            TextView questionTxt = (TextView) result.findViewById(R.id.list_of_questions);

            questionTxt.setText(question);

         return result;
        }

        @Override
        public long getItemId(int position){
            return position;}
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbHelper.closeDB();
        Log.i(ACTIVITY_NAME,  "In onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ar.clear();
       List<QuestionModel> list = dbHelper.getAllQuestions();
        for (int i = 0; i < list.size(); i++) {
            ar.add(list.get(i).getQuestion());
            adapter.notifyDataSetChanged();
        }
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Questions.this);
                builder.setTitle(R.string.help_menu)
                        .setMessage(R.string.help_info)
                        .setPositiveButton("OK", (dialogInterface, i) -> Log.d("User clicked", "OK")).show();
                break;
        }
        return true;
    }
}