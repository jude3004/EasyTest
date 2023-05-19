package com.example.easytest;

public class Sign {
    private String SignName;
    private String ImageUrl;

    public String getSignName() {
        return SignName;
    }

    public void setSignName(String signName) {
        SignName = signName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }


    public Sign() {
    }

    public Sign(String signName, String imageUrl) {
        SignName = signName;
        ImageUrl = imageUrl;
    }




}
