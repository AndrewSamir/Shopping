package com.solution.internet.shopping.singleton;


public class SingletonShopping {

    private static SingletonShopping mInstance = null;
    private String conversationId;
    private int c_id,chatUserId,cr_id;

    private SingletonShopping() {
    }


    public static SingletonShopping getInstance() {
        if (mInstance == null) {
            mInstance = new SingletonShopping();
        }
        return mInstance;
    }

    public static SingletonShopping clearAll() {

        mInstance = new SingletonShopping();
        return mInstance;
    }

    public static SingletonShopping getmInstance() {
        return mInstance;
    }

    public static void setmInstance(SingletonShopping mInstance) {
        SingletonShopping.mInstance = mInstance;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(int chatUserId) {
        this.chatUserId = chatUserId;
    }

    public int getCr_id() {
        return cr_id;
    }

    public void setCr_id(int cr_id) {
        this.cr_id = cr_id;
    }
}
