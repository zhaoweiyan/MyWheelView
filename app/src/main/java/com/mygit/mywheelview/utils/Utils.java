package com.mygit.mywheelview.utils;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 *
 */
public class Utils {

    public static boolean notEmpty(String str) {
        if (str != null && str.trim().length() > 0) {
            return true;
        } else
            return false;
    }

    public static boolean notEmpty(EditText text) {
        if (text.getText() != null && notEmpty(text.getText().toString())) {
            return true;
        } else
            return false;
    }

    public static boolean notEmpty(TextView text) {
        if (text.getText() != null && notEmpty(text.getText().toString())) {
            return true;
        } else
            return false;
    }

    public static void print(Object str) {
        System.out.println(str);
    }

    public static boolean match(String str1, String str2) {
        if (str1 != null && str2 != null) {
            if (str1.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    //留一个字符,其他用*号代替
    public static String getStarString(String str) {
        String starStr = "";
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() == 2) {
            starStr = "*" + str.substring(1);
        } else if (str.length() > 2) {
            for (int i = 0; i < str.length() - 1; i++) {
                stringBuffer.append("*");
            }
            starStr = str.replace(str.substring(0, str.length() - 1), stringBuffer.toString());
        } else {
            starStr = "";
        }
        return starStr;
    }

    //留4个字符,其他用*号代替
    public static String getStarfourString(String str) {
        String starStr = "*";
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() > 4) {
            for (int i = 0; i < str.length() - 4; i++) {
                stringBuffer.append("*");
                if (i == 2) {
                    stringBuffer.append(" ");
                } else if (i == 6) {
                    stringBuffer.append(" ");
                }
            }
            starStr = str.replace(str.substring(0, str.length() - 4), stringBuffer.toString());
        } else {
            starStr = "*";
        }
        return starStr;
    }

    /**
     * 减法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return 使用.doubleValue() 会失去最后小数点后的0
     */
    public static double sub(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 判断是否有网络连接 true网通的
     */
    public static boolean IsNetworkAvailble(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if ((info != null) && (info.isConnected())) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.e("",
                    "Get network state exception! \r\nDid you forgot to add \r\nandroid.permission.ACCESS_NETWORK_STATE in AndroidManifest.xml��\r\n");
        }
        Toast.makeText(context, "网络不给力呀",
                Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getViewHight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return height;
    }

    public static long getViewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return width;
    }


    /**
     * 获取系统版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }


    //字体变粗变细
    public static void setTextFold(TextView view, Boolean b) {
        TextPaint tp = view.getPaint();
        tp.setFakeBoldText(b);
    }

    //TextView的左右上下放置图片
    public static void setDrwableRight(Context context, int mipmap, TextView view) {
        view.setCompoundDrawables(null, null, setDrwable(context, mipmap, view), null);
    }

    public static void setDrwableTop(Context context, int mipmap, TextView view) {
        view.setCompoundDrawables(null, setDrwable(context, mipmap, view), null, null);
    }

    public static void setDrwableLeft(Context context, int mipmap, TextView view) {
        view.setCompoundDrawables(setDrwable(context, mipmap, view), null, null, null);
    }


    public static Drawable setDrwable(Context context, int mipmap, TextView view) {
        Drawable drawable = null;
        if (mipmap != -1) {
            try {
                drawable = context.getResources().getDrawable(mipmap);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return drawable;
    }

    //消息日起展示
    public static void getCreatDate(TextView tv_date, long createDate) {
        SimpleDateFormat formatter = null;
        if ((createDate / 1000 / 60 / 60 / 24) == (System.currentTimeMillis() / 1000 / 60 / 60 / 24)) {
            formatter = new SimpleDateFormat("HH:mm");
        } else {
            formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        }
        Date curDate = new Date(createDate);// 时间转化
        String str = formatter.format(curDate);
        tv_date.setText(str);
    }



    //复制字符串
    public static void copyOid(Context mContext, String oid) {
        if (Utils.notEmpty(oid)) {
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);  //粘贴板
            // 创建普通字符型ClipData  api11是android3.0多，肯定不存在了
            ClipData mClipData;
            mClipData = ClipData.newPlainText("Label", oid);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            MyToast.show("复制成功");
        } else {
            MyToast.show("复制内容不能为空");
        }

    }

    static SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm:ss ");

    public static String getReshTime() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }


    public static void setViewWidth(View view, int width) {

        //这里是headview防止为空加的处理
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        if (width > 0)
            linearParams.width = width;// 控件的宽强制设成
        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }

    /**
     * 验证字符串是否数字和字母组合
     *
     * @param str
     * @return
     */
    public static boolean isNumAlphabetMix(String str) {
        String telRegex = "(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,23}";
        if (TextUtils.isEmpty(str))
            return false;
        else
            return str.matches(telRegex);
    }

    /**
     * HH:mm:ss
     * Java Calendar 类时间操作
     *
     * @param countdownTime
     * @return
     */
    public static String getHms(String countdownTime) {
        long aLong = Long.parseLong(countdownTime);
        long temp = 0;
        StringBuffer sb = new StringBuffer();
        temp = aLong / (60 * 60 * 24);
        sb.append((temp < 10) ? "0" + temp + "天" : "" + temp + "天");

        temp = (aLong % (60 * 60 * 24)) / (60 * 60);
        sb.append((temp < 10) ? "0" + temp + "时" : "" + temp + "时");

        temp = (aLong % (60 * 60)) / (60);
        sb.append((temp < 10) ? "0" + temp + "分" : "" + temp + "分");
        return sb.toString();
    }

    /**
     * 关闭输入法
     *
     * @param context
     * @param view
     */
    public static void hideInputMethod(Context context, View view) {
        //如果输入法打开则关闭，如果没打开则打开
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        //关闭输入法窗口
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.SHOW_FORCED);
    }
}
