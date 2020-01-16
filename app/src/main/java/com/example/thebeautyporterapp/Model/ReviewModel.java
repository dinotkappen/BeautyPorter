package com.example.thebeautyporterapp.Model;

public class ReviewModel {

    String reviewID;

    public String getReviewImgUrl() {
        return reviewImgUrl;
    }

    public void setReviewImgUrl(String reviewImgUrl) {
        this.reviewImgUrl = reviewImgUrl;
    }

    String reviewImgUrl;

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewCmnt() {
        return reviewCmnt;
    }

    public void setReviewCmnt(String reviewCmnt) {
        this.reviewCmnt = reviewCmnt;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    String reviewName;
    String reviewDate;
    String reviewCmnt;
    String reviewCount;

    public ReviewModel() {
    }
    public ReviewModel(String reviewID, String reviewName, String reviewDate, String reviewCmnt, String reviewCount, String reviewImgUrl) {

        this.reviewID=reviewID;
        this.reviewName = reviewName;
        this.reviewDate = reviewDate;
        this.reviewCmnt = reviewCmnt;
        this.reviewCount=reviewCount;
        this.reviewImgUrl=reviewImgUrl;
    }
}
