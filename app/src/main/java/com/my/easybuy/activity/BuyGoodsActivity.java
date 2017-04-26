package com.my.easybuy.activity;

import android.app.Activity;
import android.content.DialogInterface;
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

public class BuyGoodsActivity extends Activity implements View.OnClickListener{
    private String url;
    private String des;
    private String price;
    private String number;
    private String address;
    private String name;
    private String phone;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_address;
    private EditText et_password;
    private TextView tv_ok;
    private TextView tv_back;
    private TextView tv_number;
    private String saleName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_goods);

        url=getIntent().getStringExtra("url");
        des=getIntent().getStringExtra("des");
        price=getIntent().getStringExtra("price");
        number=getIntent().getStringExtra("number");
        address=getIntent().getStringExtra("address");
        name=getIntent().getStringExtra("name");
        phone=getIntent().getStringExtra("phone");
        saleName=getIntent().getStringExtra("saleName");

        initView();
        initEvent();
    }

    private void initEvent() {
        tv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        et_password= (EditText) findViewById(R.id.et_password);
        tv_number= (TextView) findViewById(R.id.tv_number);

        tv_name.setText(name);
        tv_phone.setText(phone);
        tv_address.setText(address);
        tv_number.setText(number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_ok:
                String password=et_password.getText().toString().trim();
                if (password==null || password.length()!=6){
                    Toast.makeText(this,"请先填写完整6位密码...",Toast.LENGTH_SHORT).show();
                }else {
                    android.support.v7.app.AlertDialog.Builder dialog=new android.support.v7.app.AlertDialog.Builder(this);
                    dialog.setTitle("确定购买?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AVObject object=new AVObject("BuyGoodsEntity");
                            object.put("user", AVUser.getCurrentUser());
                            object.put("url",url);
                            object.put("des",des);
                            object.put("price",price);
                            object.put("number",number);
                            object.put("name",name);
                            object.put("phone",phone);
                            object.put("address",address);
                            object.put("isFuKuan",true);
                            object.put("saleName",saleName);
                            object.put("state","立即发货");
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e==null){
                                        Toast.makeText(BuyGoodsActivity.this,"购买成功！",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(BuyGoodsActivity.this,"购买失败！",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AVObject object=new AVObject("BuyGoodsEntity");
                            object.put("user", AVUser.getCurrentUser());
                            object.put("url",url);
                            object.put("des",des);
                            object.put("price",price);
                            object.put("number",number);
                            object.put("name",name);
                            object.put("phone",phone);
                            object.put("address",address);
                            object.put("saleName",saleName);
                            object.put("isFuKuan",false);
                            object.put("state","待付款");
                            object.saveInBackground();

                            dialog.dismiss();
                        }
                    }).setCancelable(true).show();

                }


                break;
        }
    }
}
