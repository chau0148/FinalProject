package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class importQuestion extends Activity {

    protected static final String ACTIVITY_NAME="ImportQuestion";
    protected static final String URL_STRING = "http://torunski.ca/CST2335/QuizInstance.xml";
    private ProgressBar progress;
    private TextView questionTxt, questionTxt2, ansTxt, ansTxt2, ch1, ch2, ch3, ch4, ch12, ch22, ch32, ch42;
    private Button questionBtn;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    QuestionDataSource dataSource;
    OptionDataSource oDataSource;
    AnswerDataSource aDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_question);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        dataSource = new QuestionDataSource(this);
        oDataSource = new OptionDataSource(this);
        aDataSource = new AnswerDataSource(this);
        dataSource.open();
        oDataSource.open();
        aDataSource.open();

        questionTxt = findViewById(R.id.questionTxt);
        questionTxt2 = findViewById(R.id.questionTxt2);
        ch1 = findViewById(R.id.choicesTxt1);
        ch2 = findViewById(R.id.choicesTxt2);
        ch3 = findViewById(R.id.choicesTxt3);
        ch4 = findViewById(R.id.choicesTxt4);
        ansTxt = findViewById(R.id.answerTxt);
        ansTxt2 = findViewById(R.id.answerTxt2);
        ch12 = findViewById(R.id.choicesTxt12);
        ch22 = findViewById(R.id.choicesTxt22);
        ch32 = findViewById(R.id.choicesTxt32);
        ch42 = findViewById(R.id.choicesTxt42);

        questionBtn = findViewById(R.id.questionList);
        questionBtn.setOnClickListener(
                (view) -> {
                    Intent intent = new Intent(importQuestion.this, Questions.class);
                    startActivity(intent);
                });

        new quizQuery().execute(null, null, null);
    }

    //innerclass that extends AsyncTask
    class quizQuery extends AsyncTask<String, Integer, String> {
        private String correct;
        private ArrayList<String> question = new ArrayList<>();
        private ArrayList<String> answers = new ArrayList<>();
        private long qid;

        private String accuracy;

        @Override
        protected String doInBackground(String... args){
            InputStream stream;

            // connecting to url and reading data input stream
            try {
                URL url = new URL(URL_STRING);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000); //in milliseconds
                conn.setConnectTimeout(15000); //in millisenconds
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                parser.getEventType();

                while(parser.getEventType() != XmlPullParser.END_DOCUMENT){
                    if(parser.getEventType() == XmlPullParser.START_TAG){
                        if(parser.getName().equalsIgnoreCase("MultipleChoiceQuestion")){
                            correct = parser.getAttributeValue(null, "correct");
                            question.add(parser.getAttributeValue(null, "question"));
                            insertQuestion("m");
                            qid = dbHelper.findQuestionId(parser.getAttributeValue(null, "question"));
                            insertAnswer(correct, qid);
                        }
                        else if(parser.getName().equalsIgnoreCase("answer")) {
                            parser.next();
                            answers.add(parser.getText());
                            insertOptions(parser.getText(), qid);
                        }
                        else if(parser.getName().equalsIgnoreCase("NumericQuestion")){
                            accuracy = parser.getAttributeValue(null, "accuracy");
                            question.add(parser.getAttributeValue(null, "question"));
                            answers.add(parser.getAttributeValue(null, "answer"));
                            insertQuestion("num");
                            qid = dbHelper.findQuestionId(parser.getAttributeValue(null, "question"));
                            insertOptions(parser.getAttributeValue(null, "answer"), qid);
                            insertShortAnswer(parser.getAttributeValue(null, "answer"), qid, accuracy);
                        }
                        else if(parser.getName().equalsIgnoreCase("TrueFalseQuestion")) {
                            question.add(parser.getAttributeValue(null, "question"));
                            answers.add(parser.getAttributeValue(null, "answer"));
                            insertQuestion("tf");
                            qid = dbHelper.findQuestionId(parser.getAttributeValue(null, "question"));
                            insertOptions("true", qid);
                            insertOptions("false", qid);
                            insertAnswer(parser.getAttributeValue(null, "answer"), qid);
                        }
                        parser.next();
                    }
                    else parser.next();
                }conn.disconnect();
                publishProgress(100);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        void insertQuestion(String type){
            for(int i=0; i<question.size();i++) {
                QuestionModel questionModel = new QuestionModel();
                questionModel.setQuestion(question.get(i));
                questionModel.setType(type);
                dataSource.create(questionModel);
            }
        }

        void insertOptions(String option, long quesId){
            OptionModel optionModel = new OptionModel();
            optionModel.setOptions(option);
            optionModel.setQuestionID(quesId);
            oDataSource.create(optionModel);
        }

        void insertAnswer(String answer, long quesId){
            AnswerModel answerModel = new AnswerModel();
            answerModel.setAnswer(answer);
            answerModel.setQuestionID(quesId);
            aDataSource.create(answerModel);
        }

        void insertShortAnswer(String answer, long quesId, String acc){
            AnswerModel answerModel = new AnswerModel();
            answerModel.setAnswer(answer);
            answerModel.setQuestionID(quesId);
            answerModel.setAccuracy(acc);
            aDataSource.create(answerModel);
        }

        @Override
        protected void onProgressUpdate(Integer ... value){
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String args){
            progress.setVisibility(View.INVISIBLE);
            questionTxt.setText("Question: " + question.get(0));
            ch1.setText("Option 1: " + answers.get(0));
            ch2.setText("Option 2: " + answers.get(1));
            ch3.setText("Option 3: " + answers.get(2));
            ch4.setText("Option 3: " + answers.get(3));
            ansTxt.setText("Answer: " + correct);

            questionTxt2.setText("Question: " + question.get(1));
            ch12.setText("Answer: " + answers.get(4));

            ch42.setText("Question: " + question.get(2));
            ansTxt2.setText("Answer: " + answers.get(5));
        }

        public boolean fileExist(String name){
            File file = getBaseContext().getFileStreamPath(name);
            return file.exists();
        }
    }
}
