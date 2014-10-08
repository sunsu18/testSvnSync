package com.sfr.engage.core;



public class AlertsSubscribeFrequencyType {
    public AlertsSubscribeFrequencyType() {
        super();
    }
    
    private String scheduleFrequency;

    private String scheduleDay;

    private String scheduleDate;
 
    private String scheduleMonth;

    public void setScheduleFrequency(String scheduleFrequency) {
        this.scheduleFrequency = scheduleFrequency;
    }

    public String getScheduleFrequency() {
        return scheduleFrequency;
    }

    public void setScheduleDay(String scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public String getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleMonth(String scheduleMonth) {
        this.scheduleMonth = scheduleMonth;
    }

    public String getScheduleMonth() {
        return scheduleMonth;
    }
}
