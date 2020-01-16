package com.example.thebeautyporterapp.Model;

public class StaffModel {
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getBussinessID() {
        return bussinessID;
    }

    public void setBussinessID(String bussinessID) {
        this.bussinessID = bussinessID;
    }

    String staffID;
    String staffname;
    String bussinessID;


    public StaffModel() {
    }
    public StaffModel(String staffID, String staffname, String bussinessID) {

        this.staffID = staffID;
        this.staffname = staffname;
        this.bussinessID = bussinessID;

    }
}
