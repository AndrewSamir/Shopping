package com.solution.internet.shopping.models.ModelProductItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProductItem
{

    @Expose
    @SerializedName("cityName")
    private String cityname;
    @Expose
    @SerializedName("categoryName")
    private String categoryname;
    @Expose
    @SerializedName("photo")
    private String photo;
    @Expose
    @SerializedName("price")
    private int price;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("item_id")
    private int itemId;

    public String getCityname()
    {
        return cityname;
    }

    public void setCityname(String cityname)
    {
        this.cityname = cityname;
    }

    public String getCategoryname()
    {
        return categoryname;
    }

    public void setCategoryname(String categoryname)
    {
        this.categoryname = categoryname;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getItemId()
    {
        return itemId;
    }

    public void setItemId(int itemId)
    {
        this.itemId = itemId;
    }
}
