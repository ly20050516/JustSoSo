package com.ly.framework.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ly.framework.context.ContextManager;

/**
 * Created by LY on 2017-06-10.
 */

public class SpModelUtils {

    private static SpModelUtils sInstance;
    private SharedPreferences mSharedPreferences;
    private static final String MODEL_NAME = "sp_mode";
    private static final int OPEN_MODE = Context.MODE_PRIVATE;
    private SpModelUtils() {
        mSharedPreferences = ContextManager.getApplicationContext().getSharedPreferences(MODEL_NAME,OPEN_MODE);
    }

    public static SpModelUtils getInstance() {
        if(sInstance == null) {
            sInstance = new SpModelUtils();
        }
        return sInstance;
    }

    public void setString(String key,String value) {
        if(TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return;
        }

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public String getString(String key,String defValue) {
        if(TextUtils.isEmpty(key)) {
            return null;
        }

        String value = mSharedPreferences.getString(key,defValue);
        return value;
    }
}
