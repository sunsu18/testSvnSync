package com.sfr.engage.core;

/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 *  3. Should this method implement Serializable?
 */
public class Messages {
    private String message;
    private String title;

    /**
     */
    public Messages() {
        super();
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return
     */
    public String getMessage() {
        return message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
