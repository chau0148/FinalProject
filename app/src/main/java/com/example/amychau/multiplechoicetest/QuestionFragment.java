package com.example.amychau.multiplechoicetest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amychau on 4/8/2018.
 */

public class QuestionFragment extends Fragment{
    public static final String ARG_PARAM = "_id";
    private final String ACTIVITY_NAME = "QuestionFragment";
    private String item;
    Boolean checkLayout;
    TextView questionTxt, answerTxt, opt1, opt2, opt3, opt4;
    Button deleteBtn, updateBtn, ansbtn;
    View opbtn1, opbtn2, opbtn3, opbtn4;

    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    View view;
    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getActivity());
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.question_fragment, container, false);

        /**
         * Initialize the objects and find the view
         */
        questionTxt = view.findViewById(R.id.questionHere);
        answerTxt = view.findViewById(R.id.answerHere);
        opt1 = view.findViewById(R.id.option1);
        opt2 = view.findViewById(R.id.option2);
        opt3 = view.findViewById(R.id.option3);
        opt4 = view.findViewById(R.id.option4);
        opbtn1 = view.findViewById(R.id.button1);
        opbtn2 = view.findViewById(R.id.button2);
        opbtn3 = view.findViewById(R.id.button3);
        opbtn4 = view.findViewById(R.id.button4);
        View frameLayout = view.findViewById(R.id.question_land);

        /**
         * Checks if landscape mode or portrait mode
         */
        if (frameLayout != null){
            checkLayout = true;
            Log.i(ACTIVITY_NAME, "Fragment Loaded");
        } else {
            checkLayout = false;
            Log.i(ACTIVITY_NAME, "Fragment Not Loaded");
        }

        String question = getArguments().getString("question");
        questionTxt.setText(getArguments().getString("question"));
        long id = dbHelper.findQuestionId(question);

        List<OptionModel> optionList = new ArrayList<>();
        optionList = dbHelper.getOptions(id);

        /**
         * Shows the options and buttons depending on the option size
         * size 4 = MC
         * size 2 = TF
         */
        if(optionList.size() == 4){
            opt1.setText(optionList.get(0).getOptions());
            opt2.setText(optionList.get(1).getOptions());
            opt3.setText(optionList.get(2).getOptions());
            opt4.setText(optionList.get(3).getOptions());
            opbtn1.setVisibility(View.VISIBLE);
            opbtn2.setVisibility(View.VISIBLE);
            opbtn3.setVisibility(View.VISIBLE);
            opbtn4.setVisibility(View.VISIBLE);
        } else if(optionList.size() == 2) {
            opt1.setText(optionList.get(0).getOptions());
            opt2.setText(optionList.get(1).getOptions());
            opbtn1.setVisibility(View.VISIBLE);
            opbtn2.setVisibility(View.VISIBLE);
        } else {
            String option = optionList.get(0).getOptions();
            int acc = dbHelper.getAccuracy(id);
            String newOption = getAccurateAnswer(option, acc);
            opt1.setText(newOption);
            opbtn1.setVisibility(View.VISIBLE);
        }

        String answer = dbHelper.getAnswer(id);
        answerTxt.setText(answer);

        // Inflate the layout for this fragment
        if (item != null){
            ((TextView) view.findViewById(R.id.questionHere)).setText(item);
        }

        /**
         * Deletes a question using the question selected
         */
        deleteBtn = view.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(view -> {
            Log.i("Delete Button", "Pressed");
            Log.i("Question is: ", question+"");
            Log.i("Question ID is: ", id+"");
            dbHelper.deleteQuestion(id);
            Toast.makeText(getActivity(), "Question Deleted", Toast.LENGTH_LONG).show();
        });

        /**
         * Update Question in the database
         * User will type the new question into the box to update the database
         */
        updateBtn = view.findViewById(R.id.updateButton);
        updateBtn.setOnClickListener(view -> {
            Log.i("Update Button", "Pressed");
            AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
            LayoutInflater linflate = getActivity().getLayoutInflater();
            View d = linflate.inflate(R.layout.update_question, null);
            EditText newQuestion =  d.findViewById(R.id.updateQuestion) ;
            AlertDialog.Builder builder = build.setView(d)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences sharedPref = getActivity().getSharedPreferences("newQuestion", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPref.edit();
                            edit.putString("newQuestion", newQuestion.getText().toString());
                            edit.apply();

                            String newQues = sharedPref.getString("newQuestion", "");
                            dbHelper.updateQuestion(newQues, id);
                            questionTxt.setText(newQues);
                        }
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    });
            build.show();
        });

        /**
         * Custom Dialog for updating the Answer.
         * User will type the answer into the box and it will update in database.
         */
       ansbtn = view.findViewById(R.id.ansButton);
       ansbtn.setOnClickListener(view -> {
           Log.i("Change", "Answer");
           AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
           LayoutInflater linflate = getActivity().getLayoutInflater();
           View d = linflate.inflate(R.layout.update_question, null);
           EditText newAnswer =  d.findViewById(R.id.updateQuestion) ;
           AlertDialog.Builder builder = build.setView(d)
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           SharedPreferences sharedPref = getActivity().getSharedPreferences("newAnswer", Context.MODE_PRIVATE);
                           SharedPreferences.Editor edit = sharedPref.edit();
                           edit.putString("newAnswer", newAnswer.getText().toString());
                           edit.apply();

                           String newAns = sharedPref.getString("newAnswer", "");
                           dbHelper.updateAnswer(newAns, id);
                           answerTxt.setText(newAns);
                       }
                   })
                   .setNegativeButton("Cancel", (dialogInterface, i) -> {
                   });
           build.show();
       });

        /**
         * Update Option 1
         * User will type the new option in the box and it will update in the database
         */
        opbtn1.setOnClickListener(view -> {
            Log.i("Change", "Option 1");
            AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
            LayoutInflater linflate = getActivity().getLayoutInflater();
            View d = linflate.inflate(R.layout.update_question, null);
            EditText newOp1 =  d.findViewById(R.id.updateQuestion) ;
            build.setView(d)
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("op1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPref.edit();
                        edit.putString("op1", newOp1.getText().toString());
                        edit.apply();

                        String newOpt1 = sharedPref.getString("op1", "");
                        dbHelper.updateOptions(newOpt1, id);
                        opt1.setText(newOpt1);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    });
            build.show();
        });

        /**
         * Update Option 2
         * User will type the new option in the box and it will update in the database
         */
        opbtn2.setOnClickListener(view -> {
            Log.i("Change", "Option 2");
            AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
            LayoutInflater linflate = getActivity().getLayoutInflater();
            View d = linflate.inflate(R.layout.update_question, null);
            EditText newOp2 =  d.findViewById(R.id.updateQuestion) ;
            build.setView(d)
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("op2", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPref.edit();
                        edit.putString("op2", newOp2.getText().toString());
                        edit.apply();

                        String newOpt2 = sharedPref.getString("op2", "");
                        dbHelper.updateOptions(newOpt2, id);
                        opt2.setText(newOpt2);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    });
            build.show();
        });

        /**
         * Update Option 3
         * User will type the new option in the box and it will update in the database
         */
        opbtn3.setOnClickListener(view -> {
            Log.i("Change", "Option 3");
            AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
            LayoutInflater linflate = getActivity().getLayoutInflater();
            View d = linflate.inflate(R.layout.update_question, null);
            EditText newOp3 =  d.findViewById(R.id.updateQuestion) ;
            build.setView(d)
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("op3", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPref.edit();
                        edit.putString("op3", newOp3.getText().toString());
                        edit.apply();

                        String newOpt3 = sharedPref.getString("op3", "");
                        dbHelper.updateOptions(newOpt3, id);
                        opt3.setText(newOpt3);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    });
            build.show();
        });

        /**
         * Update Option 4
         * User will type the new option in the box and it will update in the database
         */
        opbtn4.setOnClickListener(view -> {
            Log.i("Change", "Option 4");
            AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
            LayoutInflater linflate = getActivity().getLayoutInflater();
            View d = linflate.inflate(R.layout.update_question, null);
            EditText newOp4 =  d.findViewById(R.id.updateQuestion) ;
            build.setView(d)
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        SharedPreferences sharedPref = getActivity().getSharedPreferences("op4", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPref.edit();
                        edit.putString("op4", newOp4.getText().toString());
                        edit.apply();

                        String newOpt4 = sharedPref.getString("op4", "");
                        dbHelper.updateOptions(newOpt4, id);
                        opt4.setText(newOpt4);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    });
            build.show();
        });


        return view;
    }

    public String getAccurateAnswer(String answer, int acc){
        String afterDot = StringUtils.substringAfter(answer, ".");
        afterDot = StringUtils.substring(afterDot, 0, acc);
        String beforeDot = StringUtils.substringBefore(answer, ".");
        String newOption = beforeDot+ "." +afterDot;

        return newOption;
    }

}