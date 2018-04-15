package com.example.amychau.multiplechoicetest;

/**
 * Created by amychau on 4/8/2018.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

public class QuestionDetails extends Activity {
    TextView questionHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        Bundle bundle = new Bundle();
        bundle.putString("question", getIntent().getStringExtra("question"));
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(bundle);
        transaction.replace(R.id.fragmentQuestionDetail, questionFragment);
        transaction.commit();

    }
}