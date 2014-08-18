package com.sfr.engage.core;

public class FuelTimings {
    public FuelTimings() {
        super();
    }
    private String weekday;
    private String from;
    private String to;

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }
}
