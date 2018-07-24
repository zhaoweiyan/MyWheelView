package com.mygit.mywheelview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mygit.mywheelview.calendar.ISelectTimeCallback;
import com.mygit.mywheelview.calendar.WheelTime;
import com.mygit.mywheelview.calendar.WheelView;
import com.mygit.mywheelview.utils.MyToast;
import com.mygit.mywheelview.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2018/4/13.
 */

public class CalenDarActivity extends Activity {
    private CalenDarActivity mContext;

    @Bind(R.id.titlebar)
    TitleBarView titlebar;

    @Bind(R.id.tv_start_time)
    TextView tv_start_time;
    @Bind(R.id.view_start)
    View view_start;
    @Bind(R.id.tv_end_time)
    TextView tv_end_time;
    @Bind(R.id.view_end)
    View view_end;

    @Bind(R.id.lins_wheel_view)
    LinearLayout lins_wheel_view;
    @Bind(R.id.year)
    WheelView year;
    @Bind(R.id.month)
    WheelView month;
    @Bind(R.id.day)
    WheelView day;
    public boolean[] type = new boolean[]{true, true, true};//显示类型，默认显示： 年月日
    public int textGravity = Gravity.CENTER;
    public int textSizeContent = 25;//内容文字大小
    public int startYear;//开始年份
    public int endYear;//结尾年份
    public Calendar startDate;//开始时间
    public Calendar endDate;//终止时间
    public Calendar date;//当前选中时间
    public String label_year, label_month, label_day;//单位
    public int x_offset_year, x_offset_month, x_offset_day;//单位
    public boolean cyclic = false;//是否循环
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public WheelView.DividerType dividerType = WheelView.DividerType.FILL;//分隔线类型
    public float lineSpacingMultiplier = 1.8f; // 条目间距倍数 默认1.6
    public int textColorOut = 0xFFa8a8a8; //分割线以外的文字颜色
    public int textColorCenter = 0xFF2a2a2a; //分割线之间的文字颜色
    public boolean isCenterLabel = false;//是否只显示中间的label,默认每个item都显示

    private WheelTime wheelTime;
    private boolean isStartTimeBtn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calendar);
        mContext = this;
        ButterKnife.bind(this);
        initTitleBar();
        initWheelTime(lins_wheel_view);
    }

    private void initTitleBar() {
        titlebar.setTitle("选择时间");
        titlebar.setLeftBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 数据返回
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        titlebar.setRightBtnVisiable(View.VISIBLE);
        titlebar.setRightBtnTitle("完成");
        titlebar.setRightBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.notEmpty(tv_start_time.getText().toString())) {
                    MyToast.show("开始时间不能为空");
                    return;
                }
                if (!Utils.notEmpty(tv_end_time.getText().toString())) {
                    MyToast.show("结束时间不能为空");
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date start = format.parse(tv_start_time.getText().toString());
                    Date end = format.parse(tv_end_time.getText().toString());
                    //获取当前时间
                    Date currentDate = new Date(System.currentTimeMillis());
                    if (start.getTime() > end.getTime()) {
                        MyToast.show("结束时间不能小于开始时间");
                        return;
                    }
                    if (start.getTime() > currentDate.getTime()) {
                        MyToast.show("开始时间不能大于当前日期");
                        return;
                    }
                    if (end.getTime() > currentDate.getTime()) {
                        MyToast.show("结束时间不能大于当前日期");
                        return;
                    }
                    if (((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)) > 90) {
                        MyToast.show("抱歉您输入的时间范围大于3个月");
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 数据返回
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
//                bundle.putString(KeyConstans.START_TIME, tv_start_time.getText().toString());
//                bundle.putString(KeyConstans.END_TIME, tv_end_time.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    private void initWheelTime(LinearLayout timePickerView) {
        wheelTime = new WheelTime(timePickerView, type, textGravity, textSizeContent);
        wheelTime.setSelectChangeCallback(new ISelectTimeCallback() {
            @Override
            public void onTimeSelectChanged() {
                if (isStartTimeBtn) {
                    getWheelTime(tv_start_time);
                } else {
                    getWheelTime(tv_end_time);
                }
            }
        });
        if (startYear != 0 && endYear != 0
                && startYear <= endYear) {
            setRange();
        }
        setRangDate();

        if (Utils.notEmpty(tv_start_time.getText().toString())) {
            setTime(tv_start_time.getText().toString());
        }

        wheelTime.setLabels(label_year, label_month, label_day);
        wheelTime.setTextXOffset(x_offset_year, x_offset_month, x_offset_day);

        wheelTime.setCyclic(cyclic);
        wheelTime.setDividerColor(dividerColor);
        wheelTime.setDividerType(dividerType);
        wheelTime.setLineSpacingMultiplier(lineSpacingMultiplier);
        wheelTime.setTextColorOut(textColorOut);
        wheelTime.setTextColorCenter(textColorCenter);
        wheelTime.isCenterLabel(isCenterLabel);

        setStartTextColor();
    }


    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRange() {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRangDate() {
        wheelTime.setRangDate(startDate, endDate);
        initDefaultSelectedDate();
    }


    private void initDefaultSelectedDate() {
        //如果手动设置了时间范围
        if (startDate != null && endDate != null) {
            //若默认时间未设置，或者设置的默认时间越界了，则设置默认选中时间为开始时间。
            if (date == null || date.getTimeInMillis() < startDate.getTimeInMillis()
                    || date.getTimeInMillis() > endDate.getTimeInMillis()) {
                date = startDate;
            }
        } else if (startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            date = startDate;
        } else if (endDate != null) {
            date = endDate;
        }
    }


    /**
     * 设置选中时间,默认选中当前时间
     */
    private void setTime(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = format.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            date = calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int year, month, day;

        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            year = date.get(Calendar.YEAR);
            month = date.get(Calendar.MONTH);
            day = date.get(Calendar.DAY_OF_MONTH);
        }
        wheelTime.setPicker(year, month, day);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @OnClick(R.id.tv_start_time)
    public void startBtn() {
        isStartTimeBtn = true;
        setStartTextColor();
        if (Utils.notEmpty(tv_start_time.getText().toString())) {
            setTime(tv_start_time.getText().toString());
        }
    }

    @OnClick(R.id.tv_end_time)
    public void endBtn() {
        isStartTimeBtn = false;
        setEndTextColor();
        if (Utils.notEmpty(tv_end_time.getText().toString())) {
            setTime(tv_end_time.getText().toString());
        }
    }

    public void getWheelTime(TextView textView) {
        try {
            Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
            textView.setText(getTime(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setStartTextColor() {
        tv_start_time.setTextColor(getResources().getColor(R.color.d61518));
        view_start.setBackground(getResources().getDrawable(R.color.d61518));
        tv_end_time.setTextColor(getResources().getColor(R.color.scale));
        view_end.setBackground(getResources().getDrawable(R.color.scale));
    }

    public void setEndTextColor() {
        tv_end_time.setTextColor(getResources().getColor(R.color.d61518));
        view_end.setBackground(getResources().getDrawable(R.color.d61518));
        tv_start_time.setTextColor(getResources().getColor(R.color.scale));
        view_start.setBackground(getResources().getDrawable(R.color.scale));
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
