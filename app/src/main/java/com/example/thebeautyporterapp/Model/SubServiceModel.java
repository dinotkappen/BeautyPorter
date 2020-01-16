package com.example.thebeautyporterapp.Model;

public class SubServiceModel {

    String subServiceID;

    public String getSubServiceDescription() {
        return subServiceDescription;
    }

    public void setSubServiceDescription(String subServiceDescription) {
        this.subServiceDescription = subServiceDescription;
    }

    String subServiceDescription;

    public String getSelectedMainServiceID() {
        return selectedMainServiceID;
    }

    public void setSelectedMainServiceID(String selectedMainServiceID) {
        this.selectedMainServiceID = selectedMainServiceID;
    }

    String selectedMainServiceID;

    public String getSubServiceID() {
        return subServiceID;
    }

    public void setSubServiceID(String subServiceID) {
        this.subServiceID = subServiceID;
    }

    public String getSubServiceImgUrl() {
        return subServiceImgUrl;
    }

    public void setSubServiceImgUrl(String subServiceImgUrl) {
        this.subServiceImgUrl = subServiceImgUrl;
    }

    public String getSubServiceTitle() {
        return subServiceTitle;
    }

    public void setSubServiceTitle(String subServiceTitle) {
        this.subServiceTitle = subServiceTitle;
    }

    public String getSubServiceRating() {
        return subServiceRating;
    }

    public void setSubServiceRating(String subServiceRating) {
        this.subServiceRating = subServiceRating;
    }

    public String getSubServiceAdrz() {
        return subServiceAdrz;
    }

    public void setSubServiceAdrz(String subServiceAdrz) {
        this.subServiceAdrz = subServiceAdrz;
    }

    String subServiceImgUrl;
    String subServiceTitle;
    String subServiceRating;
    String subServiceAdrz;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    String latitude;
    String longitude;

    public String getIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(String isfavourite) {
        this.isfavourite = isfavourite;
    }

    String isfavourite;

    public String getServicesIDS() {
        return servicesIDS;
    }

    public void setServicesIDS(String servicesIDS) {
        this.servicesIDS = servicesIDS;
    }

    String servicesIDS;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String location;

    public SubServiceModel() {
    }

    public SubServiceModel(String subServiceID,String subServiceTitle, String subServiceImgUrl , String subServiceRating, String subServiceAdrz,String servicesIDS,String selectedMainServiceID,String subServiceDescription,String latitude,String longitude,String isfavourite,String location) {

        this.subServiceID = subServiceID;
        this.subServiceImgUrl = subServiceImgUrl;
        this.subServiceTitle = subServiceTitle;
        this.subServiceRating = subServiceRating;
        this.subServiceAdrz = subServiceAdrz;
        this.servicesIDS=servicesIDS;
        this.selectedMainServiceID=selectedMainServiceID;
        this.subServiceDescription=subServiceDescription;
        this.latitude=latitude;
        this.longitude=longitude;
        this.isfavourite=isfavourite;
        this.location=location;
    }
}
