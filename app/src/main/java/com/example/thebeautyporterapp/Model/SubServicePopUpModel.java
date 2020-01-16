package com.example.thebeautyporterapp.Model;

public class SubServicePopUpModel {
    String id;
    String name;

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    String business_id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    Boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }






    public SubServicePopUpModel() {
    }

    public SubServicePopUpModel(String id, String name, String price, Boolean status, String business_id) {
        this.id = id;
        this.name=name;
        this.price=price;
        this.status=status;
        this.business_id=business_id;


    }
}
