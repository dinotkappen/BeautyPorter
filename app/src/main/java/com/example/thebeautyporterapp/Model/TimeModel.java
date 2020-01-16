package com.example.thebeautyporterapp.Model;

public class TimeModel {
    public String getTimeID() {
        return timeID;
    }

    public void setTimeID(String timeID) {
        this.timeID = timeID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String timeID,time;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    Boolean status;



    public TimeModel() {
    }
    public TimeModel(String timeID, String time, Boolean status) {

        this.timeID = timeID;
        this.time = time;
        this.status=status;

    }
}
