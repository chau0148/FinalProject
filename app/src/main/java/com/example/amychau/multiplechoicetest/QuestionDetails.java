package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * This class holds the question that was selected in a Bundle
 * It gets put into the fragment class
 * Created by amychau on 4/8/2018.
 */
public class QuestionDetails extends Activity {

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