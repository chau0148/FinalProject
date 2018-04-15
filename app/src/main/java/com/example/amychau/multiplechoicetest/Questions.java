package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.amychau.multiplechoicetest.DatabaseHelper;

import org.w3c.dom.Text;

/**
 * This class displays the questions in a listView
 * When user clicks the question it will bring it to a new activity to edit the question
 */
public class Questions extends Activity {

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

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        QuestionDataSource dataSource = new QuestionDataSource(this);
        OptionDataSource oDataSource = new OptionDataSource(this);
        AnswerDataSource aDataSource = new AnswerDataSource(this);
        dataSource.open();
        oDataSource.open();
        aDataSource.open();

        List<QuestionModel> list =  dataSource.findQuestion();
        for(int i=0;i<list.size();i++){
            ar.add(list.get(i).getQuestion());
            Log.i("QuestionList ",ar.get(i));
        }

        View frameLayout = findViewById(R.id.question_land);
        if (frameLayout != null){
            checkLayout = true;
            Log.i(ACTIVITY_NAME, "Fragment Loaded");
        } else {
            checkLayout = false;
            Log.i(ACTIVITY_NAME, "Fragment Not Loaded");
        }

//        List<OptionModel> optionList = oDataSource.findOptions();
//        for(int i=0;i<optionList.size();i++){
//            Log.i("optionList: ",optionList.get(i).getOptions());
//        }
//
//        List<AnswerModel> answerList = aDataSource.findAnswer();
//        for(int i=0;i<answerList.size();i++){
//            Log.i("answer is: ", answerList.get(i).getAnswer());
//        }

//        // Questions in the chat stored in an Arraylist
        adapter = new Adapter(this, ar);
        listView = findViewById(R.id.questionList);
        listView.setAdapter(adapter);

        /**
         * The position is one less than what is selected.
         */
        final FragmentManager manager = getFragmentManager();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("pos",l+"");
                String chosenQuestion = String.valueOf(adapterView.getItemAtPosition(i));
                long rowId = l;
                Log.e("clicked on ", chosenQuestion);

                if(checkLayout){
                    Bundle bundle = new Bundle();
                    bundle.putString("question", chosenQuestion);
                    bundle.putLong("id", l);
                    QuestionFragment qFragment = new QuestionFragment();
                    qFragment.setArguments(bundle);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.question_land, qFragment);
                    transaction.commit();
                } else {
                    Intent intent = new Intent(Questions.this, QuestionDetails.class);
                    intent.putExtra("question", chosenQuestion);
                    intent.putExtra("id", l);
                    startActivity(intent);
                }}
            });
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
        dbHelper.close();
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
}