package com.solution.internet.shopping.firebaseNotifications;


import com.solution.internet.shopping.models.ModelChatMessage.ModelChatMessage;

public class NotificationData {

    private String body, type, title;
    private ModelChatMessage modelChatMessage;
    private int itemid;


    public NotificationData() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public ModelChatMessage getModelChatMessage() {
        return modelChatMessage;
    }

    public void setModelChatMessage(ModelChatMessage modelChatMessage) {
        this.modelChatMessage = modelChatMessage;
    }
}
