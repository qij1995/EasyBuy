package com.my.easybuy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.easybuy.R;


public class HomeAdapter extends BaseAdapter {
    private static Integer[] images = {
            R.drawable.nanzhuang,R.drawable.nvzhuang,R.drawable.nanxie,R.drawable.nvxie,R.drawable.neiyi,R.drawable.xiangbao,R.drawable.meizhuang,R.drawable.zhongbiao,R.drawable.shuma};
    private static String[] texts = {"男    装", "女    装", "男    鞋", "女    鞋", "内衣配饰", "箱包手袋",  "美妆个护","钟表珠宝","手机数码"};

    private Context context;


    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return 9;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            vh = new ViewHolder();
            vh.iv = (ImageView) convertView.findViewById(R.id.Image);
            vh.tv_des = (TextView) convertView.findViewById(R.id.Text);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.iv.setImageResource(images[position]);
        vh.tv_des.setText(texts[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView tv_des;
    }
}
