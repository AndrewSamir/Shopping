package com.solution.internet.shopping.models.ModelPrivateOrderInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offers
{
    @Expose
    @SerializedName("fullname")
    private String fullname;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("accepted")
    private int accepted;
    @Expose
    @SerializedName("price")
    private int price;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("userid")
    private int userid;
    @Expose
    @SerializedName("orderid")
    private int orderid;
    @Expose
    @SerializedName("offerid")
    private int offerid;

    public String getFullname()
    {
        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
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

    public int getAccepted()
    {
        return accepted;
    }

    public void setAccepted(int accepted)
    {
        this.accepted = accepted;
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

    public int getOfferid()
    {
        return offerid;
    }

    public void setOfferid(int offerid)
    {
        this.offerid = offerid;
    }
}
