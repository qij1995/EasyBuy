package com.my.easybuy.activity.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.my.easybuy.R;
import com.my.easybuy.common.base.BasePage;

/**
 * Created by qijiajun on 2017/4/26.
 */

public class GuidePageOne extends BasePage {

    public GuidePageOne(Context ct) {
        super(ct);
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.guide_page_one, null);
        return view;
    }

    @Override
    public void initData() {

    }
}

