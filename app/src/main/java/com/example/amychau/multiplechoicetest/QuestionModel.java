package com.example.amychau.multiplechoicetest;

/**
 * This class sets and gets the questions in the question table
 * Created by amychau on 4/11/2018.
 */
public class QuestionModel {
    private long questionID;
    private String question;
    private String type;

    public QuestionModel(){

    }

    long getQuestionID(){return questionID; }

    void setQuestionID(long questionID){
        this.questionID = questionID;
    }

    public String getQuestion(){
        return question;
    }

    void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }


    public void setQuestion(String question){
        this.question = question;
    }

    @Override
    public String toString(){
        return question;
    }

}
