package com.example.tavanyab.application;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.tavanyab.R;
import com.example.tavanyab.db.DBHelpeOld;
import com.example.tavanyab.fragment.AddChildFragment;
import com.example.tavanyab.model.EventBusModel;

import org.greenrobot.eventbus.EventBus;

public class Application extends android.app.Application {

    private DBHelpeOld helper;
    private static Application instance;
    private FragmentTransaction ft;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        helper = new DBHelpeOld(getApplicationContext());
    }

    public static synchronized Application getInstance() {
        return instance;
    }

    public DBHelpeOld getDatebaseHelper() {
        return helper;
    }

    public void gotoFragment(Fragment fragment,Activity activity, String name) {
        EventBus.getDefault().post(new EventBusModel(name));
        ft = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void addToSharedPreferences(String prefName,String key ,String value){
        SharedPreferences.Editor editor = getSharedPreferences(prefName, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }
}
