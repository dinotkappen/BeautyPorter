package com.example.thebeautyporterapp.Model;

public class ReviewCountModel {
    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String totalRating,rating;

    public ReviewCountModel() {
    }
    public ReviewCountModel(String totalRating, String rating) {

        this.totalRating=totalRating;
        this.rating = rating;

    }
}
