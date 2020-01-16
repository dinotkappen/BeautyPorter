
package com.example.thebeautyporterapp.Model.ServicesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessImage {

    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("image")
    @Expose
    private String image;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
