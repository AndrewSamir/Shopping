package com.solution.internet.shopping.models.ModelRefreshTokenRequest;

public class ModelRefreshTokenRequest
{
    String deviceToken;
    String deviceType = "android";

    public String getDeviceToken()
    {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken)
    {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
}
