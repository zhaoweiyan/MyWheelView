package com.mygit.mywheelview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mygit.mywheelview.R;
import com.mygit.mywheelview.utils.LogUtil;
import com.mygit.mywheelview.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by admin on 2015/11/16.
 */
public class CitySelectView extends LinearLayout implements OnWheelChangedListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private LinearLayout mLayout;
    @Bind(R.id.id_province)
    WheelView mProvince;
    @Bind(R.id.id_city)
    WheelView mCity;
    @Bind(R.id.id_area)
    WheelView mArea;
    @Bind(R.id.btn_ok)
    Button mOkBtn;



    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private JSONObject mJsonObj;
    private String[] mProvinceDatas;
    private Map<String, String[]> mCitisDatasMap = new HashMap<>();
    private Map<String, String[]> mAreaDatasMap = new HashMap<>();

    private Map<String, String> mProvinceCode = new HashMap<>();
    private Map<String, String> mCityCode = new HashMap<>();
    private Map<String, String> mAreaCode = new HashMap<>();

    public String mCurrentProviceName;
    public String mCurrentProviceCode;
    public String mCurrentCityName;
    public String mCurrentCityCode;
    public String mCurrentAreaName = "";
    public String mCurrentAreaCode = "";
    public Boolean hasArea = true;

    public CitySelectView(Context context, boolean hasArea) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.hasArea = hasArea;
        initView();
    }

    public CitySelectView(Context context) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public CitySelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public CitySelectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        mLayout = (LinearLayout) mInflater.inflate(R.layout.view_city_select, this);
        ButterKnife.bind(this, mLayout);
        initJsonData();
        initDatas();

        mProvince.setViewAdapter(new ArrayWheelAdapter<String>(mContext, mProvinceDatas));
        // 添加change事件
        mProvince.addChangingListener(this);
        // 添加change事件
        mCity.addChangingListener(this);
        // 添加change事件
        mArea.addChangingListener(this);

        mProvince.setVisibleItems(5);
        mCity.setVisibleItems(5);
        mArea.setVisibleItems(5);
        updateCities();
        updateAreas();

        if (hasArea) {
            mArea.setVisibility(View.VISIBLE);
        } else {
            mArea.setVisibility(View.GONE);
        }

    }


    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mCity.getCurrentItem();
        String[] areas = null;
        if (mCitisDatasMap.size() > 0 && pCurrent >= 0 && mAreaDatasMap.size() > 0 && mCityCode.size() > 0) {
            if (mCitisDatasMap.get(mCurrentProviceName) != null && mCitisDatasMap.get(mCurrentProviceName).length > 0) {
                mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
                LogUtil.e("mCurrentCityName=" + mCurrentCityName);
                areas = mAreaDatasMap.get(mCurrentCityName);
                mCurrentCityCode = mCityCode.get(mCurrentCityName);
            } else {
                mCurrentCityName = "";
                mCurrentCityCode = "";
            }
        } else {
            mCurrentCityName = "";
            mCurrentCityCode = "";
        }

        if (areas == null) {
            areas = new String[]{""};
        }
        mArea.setViewAdapter(new ArrayWheelAdapter<>(mContext, areas));
        if (areas.length > 0) {
            mArea.setCurrentItem(0);
            mCurrentAreaName = areas[0];
            mCurrentAreaCode = mAreaCode.get(mCurrentAreaName);
        } else {
            mCurrentAreaName = "";
            mCurrentAreaCode = "";
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息并得到code
     */
    private void updateCities() {
        int pCurrent = mProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        mCurrentProviceCode = mProvinceCode.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mCity.setViewAdapter(new ArrayWheelAdapter<String>(mContext, cities));
        mCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas() {
//        if (UserPersist.getPreCityArea() != null) {
//            String preCityArea = UserPersist.getGoodsCityBean();
//            try {
//                mJsonObj = new JSONObject(preCityArea);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("data");
            mProvinceDatas = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String provinceName = jsonP.getString("name");// 省名字
                String provinceCode = jsonP.getString("code");// 省code
                mProvinceDatas[i] = provinceName;
                mProvinceCode.put(provinceName, provinceCode);
                JSONArray jsonCs = null;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("city");
                } catch (Exception e1) {
                    continue;
                }
                String[] mCitiesDatas = new String[jsonCs.length()];
                LogUtil.e("mCitiesDatas =" + jsonCs.length());
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String cityName = jsonCity.getString("name");// 市名字
                    String cityCode = jsonCity.getString("code");// 市code
                    mCityCode.put(cityName, cityCode);
                    mCitiesDatas[j] = cityName;
                    JSONArray jsonAreas = null;
                    try {
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
                        jsonAreas = jsonCity.getJSONArray("area");
                    } catch (Exception e) {
                        continue;
                    }
                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        JSONObject jsonArea = jsonAreas.getJSONObject(k);
                        String areaName = jsonArea.getString("name");// 区名字
                        String areaCode = jsonArea.getString("code");// 区code
                        mAreaCode.put(areaName, areaCode);
                        mAreasDatas[k] = areaName;
                    }
                    if (cityName.equals("台湾")) {
                        for (int m = 0; m < mAreasDatas.length; m++) {
                            LogUtil.e("哪个区的？？？？" + mAreasDatas[m]);
                        }
                    }

                    mAreaDatasMap.put(cityName, mAreasDatas);
                }

                mCitisDatasMap.put(provinceName, mCitiesDatas);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = mContext.getAssets().open("citylistMy.txt");
            int len = -1;
            int size = is.available();
            byte[] buf = new byte[size];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "UTF-8"));
                LogUtil.e("城市名称==" + sb + "");
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mProvince) {
            updateCities();
        } else if (wheel == mCity) {
            updateAreas();
        } else if (wheel == mArea) {
            mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
        }
    }

    @OnClick(R.id.btn_ok)
    public void showChoose(View view) {
        MyToast.show("onClick" + mCurrentProviceName);
    }


}
