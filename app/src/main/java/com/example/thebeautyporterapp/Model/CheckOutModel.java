package com.example.thebeautyporterapp.Model;

import java.util.List;

public class CheckOutModel {
String id;
    String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String time;
    String guest;
    String serviceName;
    String price;

    public List<GuestOrderModel> getGuestOrderModel() {
        return guestOrderModel;
    }

    public void setGuestOrderModel(List<GuestOrderModel> guestOrderModel) {
        this.guestOrderModel = guestOrderModel;
    }

    private List<GuestOrderModel> guestOrderModel = null;
    public CheckOutModel(String id, String date, String time,String guest,String serviceName,String price,List<GuestOrderModel> guestOrderModel) {

        this.id = id;
        this.date = date;
        this.time = time;
        this.guest=guest;
        this.serviceName=serviceName;
        this.price=price;
        this.guestOrderModel=guestOrderModel;
    }
}
