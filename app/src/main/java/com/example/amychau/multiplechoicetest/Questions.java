package com.example.amychau.multiplechoicetest;

/**
 * Created by amychau on 3/13/2018.
 */

public class Questions {
    private String questionList[] = {
            "1+1 = ",
            "2+2 = "
    };

    private String multipleChoice[][] = {
            {"2","4","5","6"},
            {"1","3","4","5"}
    };

    private String correctAnswer[] = {
            "2", "4"
    };

    public String getQuestions(int index){
        String question = questionList[index];
        return question;
    }

    public String getChoice1(int index){
        String choice0 = multipleChoice[index][0];
        return choice0;
    }

    public String getChoice2(int index){
        String choice1 = multipleChoice[index][1];
        return choice1;
    }

    public String getChoice3(int index){
        String choice2 = multipleChoice[index][2];
        return choice2;
    }

    public String getChoice4(int index){
        String choice3 = multipleChoice[index][3];
        return choice3;
    }

    public String getCorrectAnswer(int index){
        String answer = correctAnswer[index];
        return answer;
    }

}

