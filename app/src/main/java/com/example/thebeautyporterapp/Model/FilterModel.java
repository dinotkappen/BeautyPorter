package com.example.thebeautyporterapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("time_type")
    @Expose
    private String timeType;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("service_staff")
    @Expose
    private String serviceStaff;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getServiceStaff() {
        return serviceStaff;
    }

    public void setServiceStaff(String serviceStaff) {
        this.serviceStaff = serviceStaff;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
