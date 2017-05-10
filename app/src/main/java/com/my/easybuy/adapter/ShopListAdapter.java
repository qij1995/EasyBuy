package com.my.easybuy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.my.easybuy.Entity.GoodsDetail;
import com.my.easybuy.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by user999 on 2017/2/27.
 */

public class ShopListAdapter extends BaseAdapter {
    private List<GoodsDetail> list;
    private Context context;

    public ShopListAdapter(List<GoodsDetail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setData(List<GoodsDetail> list){
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
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_shop_list,null);
        ImageView iv= (ImageView) view.findViewById(R.id.iv_goods);
        TextView tv_des= (TextView) view.findViewById(R.id.tv_des);
        TextView tv_price= (TextView) view.findViewById(R.id.tv_price);

        AVFile pic=list.get(position).getPic();
        if (pic!=null){
            String url=pic.getUrl();
            ImageLoader.getInstance().displayImage(url,iv,options);
        }
        tv_des.setText(list.get(position).getDes());
        tv_price.setText("¥ "+list.get(position).getPrice());
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
