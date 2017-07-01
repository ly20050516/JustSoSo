package com.ly.justsoso;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.ly.framework.context.ContextManager;
import com.ly.justsoso.greendao.DaoMaster;
import com.ly.justsoso.greendao.DaoSession;

/**
 * Created by LY on 2017-06-10.
 */

public class JustSoSoApplication extends Application {

    private static JustSoSoApplication sInstance;
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private SQLiteDatabase mSqliteDataBase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initGreenDao();
        initContextManager();
    }

    private void initContextManager() {
        ContextManager.initApplication(this);
    }

    public static JustSoSoApplication getInstance() {
        return sInstance;
    }

    private void initGreenDao() {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(this,"picture",null);
        mSqliteDataBase = mDevOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mSqliteDataBase);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDataBase() {
        return mSqliteDataBase;
    }
}
