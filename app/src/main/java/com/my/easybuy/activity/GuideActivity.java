package com.my.easybuy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.avos.avoscloud.AVUser;
import com.my.easybuy.R;


public class GuideActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
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
                    if(AVUser.getCurrentUser()==null){
                        Intent intent = new Intent();
                        intent.setClass(GuideActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent();
                        intent.setClass(GuideActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

//                    Intent intent = new Intent();
//                    intent.setClass(GuideActivity.this, LoginActivity.class);
//                    startActivity(intent);

                    finish();
                    break;
            }
        }
    };
}
