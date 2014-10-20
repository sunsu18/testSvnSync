package com.sfr.engage.core;

public class FuelTimings {
    public FuelTimings() {
        super();
    }
    private String weekday;
    private String fromHh;
    private String fromMm;
    private String toHh;
    private String toMm;

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getWeekday() {
        return weekday;
    }


    public void setFromHh(String fromHh) {
        this.fromHh = fromHh;
    }

    public String getFromHh() {
        return fromHh;
    }

    public void setFromMm(String fromMm) {
        this.fromMm = fromMm;
    }

    public String getFromMm() {
        return fromMm;
    }

    public void setToHh(String toHh) {
        this.toHh = toHh;
    }

    public String getToHh() {
        return toHh;
    }

    public void setToMm(String toMm) {
        this.toMm = toMm;
    }

    public String getToMm() {
        return toMm;
    }
}
