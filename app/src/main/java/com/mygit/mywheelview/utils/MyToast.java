package com.mygit.mywheelview.utils;

import android.content.Context;
import android.widget.Toast;

import com.mygit.mywheelview.MyApplication;


/**
 * 土司
 *
 * @author admin
 */
public class MyToast {

    public static void show(String str) {
        if (str != null) {
            Toast.makeText(MyApplication.getContext(), str, Toast.LENGTH_SHORT).show();
        }
    }

    public static void show(String str, Context context) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void show(String str, Context context, int time) {
        Toast.makeText(context, str, time).show();
    }

    public static void show(int resId, Context context) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
