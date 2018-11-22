package com.solution.internet.shopping.models.ModelGetCities;

import com.google.gson.annotations.SerializedName;

public class ModelGetCities {

    @SerializedName("city_id")
    private int cityId;
    @SerializedName("city")
    private String city;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
