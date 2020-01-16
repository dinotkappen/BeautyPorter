
package com.example.thebeautyporterapp.Model.ServicesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessStaff {

    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("staff_name")
    @Expose
    private String staffName;
    @SerializedName("image")
    @Expose
    private String image;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
