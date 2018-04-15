package com.example.amychau.multiplechoicetest;

/**
 * Created by amychau on 4/13/2018.
 */

public class AnswerModel {
    private long answerID;
    private String answer;
    private long questionID;
    private String accuracy;

    public long getAnswerID(){return answerID; }

    public void setAnswerID(long answerID){
        this.answerID = answerID;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

}
