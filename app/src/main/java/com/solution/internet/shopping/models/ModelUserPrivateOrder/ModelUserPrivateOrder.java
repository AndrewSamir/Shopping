package com.solution.internet.shopping.models.ModelUserPrivateOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUserPrivateOrder
{

    @Expose
    @SerializedName("cityName")
    private String cityname;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("photo")
    private String photo;
    @Expose
    @SerializedName("price")
    private int price;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("cityid")
    private int cityid;
    @Expose
    @SerializedName("accepted_delivery")
    private int acceptedDelivery;
    @Expose
    @SerializedName("userid")
    private int userid;
    @Expose
    @SerializedName("orderid")
    private int orderid;

    public String getCityname()
    {
        return cityname;
    }

    public void setCityname(String cityname)
    {
        this.cityname = cityname;
    }

    public String getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
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

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getCityid()
    {
        return cityid;
    }

    public void setCityid(int cityid)
    {
        this.cityid = cityid;
    }

    public int getAcceptedDelivery()
    {
        return acceptedDelivery;
    }

    public void setAcceptedDelivery(int acceptedDelivery)
    {
        this.acceptedDelivery = acceptedDelivery;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public int getOrderid()
    {
        return orderid;
    }

    public void setOrderid(int orderid)
    {
        this.orderid = orderid;
    }
}
