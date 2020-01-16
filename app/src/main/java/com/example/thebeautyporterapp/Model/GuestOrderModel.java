package com.example.thebeautyporterapp.Model;

public class GuestOrderModel {
    String guestID,bookedDate, serviceId, serviceName, cost, guestName;

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

//    public GuestOrderModel(String guestID, String bookedDate, String serviceId, String serviceName, String cost, String guestName) {
//
//        this.guestID = guestID;
//        this.bookedDate = bookedDate;
//        this.serviceId = serviceId;
//        this.serviceName=serviceName;
//        this.cost=cost;
//        this.guestName=guestName;
//
//    }
}
