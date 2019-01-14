package com.solution.internet.shopping.firebaseNotifications;


public class NotificationData
{

    private String ConversationName, Type, FirstWriterID, SecondWriterID,
            ConversationID, Text, WriterID, ToUserID,UserID,Title,Body;

    public NotificationData()
    {
    }

    public String getConversationName()
    {
        return ConversationName;
    }

    public void setConversationName(String conversationName)
    {
        ConversationName = conversationName;
    }

    public String getType()
    {
        return Type;
    }

    public void setType(String type)
    {
        Type = type;
    }

    public String getFirstWriterID()
    {
        return FirstWriterID;
    }

    public void setFirstWriterID(String firstWriterID)
    {
        FirstWriterID = firstWriterID;
    }

    public String getSecondWriterID()
    {
        return SecondWriterID;
    }

    public void setSecondWriterID(String secondWriterID)
    {
        SecondWriterID = secondWriterID;
    }

    public String getConversationID()
    {
        return ConversationID;
    }

    public void setConversationID(String conversationID)
    {
        ConversationID = conversationID;
    }

    public String getText()
    {
        return Text;
    }

    public void setText(String text)
    {
        Text = text;
    }

    public String getWriterID()
    {
        return WriterID;
    }

    public void setWriterID(String writerID)
    {
        WriterID = writerID;
    }

    public String getToUserID()
    {
        return ToUserID;
    }

    public void setToUserID(String toUserID)
    {
        ToUserID = toUserID;
    }

    public String getUserID()
    {
        return UserID;
    }

    public void setUserID(String userID)
    {
        UserID = userID;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getBody()
    {
        return Body;
    }

    public void setBody(String body)
    {
        Body = body;
    }
}
