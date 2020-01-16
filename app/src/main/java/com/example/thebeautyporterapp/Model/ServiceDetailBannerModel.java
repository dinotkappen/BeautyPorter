package com.example.thebeautyporterapp.Model;

public class ServiceDetailBannerModel {

    String bannerID;

    public String getBannerID() {
        return bannerID;
    }

    public void setBannerID(String bannerID) {
        this.bannerID = bannerID;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    String bannerName;
    String bannerImg;
    String serviceID;


    public ServiceDetailBannerModel(String bannerID,String bannerName, String bannerImg,String serviceID) {

        this.bannerID = bannerID;
        this.bannerName = bannerName;
        this.bannerImg = bannerImg;
        this.serviceID=serviceID;
    }



}
