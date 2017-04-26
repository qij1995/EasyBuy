package com.my.easybuy.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.my.easybuy.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;



public class EditGoodsActivity extends Activity implements View.OnClickListener{
    private TextView tv_back;
    private ImageView iv_goods;
    private TextView tv_des;
    private TextView tv_price;
    private TextView tv_minus;
    private TextView tv_number;
    private TextView tv_add;
    private TextView tv_ok;
    private String url;
    private String des;
    private String price;
    private String number;
    private String myId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goods);

        url=getIntent().getStringExtra("url");
        des=getIntent().getStringExtra("des");
        price=getIntent().getStringExtra("price");
        number=getIntent().getStringExtra("number");
        myId=getIntent().getStringExtra("myId");

        initView();
        initEvent();
    }

    private void initEvent() {
        tv_back.setOnClickListener(this);
        tv_minus.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        iv_goods= (ImageView) findViewById(R.id.iv_goods);
        tv_des= (TextView) findViewById(R.id.tv_des);
        tv_price= (TextView) findViewById(R.id.tv_price);
        tv_minus= (TextView) findViewById(R.id.tv_minus);
        tv_number= (TextView) findViewById(R.id.tv_number);
        tv_add= (TextView) findViewById(R.id.tv_add);
        tv_ok= (TextView) findViewById(R.id.tv_ok);

        ImageLoader.getInstance().displayImage(url,iv_goods,options);
        tv_des.setText(des);
        tv_number.setText(number);
        tv_price.setText("¥"+price);
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
            case R.id.tv_ok:
                final String buynum=tv_number.getText().toString().trim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AVQuery<AVObject> query=new AVQuery<>("ShopCartEntity");
                        try {
                            AVObject object=query.get(myId);
                            object.put("number",buynum);
                            object.saveInBackground();
                            Message message = new Message();
                            message.what = 100;
                            handler.sendMessage(message);
                        } catch (AVException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Toast.makeText(EditGoodsActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };
}
