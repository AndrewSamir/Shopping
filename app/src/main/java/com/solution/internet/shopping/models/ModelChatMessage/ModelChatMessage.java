package com.solution.internet.shopping.models.ModelChatMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChatMessage
{

    @Expose
    @SerializedName("time")
    private int time;
    @Expose
    @SerializedName("reply")
    private String reply;
    @Expose
    @SerializedName("cr_id")
    private int crId;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("fullname")
    private String fullname;
    @Expose
    @SerializedName("userid")
    private int userid;
    @Expose
    @SerializedName("c_id")
    private int cId;

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFullname()
    {
        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public int getCId()
    {
        return cId;
    }

    public void setCId(int cId)
    {
        this.cId = cId;
    }
}
