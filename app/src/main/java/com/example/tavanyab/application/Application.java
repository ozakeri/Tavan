package com.example.tavanyab.application;

import com.example.tavanyab.db.DBHelper;

public class Application extends android.app.Application {

    private DBHelper helper;
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        helper = new DBHelper(getApplicationContext());
    }

    public static synchronized Application getInstance() {
        return instance;
    }

    public DBHelper getDatebaseHelper() {
        return helper;
    }
}
