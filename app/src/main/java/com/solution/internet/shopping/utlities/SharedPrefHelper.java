package com.solution.internet.shopping.utlities;

import android.content.Context;
import android.content.SharedPreferences;


import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by andre on 22-Jan-18.
 */

public class SharedPrefHelper
{
    private static Context context;
    private static SharedPrefHelper instance = null;
    private static SharedPreferences prefs = null;
    private static SharedPreferences.Editor editor;

    public static SharedPrefHelper getInstance(Context context)
    {
        SharedPrefHelper.context = context;

        if (instance == null)
        {
            instance = new SharedPrefHelper();
            SharedPrefHelper.prefs = context.getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        }
        return instance;
    }


    public void setUser(ModelLoginResponse modelLoginResponse)
    {
        editor = context.getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putInt(DataEnum.shUserID.name(), modelLoginResponse.getUserid());
        editor.putString(DataEnum.shApiToken.name(), modelLoginResponse.getApiToken());
        editor.putString(DataEnum.shFullName.name(), modelLoginResponse.getFullname());
        editor.putString(DataEnum.shMobile.name(), modelLoginResponse.getMobile());

        editor.apply();
    }

    public String getUserid()
    {
        return prefs.getString(DataEnum.shUserID.name(), null);
    }

    public String getApiToken()
    {
        return prefs.getString(DataEnum.shApiToken.name(), null);
    }


    public String getFullName()
    {
        return prefs.getString(DataEnum.shFullName.name(), null);
    }

    public void signOut()
    {

      /*  editor = context.getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.remove(DataEnum.shUserID.name());
        editor.remove(DataEnum.shAccessToken.name());
        editor.remove(DataEnum.shProfileIdentifier.name());
        editor.remove(DataEnum.shUserName.name());
        editor.remove(DataEnum.shFullName.name());
        editor.remove(DataEnum.shBio.name());
        editor.remove(DataEnum.shMobile.name());
        editor.remove(DataEnum.shIsVerified.name());
        editor.remove(DataEnum.shImageLocation.name());
        editor.remove(DataEnum.shCountryID.name());
        editor.remove(DataEnum.shCountryName.name());
        editor.remove(DataEnum.shFollowersCount.name());
        editor.remove(DataEnum.shFollowingCount.name());
        editor.remove(DataEnum.shSharesCount.name());*/

        editor.apply();
    }
}
