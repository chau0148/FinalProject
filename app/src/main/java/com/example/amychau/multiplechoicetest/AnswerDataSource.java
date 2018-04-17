package com.example.amychau.multiplechoicetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the Answer Data Source for the Answer Table.
 * It creates and finds the Answer data in table.
 * Created by amychau on 4/13/2018.
 */

public class AnswerDataSource {
    //The columns in the Answer Table
    public final String[] columns = {
            DatabaseHelper.KEY_ID,
            DatabaseHelper.KEY_ANSWER,
            DatabaseHelper.FK_QUESTION,
            DatabaseHelper.KEY_ACCURACY
    };

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;

    AnswerDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    void open(){ db = dbHelper.getWritableDatabase();}

    //public void close(){dbHelper.close();}

    public AnswerModel create(AnswerModel answers){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_ANSWER, answers.getAnswer());
        cv.put(DatabaseHelper.FK_QUESTION,answers.getQuestionID());
        cv.put(DatabaseHelper.KEY_ACCURACY,answers.getAccuracy());
        long insertId = db.insert(DatabaseHelper.TABLE_NAME_THREE, null, cv);
        answers.setAnswerID(insertId);
        return answers;
    }

    public List<AnswerModel> findAnswer(){
        List<AnswerModel> answerList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_THREE, columns, null, null, null, null, null);
        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){
                AnswerModel answer = new AnswerModel();
                answer.setAnswerID(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                answer.setAnswer(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ANSWER)));
                answerList.add(answer);
            }
        }
        return answerList;
    }

}
