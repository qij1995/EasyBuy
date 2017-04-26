package com.my.easybuy.common.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by qijiajun on 2017/4/26.
 */

public abstract class BasePage {
    private View view;
    public Context ct;
    public String proid;
    public int isGroup;
    public Fragment fragment;

    /**
     * 1 初始化界面 2 初始化数据
     */
    public BasePage(Context ct) {
        this.ct = ct;
        LayoutInflater inflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = initView(inflater);
    }



    public BasePage(Context ct, String proid, int isGroup) {
        this.ct = ct;
        this.proid = proid;
        this.isGroup = isGroup;
        LayoutInflater inflater = (LayoutInflater) ct
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = initView(inflater);
    }



    public View getRootView() {
        return view;
    }

    public abstract View initView(LayoutInflater inflater);

    public abstract void initData();

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }




}