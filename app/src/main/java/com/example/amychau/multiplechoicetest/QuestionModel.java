package com.example.amychau.multiplechoicetest;

/**
 * Created by amychau on 4/11/2018.
 */

public class QuestionModel {
    private long questionID;
    private String question;
    private String type;

    public QuestionModel(){

    }

    public long getQuestionID(){return questionID; }

    public void setQuestionID(long questionID){
        this.questionID = questionID;
    }

    public String getQuestion(){
        return question;
    }

    public void setType(String type){
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

    public QuestionModel(String question, String opt1, String opt2, String opt3, String opt4, String answer){
        OptionModel optionModel = new OptionModel();
        AnswerModel answerModel = new AnswerModel();
        this.question = question;
        opt1 = optionModel.getOptions();

    }

}
