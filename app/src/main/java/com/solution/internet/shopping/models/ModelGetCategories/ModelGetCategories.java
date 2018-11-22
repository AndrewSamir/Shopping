package com.solution.internet.shopping.models.ModelGetCategories;

import com.google.gson.annotations.SerializedName;

public class ModelGetCategories {


    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("category")
    private String category;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
