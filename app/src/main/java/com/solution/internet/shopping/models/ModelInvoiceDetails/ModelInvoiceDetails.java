package com.solution.internet.shopping.models.ModelInvoiceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelInvoiceDetails
{

    @Expose
    @SerializedName("deliveryAvatar")
    private String deliveryavatar;
    @Expose
    @SerializedName("deliveryName")
    private String deliveryname;
    @Expose
    @SerializedName("avatar")
    private String avatar;
    @Expose
    @SerializedName("fullname")
    private String fullname;
    @Expose
    @SerializedName("c_id_fk")
    private int cIdFk;
    @Expose
    @SerializedName("time")
    private int time;
    @Expose
    @SerializedName("ip")
    private String ip;
    @Expose
    @SerializedName("user_id_fk")
    private int userIdFk;
    @Expose
    @SerializedName("accepted_by")
    private int acceptedBy;
    @Expose
    @SerializedName("price")
    private int price;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("reply")
    private String reply;
    @Expose
    @SerializedName("cr_id")
    private int crId;

    public String getDeliveryavatar()
    {
        return deliveryavatar;
    }

    public void setDeliveryavatar(String deliveryavatar)
    {
        this.deliveryavatar = deliveryavatar;
    }

    public String getDeliveryname()
    {
        return deliveryname;
    }

    public void setDeliveryname(String deliveryname)
    {
        this.deliveryname = deliveryname;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getFullname()
    {
        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public int getCIdFk()
    {
        return cIdFk;
    }

    public void setCIdFk(int cIdFk)
    {
        this.cIdFk = cIdFk;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getUserIdFk()
    {
        return userIdFk;
    }

    public void setUserIdFk(int userIdFk)
    {
        this.userIdFk = userIdFk;
    }

    public int getAcceptedBy()
    {
        return acceptedBy;
    }

    public void setAcceptedBy(int acceptedBy)
    {
        this.acceptedBy = acceptedBy;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getReply()
    {
        return reply;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }

    public int getCrId()
    {
        return crId;
    }

    public void setCrId(int crId)
    {
        this.crId = crId;
    }
}
