package com.example.amychau.multiplechoicetest; /**
 * Created by amychau on 4/8/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    //Database Version and Name
    public static String DATABASE_NAME = "questions.db";
    private static int VERSION_NUM = 3;

    //Table Names
    public static String TABLE_NAME_ONE = "questionTable";
    public static String TABLE_NAME_TWO = "OptionTable";
    public static String TABLE_NAME_THREE = "answerTable";

    //Common column name
    public static final String KEY_ID = "_id";

    //Question Table Column Names
    public static final String KEY_QUESTION = "Questions";
    public static final String KEY_TYPE = "Type";

    //Option Table Column Names
    public static final String KEY_OPTION = "Option";

    //Answer Table Column Names
    public static int checkCorrect = 0;
    public static final String KEY_ACCURACY = "Accuracy";
    public static final String FK_QUESTION = "QuestionID";

    public static final String KEY_ANSWER = "Answer";


    private static final String TABLE_ONE = "CREATE TABLE " + TABLE_NAME_ONE + " ("
                                                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + KEY_QUESTION + " TEXT, "
                                                + KEY_TYPE + " TEXT)";

    private static final String TABLE_TWO = "CREATE TABLE " + TABLE_NAME_TWO + " ("
                                                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + KEY_OPTION + " TEXT, "
                                                + FK_QUESTION + " INTEGER)";

    private static final String TABLE_THREE = "CREATE TABLE " + TABLE_NAME_THREE + " ("
                                                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + KEY_ANSWER + " TEXT, "
                                                + FK_QUESTION + " INTEGER, "
                                                + KEY_ACCURACY + " INTEGER)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Question Database ", "in onCreate");
        db.execSQL(TABLE_ONE);
        db.execSQL(TABLE_TWO);
        db.execSQL(TABLE_THREE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TWO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_THREE);

        onCreate(db);
        Log.i("Question Database", "oldVersion: " +i+ " new Version: " +i1);
    }

    public long addQuestion(QuestionModel questionModel, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_QUESTION, questionModel.getQuestion());
        cv.put(KEY_TYPE, questionModel.getType());

        //Insert Row
        long questionID = db.insert(TABLE_NAME_ONE, null, cv);
        return questionID;
    }

    /**
     * Get a single question
     */
    public QuestionModel getQuestion(long questionID){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_ONE + " WHERE " +KEY_ID+ " = " +questionID;

        Log.e("Database", "In SELECT QUERY");
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestionID(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        questionModel.setQuestion(cursor.getString(cursor.getColumnIndex(KEY_QUESTION)));
        questionModel.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));

        return questionModel;
    }

    /**
     * Get All Questions
     */
    public List<QuestionModel> getAllQuestions(){
        List<QuestionModel> questionList = new ArrayList<QuestionModel>();
        String query = "SELECT * FROM " + TABLE_NAME_ONE;

        Log.e("Database ", "In SELECT ALL QUERY");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //Loop through rows and adding it to list
        if(cursor.moveToFirst()){
            do{
                QuestionModel questionModel = new QuestionModel();
                questionModel.setQuestionID(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                questionModel.setQuestion(cursor.getString(cursor.getColumnIndex(KEY_QUESTION)));
                questionModel.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
                questionList.add(questionModel);
            } while (cursor.moveToNext());
        }
        return questionList;
    }

    /**
     * Find a question ID by question
     */
    public long findQuestionId(String question){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_ONE + " WHERE " +KEY_QUESTION+ " LIKE '" +question+ "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        QuestionModel questionModel = new QuestionModel();
        questionModel.setQuestionID(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
        questionModel.setQuestion(cursor.getString(cursor.getColumnIndex(KEY_QUESTION)));
        questionModel.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
        long quesId = questionModel.getQuestionID();
        return quesId;
    }

    /**
     * Updating a Question
     */
    public int updateQuestion(String newQues, long qid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_QUESTION, newQues);
        return db.update(TABLE_NAME_ONE, cv, KEY_ID + " = " +qid, null);
    }

    /**
     * Deleting a Question
     */
    public void deleteQuestion(long questionID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_ONE, KEY_ID + " = ?", new String[] { String.valueOf(questionID)});
    }


    /**
     * Getting all Options
     */
    public List<OptionModel> getAllOptions(){
        List<OptionModel> optionList = new ArrayList<OptionModel>();
        String query = "SELECT * FROM " + TABLE_NAME_TWO;

        Log.e("Options", "In SELECT ALL OPTIONS");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //Loop through rows and adding it to list
        if (cursor.moveToFirst()){
            do {
                OptionModel optionModel = new OptionModel();
                optionModel.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                optionModel.setOptions(cursor.getString(cursor.getColumnIndex(KEY_OPTION)));

                optionList.add(optionModel);
            } while (cursor.moveToNext());
        }
        return optionList;
    }

    /**
     * Get all the options with a specif ID
     */
    public List<OptionModel> getOptions(long qid){
        List<OptionModel> optionList = new ArrayList<>();
        Log.i("Getting specific", "Options with QID");

        String query = "SELECT * FROM " + TABLE_NAME_TWO + " WHERE " + FK_QUESTION + " = " +qid;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                OptionModel optionModel = new OptionModel();
                optionModel.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                optionModel.setOptions(cursor.getString(cursor.getColumnIndex(KEY_OPTION)));

                optionList.add(optionModel);
            } while (cursor.moveToNext());
        }
        return optionList;
    }

    /**
     * Update Options
     */
    public int updateOptions(String newOption, long qid){
        Log.i("Updating", "Option");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_OPTION, newOption);

        return db.update(TABLE_NAME_TWO, cv, KEY_ID + " = " +qid, null);
    }


    /**
     * Getting Question Total
     */
    public int questionTotal(){
        String count = "SELECT * FROM " +TABLE_NAME_ONE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(count, null);
        int total = cursor.getCount();
        cursor.close();
        return total;
    }

    /**
     * Get Answer By QID
     */
    public String getAnswer(long qid) {
        Log.i("Getting Answer", "With QID");

        String query = "SELECT * FROM " + TABLE_NAME_THREE + " WHERE " + FK_QUESTION + " = " + qid;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String answer = null;
        if (cursor.moveToFirst()) {
            AnswerModel answerModel = new AnswerModel();
            answerModel.setAnswerID(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
            answerModel.setAnswer(cursor.getString(cursor.getColumnIndex(KEY_ANSWER)));
            answer = answerModel.getAnswer();
        }
        return answer;
    }

    /**
     * Update Answer
     */
    public int updateAnswer(String newAnswer, long qid){
        Log.i("Updating", "Answer");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ANSWER, newAnswer);

        return db.update(TABLE_NAME_THREE, cv, KEY_ID + " = " +qid, null);
    }

    public int getAccuracy(long qid){
        Log.i("Getting", "Accuracy");
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_THREE + " WHERE " + FK_QUESTION + " = " + qid;
        Cursor cursor = db.rawQuery(query, null);

        int accuracy = 0;
        if(cursor.moveToFirst()){
            AnswerModel answerModel = new AnswerModel();
            answerModel.setAccuracy(cursor.getString(cursor.getColumnIndex(KEY_ACCURACY)));
            accuracy = Integer.parseInt(answerModel.getAccuracy());
        }
        return accuracy;
    }

    /**
     * Close Database Connection
     */
    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()){
            db.close();
        }
    }
}