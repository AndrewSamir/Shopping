package com.solution.internet.shopping.application;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.solution.internet.shopping.R;

import io.fabric.sdk.android.Fabric;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ShoppingApplication extends MultiDexApplication
{
    Configuration config;
    Locale locale;

    private static ShoppingApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;

        initLang();


//        Fabric.with(this, new Crashlytics());
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        Twitter.initialize(this);
//        Log.e("h", "ad");
//          printKeyHash();
//        printKeyHash();
        initFont();

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

    public static ShoppingApplication getInstance()
    {
        return instance;
    }

    private void initFont()
    {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/GE SS Two Medium.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void initLang()
    {

        //  String lang = SharedPrefUtil.getInstance(getApplicationContext()).read("settingLangName", "en");
        String lang = "ar";
        locale = new Locale(lang);
        Locale.setDefault(locale);
        config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public void printKeyHash()
    {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("VIVZ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e)
        {

        } catch (NoSuchAlgorithmException e)
        {

        }
    }

    public void clearApplicationData()
    {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists())
        {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames)
            {
                if (!fileName.equals("lib"))
                {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    public static boolean deleteFile(File file)
    {
        boolean deletedAll = true;
        if (file != null)
        {
            if (file.isDirectory())
            {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++)
                {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else
            {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }

   /*
    public Socket getSocket() {
        return mSocket;
    }
    */
}
