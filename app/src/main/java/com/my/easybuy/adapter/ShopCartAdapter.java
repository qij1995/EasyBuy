package com.my.easybuy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.easybuy.Entity.ShopCartEntity;
import com.my.easybuy.R;
import com.my.easybuy.activity.ShopCartAddressActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by user999 on 2017/3/1.
 */

public class ShopCartAdapter extends BaseAdapter {
    private Context context;
    private List<ShopCartEntity> list;

    public ShopCartAdapter(Context context, List<ShopCartEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<ShopCartEntity> list){
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
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_shop_cart,null);
        ImageView iv_goods= (ImageView) view.findViewById(R.id.iv_goods);
        TextView tv_des= (TextView) view.findViewById(R.id.tv_des);
        TextView tv_price= (TextView) view.findViewById(R.id.tv_price);
        TextView tv_buy= (TextView) view.findViewById(R.id.tv_buy);


        final String des=list.get(position).getDes();
        final String price=list.get(position).getPrice();

        tv_des.setText(des);
        tv_price.setText("¥"+price);
        final String url=list.get(position).getUrl();
        ImageLoader.getInstance().displayImage(url,iv_goods,options);
        final String number=list.get(position).getNumber();
//        String objId=list.get(position).getObjId();

        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("url",url);
                intent.putExtra("des",des);
                intent.putExtra("price",price);
                intent.putExtra("number",number);
                intent.setClass(context, ShopCartAddressActivity.class);
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
