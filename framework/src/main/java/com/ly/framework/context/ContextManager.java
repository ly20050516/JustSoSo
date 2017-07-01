package com.ly.framework.context;

import android.app.Application;
import android.content.Context;

/**
 * Created by ly on 2017/7/1.
 */

public class ContextManager {

    private static Context mApplicationContext;

    public static void initApplication(Application application) {
        mApplicationContext = application;
    }

    public static Context getApplicationContext() {
        return mApplicationContext;
    }
}
