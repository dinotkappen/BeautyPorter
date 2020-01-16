package com.example.thebeautyporterapp.Model;

import java.util.List;

public class BookingDetailsModel {
    String bookingID;
    String bookedDate;
    String mode;

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getBookedPrice() {
        return bookedPrice;
    }

    public void setBookedPrice(String bookedPrice) {
        this.bookedPrice = bookedPrice;
    }

    public String getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(String bookedTime) {
        this.bookedTime = bookedTime;
    }

    public String getBookedServiceName() {
        return bookedServiceName;
    }

    public void setBookedServiceName(String bookedServiceName) {
        this.bookedServiceName = bookedServiceName;
    }

    public String getBookedServiceID() {
        return bookedServiceID;
    }

    public void setBookedServiceID(String bookedServiceID) {
        this.bookedServiceID = bookedServiceID;
    }

    public String getBookedStaffId() {
        return bookedStaffId;
    }

    public void setBookedStaffId(String bookedStaffId) {
        this.bookedStaffId = bookedStaffId;
    }

    String bookedPrice;
    String bookedTime;
    String bookedServiceName;
    String bookedServiceID;
    String bookedStaffId;

    private List<GuestOrderModel> guestOrderModel = null;
    public BookingDetailsModel() {
    }

    public List<GuestOrderModel> getGuestOrderModel() {
        return guestOrderModel;
    }

    public void setGuestOrderModel(List<GuestOrderModel> guestOrderModel) {
        this.guestOrderModel = guestOrderModel;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public BookingDetailsModel(String bookingID, String bookedDate, String bookedTime, String bookedServiceID, String bookedServiceName, String bookedStaffId, String bookedPrice, String mode, List<GuestOrderModel> guestOrderModel) {

        this.bookingID = bookingID;
        this.bookedDate = bookedDate;
        this.bookedTime = bookedTime;
        this.bookedServiceID=bookedServiceID;
        this.bookedServiceName=bookedServiceName;
        this.bookedStaffId=bookedStaffId;
        this.bookedPrice=bookedPrice;
        this.mode=mode;
        this.guestOrderModel=guestOrderModel;
    }
}
