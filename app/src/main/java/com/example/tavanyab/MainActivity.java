package com.example.tavanyab;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.tavanyab.application.Application;
import com.example.tavanyab.drawer.DrawerHeader;
import com.example.tavanyab.drawer.DrawerMenuItem;
import com.example.tavanyab.fragment.HomeFragment;
import com.example.tavanyab.fragment.ResultEvaluatingFragment;
import com.example.tavanyab.model.EventBusModel;
import com.mindorks.placeholderview.PlaceHolderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private String currentPage = null;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = findViewById(R.id.drawer_layout);
        mDrawerView = findViewById(R.id.drawerView);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        AppCompatImageView img_menu = mToolbar.findViewById(R.id.img_menu);
        AppCompatImageView img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.GONE);

        ViewCompat.setLayoutDirection(mToolbar, ViewCompat.LAYOUT_DIRECTION_RTL);
        setupDrawer();

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawer.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawer.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        Application.getInstance().gotoFragment(new HomeFragment(), this, "HomeFragment");
    }


    private void setupDrawer() {
        mDrawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_REQUESTS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_GROUPS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_TERMS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT));
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Subscribe
    public void onEvent(EventBusModel event) {
        System.out.println("getCurrentPage" + event.getCurrentPage());
        currentPage = event.getCurrentPage();
        System.out.println("currentPage=111====" + currentPage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("currentActivity", MODE_PRIVATE);
        String name = prefs.getString("currentPage", "No name defined");
        System.out.println("currentPage=222====" + name);
        if (name != null && name.equals("StartEvaluatingActivity")) {
            Application.getInstance().gotoFragment(new ResultEvaluatingFragment(), this, "ResultEvaluatingFragment");
            Application.getInstance().addToSharedPreferences("currentActivity", "currentPage", null);
        }
    }
}
