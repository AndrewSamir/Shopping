package com.solution.internet.shopping.models.ModelPrivateOrderOfferNewRequest;

public class ModelPrivateOrderOfferNewRequest
{
    int orderid;
    int offerid;
    String content;
    String price;

    public int getOfferid()
    {
        return offerid;
    }

    public void setOfferid(int offerid)
    {
        this.offerid = offerid;
    }

    public int getOrderid()
    {
        return orderid;
    }

    public void setOrderid(int orderid)
    {
        this.orderid = orderid;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }
}
