package com.my.easybuy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.my.easybuy.R;


public class RegisterActivity extends Activity implements View.OnClickListener{
    private EditText et_write_name;
    private EditText et_write_pwd;
    private EditText et_write_repwd;
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }


    private void initView() {
        ImageView iv_back = (ImageView)findViewById(R.id.iv_back);
        et_write_name = (EditText) findViewById(R.id.et_write_name);
        et_write_pwd = (EditText) findViewById(R.id.et_write_pwd);
        et_write_repwd=(EditText) findViewById(R.id.et_write_repwd);
        tv_register= (TextView) findViewById(R.id.tv_register);

        tv_register.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                String name = et_write_name.getText().toString().trim();
                String pwd = et_write_pwd.getText().toString().trim();
                String repwd =et_write_repwd.getText().toString().trim();
                if(repwd.equals(pwd)) {

                    if (name != null && pwd != null  && name.length() > 0 && pwd.length() > 0 ) {
                        AVUser user = new AVUser();
                        user.setUsername(name);
                        user.setPassword(pwd);
                        user.put("type","买家");
                        // 其他属性可以像其他AVObject对象一样使用put方法添加
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(AVException e) {
                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Log.e("UUY",e.toString());
                                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "请输入正确信息", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"密码不一致", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
