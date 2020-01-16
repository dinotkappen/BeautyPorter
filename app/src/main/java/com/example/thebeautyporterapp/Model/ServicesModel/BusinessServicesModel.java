
package com.example.thebeautyporterapp.Model.ServicesModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessServicesModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("content")
    @Expose
    private List<Content> content = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

}
