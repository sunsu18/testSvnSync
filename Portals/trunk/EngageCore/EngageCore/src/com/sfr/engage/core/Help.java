package com.sfr.engage.core;


/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 *  3. Should this method implement Serializable?
 */
public class Help {

    private String question;
    private String answer;


    /**
     */
    public Help() {
        super();
    }

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
