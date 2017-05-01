package com.my.easybuy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.my.easybuy.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetActivity extends Activity implements View.OnClickListener{
    EditText et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        et_email = (EditText) findViewById(R.id.et_email);
        TextView tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                String email = et_email.getText().toString().trim();
                if (email != null && isEmail(email)) {
                        AVUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Intent intent = new Intent();
                                    intent.setClass(ForgetActivity.this,ForgetNextActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    e.printStackTrace();
                                    Toast.makeText(ForgetActivity.this, "邮箱发送失败，请检查网络或者确认邮箱地址的正确", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                } else {
                    Toast.makeText(ForgetActivity.this, "邮箱地址为空或者格式不对", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
