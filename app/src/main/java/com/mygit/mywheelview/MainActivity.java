package com.mygit.mywheelview;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.mygit.mywheelview.view.CitySelectDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.lins_calendar)
    TextView lins_calendar;
    @Bind(R.id.lins_address)
    TextView lins_address;
    private MainActivity mContext;
    private String provinceName;
    private String cityName;
    private String areaName;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContext=this;
    }

    @OnClick(R.id.lins_calendar)
    public void startCalendar(){
        //时间滚轮---自建类
        Intent intent = new Intent(mContext, CalenDarActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.lins_address)
    public void startAddress(){
        final CitySelectDialog dialog = new CitySelectDialog(
                this, R.style.AppTheme_MyDialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dlg) {
                String addr = "";
                if (dialog.provinceName!=null) {
                    addr = dialog.provinceName;
                    provinceName = dialog.provinceName;
                    provinceCode = dialog.provinceCode;
                }
                if (dialog.cityName!=null) {
                    addr = addr + dialog.cityName;
                    cityName = dialog.cityName;
                    cityCode = dialog.cityCode;
                }
                if (dialog.areaName!=null) {
                    addr = addr + dialog.areaName;
                    areaName = dialog.areaName;
                    areaCode = dialog.areaCode;
                }
                lins_address.setText(addr);
            }
        });
    }
}
