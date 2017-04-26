package com.my.easybuy.activity.page;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.my.easybuy.R;
import com.my.easybuy.activity.GuideActivity;
import com.my.easybuy.activity.LoginActivity;
import com.my.easybuy.common.base.BasePage;
import com.my.easybuy.common.manager.AppManager;


/**
 * Created by qijiajun on 2017/4/26.
 */

public class GuidePageTwo extends BasePage {


    RelativeLayout rl_enter;
    ImageView iv_enter;

    public GuidePageTwo(Context ct) {
        super(ct);
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.guide_page_two, null);
        iv_enter=(ImageView) view.findViewById(R.id.iv_enter);
        iv_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ct, LoginActivity.class);
                ct.startActivity(intent);
                AppManager.getAppManager().finishActivity(GuideActivity.class);
            }
        });
        return view;
    }

    @Override
    public void initData() {

    }


}