package com.solution.internet.shopping.models.ModelCommenResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andre on 21-Jan-18.
 */

public class ModelCommenResponse {

    @SerializedName("status")
    private String Status;
    @SerializedName("link")
    private String link;
    @SerializedName("code")
    private int code;
    @SerializedName("c_id")
    private int c_id = -1;
    @SerializedName(value = "ResponseMessage", alternate = "message")
    private String ResponseMessage;

    @SerializedName("data")
    private Object Data;


    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String ResponseMessage) {
        this.ResponseMessage = ResponseMessage;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
