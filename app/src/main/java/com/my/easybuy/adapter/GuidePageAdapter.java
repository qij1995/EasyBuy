package com.my.easybuy.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.my.easybuy.common.base.BasePage;

import java.util.List;

/**
 * Created by qijiajun on 2017/4/26.
 */

public class GuidePageAdapter extends PagerAdapter {
    private List<BasePage> list;

    public GuidePageAdapter(List<BasePage> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        ((ViewPager) container)
                .removeView(list.get(position).getRootView());

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container)
                .addView(list.get(position).getRootView(), 0);
        return list.get(position).getRootView();
    }

}