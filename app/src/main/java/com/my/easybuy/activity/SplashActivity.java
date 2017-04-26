package com.my.easybuy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.avos.avoscloud.AVUser;
import com.my.easybuy.R;


public class SplashActivity extends Activity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
//        StatusBarUtil.setTranslucent(this, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(110,2500);
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 110:
                    if(AVUser.getCurrentUser()==null && preferences.getBoolean("firststart", true)){
                        editor = preferences.edit();
                        //将登录标志位设置为false，下次登录时不在显示首次登录界面
                        editor.putBoolean("firststart", false);
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(SplashActivity.this, GuideActivity.class);
                        startActivity(intent);
                    }else if (AVUser.getCurrentUser()==null){
                        Intent intent = new Intent();
                        intent.setClass(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent();
                        intent.setClass(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }


//                    Intent intent = new Intent();
//                    intent.setClass(SplashActivity.this, LoginActivity.class);
//                    startActivity(intent);

                    finish();
                    break;
            }
        }
    };
}
