package com.solution.internet.shopping.utlities;

import android.content.Context;
import android.content.SharedPreferences;


import com.solution.internet.shopping.models.ModelLoginResponse.ModelLoginResponse;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by andre on 22-Jan-18.
 */

public class SharedPrefHelper {
    private static Context context;
    private static SharedPrefHelper instance = null;
    private static SharedPreferences prefs = null;
    private static SharedPreferences.Editor editor;

    public static SharedPrefHelper getInstance(Context context) {
        SharedPrefHelper.context = context;

        if (instance == null) {
            instance = new SharedPrefHelper();
            SharedPrefHelper.prefs = context.getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        }
        return instance;
    }


    public void setUser(ModelLoginResponse modelLoginResponse) {
        editor = context.getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.putInt(DataEnum.shUserID.name(), modelLoginResponse.getUserid());
        editor.putString(DataEnum.shApiToken.name(), modelLoginResponse.getApiToken());
        editor.putString(DataEnum.shFullName.name(), modelLoginResponse.getFullname());
        editor.putString(DataEnum.shMobile.name(), modelLoginResponse.getMobile());
        editor.putString(DataEnum.shType.name(), modelLoginResponse.getUsertype());
        editor.putString(DataEnum.shAvatar.name(), modelLoginResponse.getAvatar());
        editor.putInt(DataEnum.shWallet.name(), modelLoginResponse.getWallet());

        editor.apply();
    }

    public int getUserid() {
        return prefs.getInt(DataEnum.shUserID.name(), -1);
    }

    public int getWallet() {
        return prefs.getInt(DataEnum.shWallet.name(), 0);
    }

    public String getApiToken() {
        return prefs.getString(DataEnum.shApiToken.name(), null);
    }

    public String getAvatar() {
        return prefs.getString(DataEnum.shAvatar.name(), "empty string");
    }


    public String getFullName() {
        return prefs.getString(DataEnum.shFullName.name(), null);
    }

    public String getUserType() {
        return prefs.getString(DataEnum.shType.name(), null);
    }

    public void signOut() {

        editor = context.getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
        editor.remove(DataEnum.shUserID.name());
        editor.remove(DataEnum.shApiToken.name());
        editor.remove(DataEnum.shFullName.name());
        editor.remove(DataEnum.shMobile.name());
        editor.remove(DataEnum.shType.name());

        editor.apply();
    }
}
