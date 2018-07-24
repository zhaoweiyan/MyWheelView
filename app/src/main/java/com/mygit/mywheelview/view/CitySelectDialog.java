package com.mygit.mywheelview.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by admin on 2015/11/16.
 */
public class CitySelectDialog extends Dialog {

    private Context mContext;
    private CitySelectView mCityView;
    public String provinceName;
    public String cityName;
    public String areaName;
    public String provinceCode;
    public String cityCode;
    public String areaCode;
    private Boolean hasArea;

    public CitySelectDialog(Context context) {
        super(context);
        mContext = context;
    }

    public CitySelectDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public CitySelectDialog(Context context, int theme, boolean hasArea) {
        super(context, theme);
        mContext = context;
        this.hasArea = hasArea;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityView = new CitySelectView(mContext);
        setContentView(mCityView);
        mCityView.mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provinceName = mCityView.mCurrentProviceName;
                cityName = mCityView.mCurrentCityName;
                areaName = mCityView.mCurrentAreaName;
                provinceCode = mCityView.mCurrentProviceCode;
                cityCode = mCityView.mCurrentCityCode;
                areaCode = mCityView.mCurrentAreaCode;
                cancel();
            }
        });
    }



}
