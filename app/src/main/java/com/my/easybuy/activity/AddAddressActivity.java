package com.my.easybuy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.my.easybuy.R;

/**
 * Created by user999 on 2017/2/28.
 */

public class AddAddressActivity extends Activity implements View.OnClickListener{
    private TextView tv_back;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_address;
    private TextView tv_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        initView();
        initEvent();
    }

    private void initEvent() {
        tv_ok.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        et_name= (EditText) findViewById(R.id.et_name);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_address= (EditText) findViewById(R.id.et_address);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_ok:
                String address=et_address.getText().toString().trim();
                String name=et_name.getText().toString().trim();
                String phone=et_phone.getText().toString().trim();
                if (address!=null && address.length()>0 && name!=null && name.length()>0 && phone!=null && phone.length()>0){
                    AVObject object=new AVObject("AddressEntity");
                    object.put("name",name);
                    object.put("user", AVUser.getCurrentUser());
                    object.put("phone",phone);
                    object.put("address",address);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                Toast.makeText(AddAddressActivity.this,"提交成功！",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(AddAddressActivity.this,"提交失败！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"请将信息填写完整！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
