package com.example.amychau.multiplechoicetest;

/**
 * This class gets and sets the fields in the Answer Table
 * Created by amychau on 4/13/2018.
 */

public class AnswerModel {
    private long answerID;
    private String answer;
    private long questionID;
    private String accuracy;

    public long getAnswerID(){return answerID; }

    void setAnswerID(long answerID){
        this.answerID = answerID;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    long getQuestionID() {
        return questionID;
    }

    void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    String getAccuracy() {
        return accuracy;
    }

    void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

}
