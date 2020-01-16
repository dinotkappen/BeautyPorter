package com.example.thebeautyporterapp.Model;

public class OfferModel {
    String offerID;
    String offerTitle;
    String offerDesc;
    String couponCode;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    String couponType;

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    String offerPrice;
    public OfferModel() {
    }
    public OfferModel(String offerID, String offerTitle, String offerDesc, String offerPrice, String couponCode, String couponType) {

        this.offerID = offerID;
        this.offerTitle = offerTitle;
        this.offerDesc = offerDesc;
        this.offerPrice=offerPrice;
        this.couponCode=couponCode;
        this.couponType=couponType;
    }
}
