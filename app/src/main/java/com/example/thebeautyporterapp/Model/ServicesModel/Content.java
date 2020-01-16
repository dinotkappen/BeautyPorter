
package com.example.thebeautyporterapp.Model.ServicesModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("business_type")
    @Expose
    private String businessType;
    @SerializedName("specialization")
    @Expose
    private String specialization;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_ip")
    @Expose
    private String createdIp;
    @SerializedName("modified_ip")
    @Expose
    private String modifiedIp;
    @SerializedName("service_ids")
    @Expose
    private String serviceIds;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("street_name")
    @Expose
    private String streetName;
    @SerializedName("street_number")
    @Expose
    private String streetNumber;
    @SerializedName("zone_number")
    @Expose
    private String zoneNumber;
    @SerializedName("building_number")
    @Expose
    private String buildingNumber;
    @SerializedName("minimum_price")
    @Expose
    private String minimumPrice;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("isfavourite")
    @Expose
    private String isfavourite;
    @SerializedName("locations")
    @Expose
    private String locations;
    @SerializedName("BusinessServices")
    @Expose
    private List<BusinessService> businessServices = null;
    @SerializedName("business_categories")
    @Expose
    private BusinessCategories businessCategories;
    @SerializedName("business_time")
    @Expose
    private List<BusinessTime> businessTime = null;
    @SerializedName("business_images")
    @Expose
    private List<BusinessImage> businessImages = null;
    @SerializedName("business_staff")
    @Expose
    private List<BusinessStaff> businessStaff = null;
    @SerializedName("coupons")
    @Expose
    private List<Coupon> coupons = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getModifiedIp() {
        return modifiedIp;
    }

    public void setModifiedIp(String modifiedIp) {
        this.modifiedIp = modifiedIp;
    }

    public String getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(String serviceIds) {
        this.serviceIds = serviceIds;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZoneNumber() {
        return zoneNumber;
    }

    public void setZoneNumber(String zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(String minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(String isfavourite) {
        this.isfavourite = isfavourite;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public List<BusinessService> getBusinessServices() {
        return businessServices;
    }

    public void setBusinessServices(List<BusinessService> businessServices) {
        this.businessServices = businessServices;
    }

    public BusinessCategories getBusinessCategories() {
        return businessCategories;
    }

    public void setBusinessCategories(BusinessCategories businessCategories) {
        this.businessCategories = businessCategories;
    }

    public List<BusinessTime> getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(List<BusinessTime> businessTime) {
        this.businessTime = businessTime;
    }

    public List<BusinessImage> getBusinessImages() {
        return businessImages;
    }

    public void setBusinessImages(List<BusinessImage> businessImages) {
        this.businessImages = businessImages;
    }

    public List<BusinessStaff> getBusinessStaff() {
        return businessStaff;
    }

    public void setBusinessStaff(List<BusinessStaff> businessStaff) {
        this.businessStaff = businessStaff;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

}
