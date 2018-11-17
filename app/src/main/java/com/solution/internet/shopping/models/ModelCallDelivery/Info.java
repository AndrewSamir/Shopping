package com.solution.internet.shopping.models.ModelCallDelivery;

import com.google.gson.annotations.SerializedName;

public class Info {
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
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("api_token")
    private String apiToken;
    @SerializedName("resetcode")
    private String resetcode;
    @SerializedName("customer_status")
    private String customerStatus;
    @SerializedName("country_id")
    private int countryId;
    @SerializedName("city_id")
    private int cityId;
    @SerializedName("area_id")
    private int areaId;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;
    @SerializedName("userType")
    private String usertype;
    @SerializedName("notes")
    private String notes;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getResetcode() {
        return resetcode;
    }

    public void setResetcode(String resetcode) {
        this.resetcode = resetcode;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
