package com.mygit.mywheelview;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义标题
 *
 * @author admin
 */
public class TitleBarView extends LinearLayout {

    private Context mContext;
    private LayoutInflater mInflater;
    private LinearLayout mLayout;

    @Bind(R.id.btn_left)
    Button mBtnLeft;
    @Bind(R.id.btn_right)
    Button mBtnRight;
    @Bind(R.id.title)
    TextView mTitle;

    public TitleBarView(Context context) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    /**
     * 初始化试图
     */
    private void initView() {
        mLayout = (LinearLayout) mInflater.inflate(R.layout.view_titlebar, this);
        ButterKnife.bind(this, mLayout);

    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTitle(int resId) {
        mTitle.setText(resId);
    }

    /**
     * 设置左右按钮监听
     *
     * @param listner
     */
    public void setLeftBtnListener(OnClickListener listner) {
        mBtnLeft.setOnClickListener(listner);
    }

    public void setRightBtnListener(OnClickListener listner) {
        mBtnRight.setOnClickListener(listner);
    }

    public void setLeftBtnVisiable(int visiable) {
        mBtnLeft.setVisibility(visiable);
    }

    public void setRightBtnVisiable(int visiable) {
        mBtnRight.setVisibility(visiable);
    }

    public void setLeftBtnTitle(String text) {
        mBtnLeft.setText(text);
    }

    public void setRightBtnTitle(String text) {
        mBtnRight.setText(text);
    }

    public void setLeftBtnBackground(int resId) {
        mBtnLeft.setCompoundDrawables(null, null, null, null);
        mBtnLeft.setBackgroundResource(resId);
    }

    public void setRightBtnBackground(int resId) {
        mBtnRight.setCompoundDrawables(null, null, null, null);
        mBtnRight.setBackgroundResource(resId);
    }

    /**
     * 设置左右图片
     *
     * @param res
     */
    public void setLeftBtnRes(int res) {
        Drawable drawable = mContext.getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        mBtnLeft.setCompoundDrawables(drawable, null, null, null);
    }

    public void setRight(String str) {
        mBtnRight.setCompoundDrawables(null, null, null, null);
        mBtnRight.setText(str);
    }

    public void setRightBtnRes(int res) {
        Drawable drawable = mContext.getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        mBtnRight.setCompoundDrawables(null, null, drawable, null);
    }

}
