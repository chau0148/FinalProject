package com.example.amychau.multiplechoicetest;

/**
 * This class gets and sets the Option field in the database
 * Created by amychau on 4/11/2018.
 */
public class OptionModel {

    long id;
    private String options;
    private long questionID;

    public OptionModel(){

    }

    public OptionModel(String options){
        this.options = options;
    }

    public OptionModel(long id, String options){
        this.id = id;
        this.options = options;
    }

    //Setters and Getters for ID and Options
    public void setId(long id){
        this.id = id;
    }

    void setOptions(String options){
        this.options = options;
    }

    public long getId(){
        return this.id;
    }

    String getOptions(){
        return this.options;
    }

    long getQuestionID() {
        return questionID;
    }

    void setQuestionID(long questionID) {
        this.questionID = questionID;
    }
}
