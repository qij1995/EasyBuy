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
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;
import com.my.easybuy.R;

/**
 * Created by user999 on 2017/2/28.
 */

public class PayActivity extends Activity implements View.OnClickListener {
    private String url;
    private String des;
    private String price;
    private String number;
    private String address;
    private String name;
    private String objId;
    //    private String objectId;
    private String pay_objectId;
    private String phone;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_price;
    private EditText et_password;
    private TextView tv_ok;
    private TextView tv_back;
    private TextView tv_number;
    private String saleName;
    private int pre;
    private int num;
    private String total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_goods);

        url = getIntent().getStringExtra("url");
        des = getIntent().getStringExtra("des");
        price = getIntent().getStringExtra("price");
        number = getIntent().getStringExtra("number");
        address = getIntent().getStringExtra("address");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        objId = getIntent().getStringExtra("objId");
//        objectId=getIntent().getStringExtra("objectId");
        pay_objectId = getIntent().getStringExtra("pay_objectId");
        saleName = getIntent().getStringExtra("saleName");
        pre = Integer.parseInt(price);
        num = Integer.parseInt(number);
        total = String.valueOf(pre*num);
        initView();
        initEvent();
    }

    private void initEvent() {
        tv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_name.setText(name);
        tv_phone.setText(phone);
        tv_address.setText(address);
        tv_number.setText(number+" 件");
        tv_price.setText(total+" 元");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_ok:
                String password = et_password.getText().toString().trim();
                if (password == null || password.length() != 6) {
                    Toast.makeText(this, "请先填写完整6位密码...", Toast.LENGTH_SHORT).show();
                } else {
                    android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
                    dialog.setTitle("确定购买?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    AVQuery query = new AVQuery<>("BuyGoodsEntity");
                                    try {
                                        AVObject object = query.get(pay_objectId);
                                        object.put("state", "待发货");
                                        object.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                if (e == null) {
                                                    Toast.makeText(PayActivity.this, "支付成功！", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(PayActivity.this, "支付失败,请重新支付！", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } catch (AVException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(PayActivity.this, "请尽快付款！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).setCancelable(true).show();

                }


                break;
        }
    }
}
