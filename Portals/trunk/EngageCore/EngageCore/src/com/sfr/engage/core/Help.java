package com.sfr.engage.core;


public class Help {
    
    public Help() {
        super();
    }
     private String  question;
     private String answer;


    /**
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return
     */
    public String getAnswer() {
        return answer;
    }
}
