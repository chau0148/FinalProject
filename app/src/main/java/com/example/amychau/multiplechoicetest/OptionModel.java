package com.example.amychau.multiplechoicetest;

/**
 * Created by amychau on 4/11/2018.
 */

public class OptionModel {

    long id;
    String options;
    long questionID;

    //constructors
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

    public void setOptions(String options){
        this.options = options;
    }

    public long getId(){
        return this.id;
    }

    public String getOptions(){
        return this.options;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }
}
