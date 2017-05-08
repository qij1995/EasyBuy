package com.my.easybuy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestEmailVerifyCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.bigkoo.pickerview.OptionsPickerView;
import com.my.easybuy.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends Activity implements View.OnClickListener{
    private EditText et_write_name;
    private EditText et_write_email;
    private EditText et_write_pwd;
    private EditText et_write_repwd;
    private TextView tv_register;
    private TextView tv_sex;
    private OptionsPickerView sexPvOptions1;
    private RelativeLayout rl_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initSex();
    }


    private void initView() {
        ImageView iv_back = (ImageView)findViewById(R.id.iv_back);
        et_write_name = (EditText) findViewById(R.id.et_write_name);
        et_write_email = (EditText) findViewById(R.id.et_write_email);
        et_write_pwd = (EditText) findViewById(R.id.et_write_pwd);
        et_write_repwd =(EditText) findViewById(R.id.et_write_repwd);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        rl_sex =(RelativeLayout)findViewById(R.id.rl_sex);

        tv_register.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }

    private void initSex() {
        final ArrayList<String> sexList = new ArrayList<String>();
        sexList.add("男");
        sexList.add("女");
        sexPvOptions1 = myOptionsPickerView(sexList);
        sexPvOptions1.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(final int options1, int option2, int options3) {
                            tv_sex.setText(sexList.get(options1));
            }
        });
    }

    public OptionsPickerView myOptionsPickerView(ArrayList<String> list) {
        OptionsPickerView pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(list);
        pvOptions.setCyclic(false);
        pvOptions.setSelectOptions(1);
        return pvOptions;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                String name = et_write_name.getText().toString().trim();
                final String email = et_write_email.getText().toString().trim();
                String pwd = et_write_pwd.getText().toString().trim();
                String repwd =et_write_repwd.getText().toString().trim();
                String sex =tv_sex.getText().toString().trim();
                if (email !=null && isEmail(email)){

                if(repwd.equals(pwd)) {

                    if (name != null && pwd != null  && name.length() > 0 && pwd.length() > 0 &&sex != null) {
                        AVUser user = new AVUser();
                        user.setUsername(name);
                        user.setEmail(email);
                        user.setPassword(pwd);
                        user.put("type","买家");
                        user.put("sex",sex);
                        // 其他属性可以像其他AVObject对象一样使用put方法添加
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(AVException e) {
                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    AVUser.getCurrentUser().requestEmailVerfiyInBackground(email, new RequestEmailVerifyCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                Intent intent = new Intent();
                                                intent.setClass(RegisterActivity.this,EmailVerfiyActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                e.printStackTrace();
                                                Toast.makeText(RegisterActivity.this, "邮箱发送失败，请检查网络或者确认邮箱地址的正确", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    Log.e("UUY",e.toString());
                                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "请输入完整正确的信息", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"密码不一致", Toast.LENGTH_SHORT).show();
                }
                }else{
                    Toast.makeText(RegisterActivity.this,"邮箱地址为空或者格式不对", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_sex:
                sexPvOptions1.show();
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
