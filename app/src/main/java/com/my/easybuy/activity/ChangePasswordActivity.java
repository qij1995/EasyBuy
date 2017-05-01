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
import com.avos.avoscloud.UpdatePasswordCallback;
import com.my.easybuy.R;

public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_senew_password;
    private TextView tv_changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView() {
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_senew_password = (EditText) findViewById(R.id.et_senew_password);
        tv_changepassword = (TextView) findViewById(R.id.tv_changepassword);

        tv_changepassword.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_changepassword:
                String old_pwd = et_old_password.getText().toString().trim();
                String new_pwd = et_new_password.getText().toString().trim();
                String repwd = et_senew_password.getText().toString().trim();
                if (repwd.equals(new_pwd)) {

                    if (old_pwd != null && new_pwd != null && old_pwd.length() > 0 && new_pwd.length() > 0) {
                            AVUser user = AVUser.getCurrentUser();
                            if (user != null) {
                                user.updatePasswordInBackground(old_pwd, new_pwd, new UpdatePasswordCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        //逻辑处理
                                        if (e == null) {
                                            Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Log.e("UUY", e.toString());
                                            Toast.makeText(ChangePasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "请输入正确旧密码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "新密码不一致", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
