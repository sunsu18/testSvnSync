package com.sfr.core.bean;

public class QuestionAnswer extends BaseBean implements Comparable<QuestionAnswer> {

    private static final long serialVersionUID = 1L;
    private String question;
    private String answer;
    private Integer index;

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public int compareTo(QuestionAnswer o) {
        return this.index - o.index;
    }
}
