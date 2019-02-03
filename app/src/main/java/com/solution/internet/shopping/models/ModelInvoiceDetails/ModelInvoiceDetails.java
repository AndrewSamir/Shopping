package com.solution.internet.shopping.models.ModelInvoiceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelInvoiceDetails {

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
    @Expose
    @SerializedName("confirm_seller")
    private int confirm_seller;
    @Expose
    @SerializedName("confirm_buyer")
    private int confirm_buyer;
    @Expose
    @SerializedName("receipt_id")
    private int receipt_id;
    @Expose
    @SerializedName("isPaid")
    private int isPaid;

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public int getConfirm_seller() {
        return confirm_seller;
    }

    public void setConfirm_seller(int confirm_seller) {
        this.confirm_seller = confirm_seller;
    }

    public int getConfirm_buyer() {
        return confirm_buyer;
    }

    public void setConfirm_buyer(int confirm_buyer) {
        this.confirm_buyer = confirm_buyer;
    }

    public String getDeliveryavatar() {
        return deliveryavatar;
    }

    public void setDeliveryavatar(String deliveryavatar) {
        this.deliveryavatar = deliveryavatar;
    }

    public String getDeliveryname() {
        return deliveryname;
    }

    public void setDeliveryname(String deliveryname) {
        this.deliveryname = deliveryname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getCIdFk() {
        return cIdFk;
    }

    public void setCIdFk(int cIdFk) {
        this.cIdFk = cIdFk;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(int userIdFk) {
        this.userIdFk = userIdFk;
    }

    public int getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(int acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getCrId() {
        return crId;
    }

    public void setCrId(int crId) {
        this.crId = crId;
    }
}
