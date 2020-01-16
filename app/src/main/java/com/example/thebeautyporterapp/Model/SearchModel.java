package com.example.thebeautyporterapp.Model;

public class SearchModel {
    String searchId;

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchImgUrl() {
        return searchImgUrl;
    }

    public void setSearchImgUrl(String searchImgUrl) {
        this.searchImgUrl = searchImgUrl;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getSearchRating() {
        return searchRating;
    }

    public void setSearchRating(String searchRating) {
        this.searchRating = searchRating;
    }

    public String getSearchAdrz() {
        return searchAdrz;
    }

    public void setSearchAdrz(String searchAdrz) {
        this.searchAdrz = searchAdrz;
    }

    String searchImgUrl;
    String searchTitle;
    String searchRating;
    String searchAdrz;

    public SearchModel() {
    }
    public SearchModel(String searchId, String searchImgUrl, String searchTitle, String searchRating, String searchAdrz) {

        this.searchId=searchId;
        this.searchImgUrl = searchImgUrl;
        this.searchTitle = searchTitle;
        this.searchRating = searchRating;
        this.searchAdrz=searchAdrz;
    }
}

