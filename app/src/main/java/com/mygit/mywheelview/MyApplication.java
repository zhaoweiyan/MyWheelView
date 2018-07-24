package com.mygit.mywheelview;


import android.app.Application;
import android.content.Context;

/**
 * 应用
 *
 * @author admin
 */
public class MyApplication extends Application {

    private static Context sApplicationContext;

    public static int count = 0;//消息数量

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = getApplicationContext();
    }

    public static Context getContext() {
        return sApplicationContext;
    }
}


