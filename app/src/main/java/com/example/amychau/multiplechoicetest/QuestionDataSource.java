package com.example.amychau.multiplechoicetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the data source for questions table
 * Able to create a question and add it to the question table.
 * Created by amychau on 4/11/2018.
 */
public class QuestionDataSource {

    private final String[] columns = {
            DatabaseHelper.KEY_ID,
            DatabaseHelper.KEY_QUESTION,
            DatabaseHelper.KEY_TYPE
    };

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;

    QuestionDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    void open(){ db = dbHelper.getWritableDatabase();}

    public void close(){dbHelper.close();}

    public QuestionModel create(QuestionModel questions){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_QUESTION, questions.getQuestion());
        cv.put(DatabaseHelper.KEY_TYPE,questions.getType());
        long insertId = db.insert(DatabaseHelper.TABLE_NAME_ONE, null, cv);
        questions.setQuestionID(insertId);
        return questions;
    }

    List<QuestionModel> findQuestion(){
        List<QuestionModel> questionList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_ONE, columns, null, null, null, null, null);
        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){
                QuestionModel question = new QuestionModel();
                question.setQuestionID(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_QUESTION)));
                questionList.add(question);
            }
        }
        return questionList;
    }
}
