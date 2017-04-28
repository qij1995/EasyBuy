package com.my.easybuy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.easybuy.Entity.BuyGoodsEntity;
import com.my.easybuy.R;
import com.my.easybuy.activity.PayAddressActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by user999 on 2017/2/27.
 */

public class MyBuyAdapter extends BaseAdapter {
    private List<BuyGoodsEntity> list;
    private Context context;

    public MyBuyAdapter(List<BuyGoodsEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setData(List<BuyGoodsEntity> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_my_buy,null);
        ImageView iv= (ImageView) view.findViewById(R.id.iv_goods);
        TextView tv_des= (TextView) view.findViewById(R.id.tv_des);
        TextView tv_price= (TextView) view.findViewById(R.id.tv_price);
        TextView tv_number= (TextView) view.findViewById(R.id.tv_number);
        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
        RelativeLayout rl_pay = (RelativeLayout) view.findViewById(R.id.rl_pay);

        ImageLoader.getInstance().displayImage(list.get(position).getUrl(),iv,options);
        tv_des.setText(list.get(position).getDes());
        tv_price.setText("¥"+list.get(position).getPrice());
        tv_number.setText(list.get(position).getObjectId());
        tv_num.setText("x"+list.get(position).getNumber());
        final String des=list.get(position).getDes();
        final String price=list.get(position).getPrice();
        final String number=list.get(position).getNumber();
        final String url=list.get(position).getUrl();
        final String objectId=list.get(position).getObjectId();

        rl_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("url",url);
                intent.putExtra("des",des);
                intent.putExtra("price",price);
                intent.putExtra("number",number);
                intent.putExtra("pay_objectId",objectId);
                intent.setClass(context, PayAddressActivity.class);
                context.startActivity(intent);
            }
        });


        return view;
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
}
