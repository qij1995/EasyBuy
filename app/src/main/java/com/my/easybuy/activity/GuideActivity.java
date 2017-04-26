package com.my.easybuy.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.my.easybuy.R;
import com.my.easybuy.activity.page.GuidePageOne;
import com.my.easybuy.activity.page.GuidePageTwo;
import com.my.easybuy.adapter.GuidePageAdapter;
import com.my.easybuy.common.base.BaseActivity;
import com.my.easybuy.common.base.BasePage;
import com.my.easybuy.common.manager.AppManager;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity {


    private static final int CODE_FOR_READ_PHONE_STATE = 0;
    private ViewPager guideViewpager;
    private LinearLayout pointGroup;
    private ArrayList<BasePage> pages;
    private int lastPosition;
    private GuidePageOne guidePageOne;
    private GuidePageTwo guidePageTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_guide);
        AppManager.getAppManager().addActivity(this);
        guideViewpager = (ViewPager) findViewById(R.id.guide_viewpager);
        pointGroup = (LinearLayout) findViewById(R.id.point_group);
        pages = new ArrayList<>();
        guidePageOne = new GuidePageOne(this);
        pages.add(guidePageOne);
        guidePageTwo = new GuidePageTwo(this);
        pages.add(guidePageTwo);


        GuidePageAdapter guidePageAdapter = new GuidePageAdapter(pages);
        guideViewpager.setAdapter(guidePageAdapter);
        guideViewpager.setOnPageChangeListener(onPageChangeListener);

    }
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {


        /**
         * 页面切换后调用 position 新的页面位置
         */
        @Override
        public void onPageSelected(int position) {

            // 改变指示点的状态
            // 把当前点enbale 为true
//            pointGroup.getChildAt(position).setEnabled(true);
            // 把上一个点设为false
//            pointGroup.getChildAt(lastPosition).setEnabled(false);
//            lastPosition = position;

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}
