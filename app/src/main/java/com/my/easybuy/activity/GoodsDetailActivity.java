package com.my.easybuy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.my.easybuy.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by user999 on 2017/2/28.
 */

public class GoodsDetailActivity extends Activity implements View.OnClickListener{
    private TextView tv_back;
    private ImageView iv_goods;
    private TextView tv_des;
    private TextView tv_price;
    private TextView tv_minus;
    private TextView tv_number;
    private TextView tv_add;
    private TextView tv_buy;
    private TextView tv_shop_cart;
    private String url;
    private String des;
    private String price;
    private TextView tv_address;
    private String name;
    private String phone;
    private String objId;
    private String objectId;
    private TextView tv_pinglun;
    private String saleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        url=getIntent().getStringExtra("url");
        des=getIntent().getStringExtra("des");
        price=getIntent().getStringExtra("price");
        objId=getIntent().getStringExtra("objId");
        objectId=getIntent().getStringExtra("objectId");
        saleName=getIntent().getStringExtra("saleName");
        initView();
        initEvent();
    }

    private void initEvent() {
        tv_back.setOnClickListener(this);
        tv_minus.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_buy.setOnClickListener(this);
        tv_shop_cart.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        tv_pinglun.setOnClickListener(this);
    }

    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        iv_goods= (ImageView) findViewById(R.id.iv_goods);
        tv_des= (TextView) findViewById(R.id.tv_des);
        tv_price= (TextView) findViewById(R.id.tv_price);
        tv_minus= (TextView) findViewById(R.id.tv_minus);
        tv_number= (TextView) findViewById(R.id.tv_number);
        tv_add= (TextView) findViewById(R.id.tv_add);
        tv_buy= (TextView) findViewById(R.id.tv_buy);
        tv_shop_cart= (TextView) findViewById(R.id.tv_shop_cart);
        tv_address= (TextView) findViewById(R.id.tv_address);
        tv_pinglun= (TextView) findViewById(R.id.tv_pinglun);

        tv_des.setText(des);
        tv_price.setText("¥"+price);
        if (url!=null){
            ImageLoader.getInstance().displayImage(url,iv_goods,options);
        }
    }


    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.color.bg_no_photo)
            .showImageForEmptyUri(R.color.bg_no_photo)
            .showImageOnFail(R.color.bg_no_photo)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
            .build();

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_minus:
                String number=tv_number.getText().toString().trim();
                int num=Integer.parseInt(number);
                if (num>1){
                    num=num-1;
                    tv_number.setText(num+"");
                }
                break;
            case R.id.tv_add:
                String number1=tv_number.getText().toString().trim();
                int num1=Integer.parseInt(number1);
                num1=num1+1;
                tv_number.setText(num1+"");
                break;
            case R.id.tv_address:
                intent=new Intent();
                intent.setClass(this,AddressListActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.tv_buy:
                String address=tv_address.getText().toString().trim();
                String number2=tv_number.getText().toString().trim();
                if (!address.equals("请选择收货地址")){
                    intent=new Intent();
                    intent.setClass(GoodsDetailActivity.this,BuyGoodsActivity.class);
                    intent.putExtra("url",url);
                    intent.putExtra("des",des);
                    intent.putExtra("price",price);
                    intent.putExtra("number",number2);
                    intent.putExtra("address",address);
                    intent.putExtra("objId",objId);
                    intent.putExtra("objectId",objectId);
                    intent.putExtra("name",name);
                    intent.putExtra("phone",phone);
                    intent.putExtra("saleName",name);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"请先选择收货地址",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_shop_cart:
                String number3=tv_number.getText().toString().trim();
                AVObject object=new AVObject("ShopCartEntity");
                object.put("url",url);
                object.put("des",des);
                object.put("price",price);
                object.put("number",number3);
                object.put("objId",objId);
                object.put("saleName",name);
//                object.put("objectId",objectId);
                object.put("car_state","未处理");
                object.put("user",AVUser.getCurrentUser());
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e==null){
                            Toast.makeText(GoodsDetailActivity.this,"加入购物车成功！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.tv_pinglun:
                intent=new Intent();
                intent.setClass(this,DetailPingLunActivity.class);
                intent.putExtra("objId",objId);
                intent.putExtra("saleName",name);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && data!=null){
            String address=data.getStringExtra("address");
            name=data.getStringExtra("name");
            phone=data.getStringExtra("phone");
            tv_address.setText(address);
        }
    }
}
