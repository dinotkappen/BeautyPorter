package com.example.thebeautyporterapp.Model.ServicesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coupon {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("coupon_name")
    @Expose
    private String couponName;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("valid_form")
    @Expose
    private String validForm;
    @SerializedName("valid_to")
    @Expose
    private String validTo;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getValidForm() {
        return validForm;
    }

    public void setValidForm(String validForm) {
        this.validForm = validForm;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

