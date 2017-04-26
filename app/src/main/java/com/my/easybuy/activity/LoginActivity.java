package com.my.easybuy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.my.easybuy.R;


public class LoginActivity extends Activity implements View.OnClickListener {

    private LoginActivity context;
    private EditText et_name;
    private EditText et_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this ;
        initView();
    }

    private void initView() {
        TextView btn_login = (TextView) findViewById(R.id.tv_login);
        btn_login.setOnClickListener(this);
        TextView tv_zhuce = (TextView)findViewById(R.id.tv_register);
        tv_zhuce.setOnClickListener(this);
        et_name = (EditText)findViewById(R.id.et_name);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                Login();
                break;
            case R.id.tv_register:
                Register();
                break;
        }
    }

    private void Login(){
        String username = et_name.getText().toString();
        String password = et_pwd.getText().toString();
        if (TextUtils.isEmpty(username)){
            Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if(username!=null&&username.length()>0&&password!=null&&password.length()>0) {
            AVUser.logInInBackground(username, password, new LogInCallback() {
                public void done(AVUser user, AVException e) {
                    if (e == null) {
//                        // 登录成功
                        String type=user.getString("type");
                        if (type.equals("买家")){
                            GoHome();
                        }else {
                            Toast.makeText(LoginActivity.this,"这是买家平台...",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 登录失败
                        Toast.makeText(context,"登录失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(context,"请填写登录信息", Toast.LENGTH_SHORT).show();
        }
    }

    private void Register() {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        startActivity(intent);
    }

    private void GoHome() {
        Intent intent = new Intent();
        intent.setClass(context,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
