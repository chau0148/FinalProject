package com.example.amychau.multiplechoicetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amychau on 4/12/2018.
 */

public class OptionDataSource {

    public final String[] columns = {
            DatabaseHelper.KEY_ID,
            DatabaseHelper.KEY_OPTION,
            DatabaseHelper.FK_QUESTION
    };


    SQLiteOpenHelper dbHelper;
    SQLiteDatabase db;

    public OptionDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open(){ db = dbHelper.getWritableDatabase();}

    public void close(){dbHelper.close();}

    public OptionModel create(OptionModel options){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_OPTION, options.getOptions());
        cv.put(DatabaseHelper.FK_QUESTION, options.getQuestionID());
        long insertId = db.insert(DatabaseHelper.TABLE_NAME_TWO, null, cv);
        options.setId(insertId);
        return options;

    }

    public List<OptionModel> findOptions(){
        List<OptionModel> optionList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME_TWO, columns, null, null, null, null, null);
        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){
                OptionModel option = new OptionModel();
                option.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                option.setOptions(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_OPTION)));
                optionList.add(option);
            }
        }
        return optionList;
    }
}
