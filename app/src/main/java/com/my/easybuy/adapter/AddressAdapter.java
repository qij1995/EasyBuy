package com.my.easybuy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.easybuy.Entity.AddressEntity;
import com.my.easybuy.R;

import java.util.List;

/**
 * Created by user999 on 2017/2/28.
 */

public class AddressAdapter extends BaseAdapter {
    private Context context;
    private List<AddressEntity> list;

    public AddressAdapter(Context context, List<AddressEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<AddressEntity> list){
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
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_address,null);
        TextView tv_address= (TextView) view.findViewById(R.id.tv_address);
        TextView tv_phone= (TextView) view.findViewById(R.id.tv_phone);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        tv_address.setText(list.get(position).getAddress());
        tv_phone.setText(list.get(position).getPhone());
        tv_name.setText(list.get(position).getName());
        return view;
    }
}
