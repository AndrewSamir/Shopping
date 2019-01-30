package com.solution.internet.shopping.models.ModelLoginResponse;

import com.google.gson.annotations.SerializedName;

public class ModelLoginResponse {


    @SerializedName("userid")
    private int userid;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("email")
    private String email;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("mobile_st")
    private int mobileSt;
    @SerializedName("userType")
    private String usertype;
    @SerializedName("country_id")
    private int countryId;
    @SerializedName("city_id")
    private int cityId;
    @SerializedName("wallet")
    private int wallet;
    @SerializedName("area_id")
    private int areaId;
    @SerializedName("api_token")
    private String apiToken;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("created_at")
    private CreatedAt createdAt;

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getMobileSt() {
        return mobileSt;
    }

    public void setMobileSt(int mobileSt) {
        this.mobileSt = mobileSt;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }
}
