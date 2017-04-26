package com.my.easybuy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.easybuy.Entity.PingLunEntity;
import com.my.easybuy.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class PingLunAdapter extends BaseAdapter {
    private Context context;
    private List<PingLunEntity> list;

    public PingLunAdapter(Context context, List<PingLunEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<PingLunEntity> list){
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
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_pingjia,null);
        TextView tv_my_name= (TextView) view.findViewById(R.id.tv_my_name);
        TextView tv_my_content= (TextView) view.findViewById(R.id.tv_my_content);
        TextView tv_sale_name= (TextView) view.findViewById(R.id.tv_sale_name);
        TextView tv_sale_content= (TextView) view.findViewById(R.id.tv_sale_content);

        RelativeLayout rl_my= (RelativeLayout) view.findViewById(R.id.rl_my);

        tv_my_name.setText(list.get(position).getUser().getUsername()+":");
        tv_my_content.setText(list.get(position).getContent());

        if (list.get(position).getSaleContent()!=null && list.get(position).getSaleContent().length()>0){
            rl_my.setVisibility(View.VISIBLE);
            tv_sale_name.setText(":"+list.get(position).getSaleName());
            tv_sale_content.setText(list.get(position).getSaleContent());
        }else {
            rl_my.setVisibility(View.GONE);
        }



        return view;
    }
}
