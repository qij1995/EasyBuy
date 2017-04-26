package com.my.easybuy.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVUser;
import com.my.easybuy.R;
import com.my.easybuy.activity.LoginActivity;
import com.my.easybuy.activity.MyBuyActivity;
import com.my.easybuy.activity.PersonActivity;


import java.io.File;



public class MeFragment extends Fragment implements View.OnClickListener{

    private View view;
    private RelativeLayout rl_me;
    private RelativeLayout rl_my_buy;
    private RelativeLayout rl_share;
    private RelativeLayout rl_login_out;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_me,container,false);

        context=getActivity();
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        rl_me.setOnClickListener(this);
        rl_my_buy.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        rl_login_out.setOnClickListener(this);

    }

    private void initView() {
        rl_me= (RelativeLayout) view.findViewById(R.id.rl_me);
        rl_my_buy= (RelativeLayout) view.findViewById(R.id.rl_my_buy);
        rl_share= (RelativeLayout) view.findViewById(R.id.rl_share);
        rl_login_out= (RelativeLayout) view.findViewById(R.id.rl_login_out);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.rl_me:
                intent = new Intent();
                intent.setClass(context,PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_my_buy:
                intent=new Intent();
                intent.setClass(context, MyBuyActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_share:
                doShare("https://www.pgyer.com/1204","1.jpg");
                break;
            case R.id.rl_login_out:
                AVUser.logOut();             //清除缓存用户对象
                AVUser currentUser = AVUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(context, LoginActivity.class));
                getActivity().finish();
                break;
        }
    }


    /**
     * 分享
     *
     * @param info
     *            分享的内容
     * @param picPath
     *            图片的地址
     */
    public void doShare(String info, String picPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        if (!TextUtils.isEmpty(picPath) && isFileExist(picPath)) {
            Uri uri = Uri.parse("file:///" + picPath);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra("sms_body", info);
        } else {
            intent.setType("text/plain");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_TEXT, info);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        startActivity(Intent.createChooser(intent, "选择分享的类型"));
    }
    /**
     * 判断指定文件时候存在
     *
     * @param picPath
     * @return
     */
    private boolean isFileExist(String picPath) {
        File file = new File(picPath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

}
