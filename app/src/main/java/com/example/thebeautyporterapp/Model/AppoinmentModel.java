package com.example.thebeautyporterapp.Model;

public class AppoinmentModel {
    public String getLattittude() {
        return lattittude;
    }

    public void setLattittude(String lattittude) {
        this.lattittude = lattittude;
    }

    public String getLongittude() {
        return longittude;
    }

    public void setLongittude(String longittude) {
        this.longittude = longittude;
    }

    String lattittude,longittude;
    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getBtn_Status() {
        return btn_Status;
    }

    public void setBtn_Status(String btn_Status) {
        this.btn_Status = btn_Status;
    }

    String booking_status, btn_Status;

    public String getAppoinmentID() {
        return appoinmentID;
    }

    public void setAppoinmentID(String appoinmentID) {
        this.appoinmentID = appoinmentID;
    }

    public String getAppoinmentImg() {
        return appoinmentImg;
    }

    public void setAppoinmentImg(String appoinmentImg) {
        this.appoinmentImg = appoinmentImg;
    }

    public String getAppoinmentTitle() {
        return appoinmentTitle;
    }

    public void setAppoinmentTitle(String appoinmentTitle) {
        this.appoinmentTitle = appoinmentTitle;
    }

    public String getAppoinmentDate() {
        return appoinmentDate;
    }

    public void setAppoinmentDate(String appoinmentDate) {
        this.appoinmentDate = appoinmentDate;
    }

    public String getAppoinmentTime() {
        return appoinmentTime;
    }

    public void setAppoinmentTime(String appoinmentTime) {
        this.appoinmentTime = appoinmentTime;
    }
    String appoinmentID;
    String appoinmentImg;
    String appoinmentTitle;
    String appoinmentDate;
    String appoinmentTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;
    public AppoinmentModel(String appoinmentID,String appoinmentTitle,String appoinmentDate,String appoinmentTime, String appoinmentImg, String booking_status,String btn_Status,String lattittude,String longittude,String phone ) {

        this.appoinmentID=appoinmentID;
        this.appoinmentImg = appoinmentImg;
        this.appoinmentTitle = appoinmentTitle;
        this.appoinmentDate = appoinmentDate;
        this.appoinmentTime=appoinmentTime;
        this.booking_status=booking_status;
        this.btn_Status=btn_Status;
        this.lattittude=lattittude;
        this.longittude=longittude;
        this.phone=phone;

    }
}
