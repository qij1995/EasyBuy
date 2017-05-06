package com.my.easybuy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.my.easybuy.R;
import com.my.easybuy.activity.AllOrderActivity;
import com.my.easybuy.activity.ChangePasswordActivity;
import com.my.easybuy.activity.DaiFaHuoActivity;
import com.my.easybuy.activity.DaiFuKuanActivity;
import com.my.easybuy.activity.DaiPingJiaActivity;
import com.my.easybuy.activity.DaiShouHuoActivity;
import com.my.easybuy.activity.LoginActivity;
import com.my.easybuy.activity.PersonActivity;


public class SettingFragment extends Fragment implements View.OnClickListener{
    private View view;
    private LinearLayout ll_daifukuan;
    private LinearLayout ll_daifahuo;
    private LinearLayout ll_daishouhuo;
    private LinearLayout ll_daipingjia;
    private RelativeLayout rl_person;
    private RelativeLayout rl_about;
    private RelativeLayout rl_account;
    private TextView tv_login_out;
    private TextView tv_all_order;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_setting,container,false);

        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        ll_daifukuan.setOnClickListener(this);
        ll_daifahuo.setOnClickListener(this);
        ll_daipingjia.setOnClickListener(this);
        ll_daishouhuo.setOnClickListener(this);
        rl_person.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);
        tv_all_order.setOnClickListener(this);
    }

    private void initView() {
        ll_daifukuan= (LinearLayout) view.findViewById(R.id.ll_daifukuan);
        ll_daifahuo= (LinearLayout) view.findViewById(R.id.ll_daifahuo);
        ll_daishouhuo= (LinearLayout) view.findViewById(R.id.ll_daishouhuo);
        ll_daipingjia= (LinearLayout) view.findViewById(R.id.ll_daipingjia);
        rl_person= (RelativeLayout) view.findViewById(R.id.rl_person);
        rl_about= (RelativeLayout) view.findViewById(R.id.rl_about);
        rl_account= (RelativeLayout) view.findViewById(R.id.rl_account);
        tv_login_out= (TextView) view.findViewById(R.id.tv_login_out);
        tv_all_order= (TextView) view.findViewById(R.id.tv_all_order);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tv_all_order:
                intent=new Intent();
                intent.setClass(getActivity(), AllOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_daifukuan:
                intent=new Intent();
                intent.setClass(getActivity(), DaiFuKuanActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_daifahuo:
                intent=new Intent();
                intent.setClass(getActivity(), DaiFaHuoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_daishouhuo:
                intent=new Intent();
                intent.setClass(getActivity(), DaiShouHuoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_daipingjia:
                intent=new Intent();
                intent.setClass(getActivity(), DaiPingJiaActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_person:
                intent = new Intent();
                intent.setClass(getActivity(),PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_account:
                intent = new Intent();
                intent.setClass(getActivity(),ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about:
                break;
            case R.id.tv_login_out:
                AVUser.logOut();             //清除缓存用户对象
                AVUser currentUser = AVUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
