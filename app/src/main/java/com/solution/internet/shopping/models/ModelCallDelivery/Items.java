package com.solution.internet.shopping.models.ModelCallDelivery;

import com.google.gson.annotations.SerializedName;

public class Items {
    @SerializedName("item_id")
    private int itemId;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private int price;
    @SerializedName("photo")
    private String photo;
    @SerializedName("categoryName")
    private String categoryname;
    @SerializedName("cityName")
    private String cityname;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
