package com.solution.internet.shopping.models.ModelCallDelivery;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelCallDelivery {


    @SerializedName("info")
    private Info info;
    @SerializedName("items")
    private List<Items> items;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
