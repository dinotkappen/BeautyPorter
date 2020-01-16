package com.example.thebeautyporterapp.Model;

public class AppoinmentDetailModel {

    String idDetail;
    String imgDetail;
    String nameDetail;
    String timeDetail;
    String dateDetail;
    String serviceDetail;
    String priceDetail;

    public String getLattittude() {
        return lattittude;
    }

    public void setLattittude(String lattittude) {
        this.lattittude = lattittude;
    }

    public String getLongittude() {
        return longittude;
    }

    public void setLongittude(String longittude) {
        this.longittude = longittude;
    }

    String lattittude;
    String longittude;

    public String getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(String idDetail) {
        this.idDetail = idDetail;
    }

    public String getImgDetail() {
        return imgDetail;
    }

    public void setImgDetail(String imgDetail) {
        this.imgDetail = imgDetail;
    }

    public String getNameDetail() {
        return nameDetail;
    }

    public void setNameDetail(String nameDetail) {
        this.nameDetail = nameDetail;
    }

    public String getTimeDetail() {
        return timeDetail;
    }

    public void setTimeDetail(String timeDetail) {
        this.timeDetail = timeDetail;
    }

    public String getDateDetail() {
        return dateDetail;
    }

    public void setDateDetail(String dateDetail) {
        this.dateDetail = dateDetail;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(String priceDetail) {
        this.priceDetail = priceDetail;
    }

    public AppoinmentDetailModel() {
    }
    String contact_no;

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public AppoinmentDetailModel(String idDetail, String imgDetail, String nameDetail, String timeDetail, String dateDetail, String serviceDetail, String priceDetail, String lattittude, String longittude, String contact_no) {

        this.idDetail = idDetail;
        this.imgDetail = imgDetail;
        this.nameDetail = nameDetail;
        this.timeDetail=timeDetail;
        this.dateDetail=dateDetail;
        this.serviceDetail=serviceDetail;
        this.priceDetail=priceDetail;
        this.lattittude=lattittude;
        this.longittude=longittude;
        this.contact_no=contact_no;
    }
}
