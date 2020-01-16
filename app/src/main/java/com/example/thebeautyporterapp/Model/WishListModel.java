package com.example.thebeautyporterapp.Model;

public class WishListModel {
    public String getWishListImgUrl() {
        return wishListImgUrl;
    }

    public void setWishListImgUrl(String wishListImgUrl) {
        this.wishListImgUrl = wishListImgUrl;
    }

    public String getWishListTitle() {
        return wishListTitle;
    }

    public void setWishListTitle(String wishListTitle) {
        this.wishListTitle = wishListTitle;
    }

    public String getWishListRating() {
        return wishListRating;
    }

    public void setWishListRating(String wishListRating) {
        this.wishListRating = wishListRating;
    }

    public String getWishListAdrz() {
        return wishListAdrz;
    }

    public void setWishListAdrz(String wishListAdrz) {
        this.wishListAdrz = wishListAdrz;
    }

    String wishListImgUrl;
    String wishListTitle;
    String wishListRating;
    String wishListAdrz;

    public String getWishListId() {
        return wishListId;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }

    String wishListId;
    public WishListModel() {
    }
    public WishListModel(String wishListId, String wishListImgUrl, String wishListTitle, String wishListRating, String wishListAdrz) {

        this.wishListId=wishListId;
        this.wishListImgUrl = wishListImgUrl;
        this.wishListTitle = wishListTitle;
        this.wishListRating = wishListRating;
        this.wishListAdrz=wishListAdrz;
    }
}
