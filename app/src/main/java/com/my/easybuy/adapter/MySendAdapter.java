package com.my.easybuy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.my.easybuy.Entity.BuyGoodsEntity;
import com.my.easybuy.R;
import com.my.easybuy.activity.BuyGoodsActivity;
import com.my.easybuy.activity.EditGoodsActivity;
import com.my.easybuy.activity.ShopCartAddressActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.my.easybuy.R.id.rl_pay;

/**
 * Created by user999 on 2017/2/27.
 */

public class MySendAdapter extends BaseAdapter {
    private List<BuyGoodsEntity> list;
    private Context context;

    public MySendAdapter(List<BuyGoodsEntity> list, Context context) {
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
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_my_send,null);
        ImageView iv= (ImageView) view.findViewById(R.id.iv_goods);
        TextView tv_des= (TextView) view.findViewById(R.id.tv_des);
        TextView tv_price= (TextView) view.findViewById(R.id.tv_price);
        TextView tv_number= (TextView) view.findViewById(R.id.tv_number);
        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
        RelativeLayout rl_send = (RelativeLayout) view.findViewById(R.id.rl_send);

        ImageLoader.getInstance().displayImage(list.get(position).getUrl(),iv,options);
        tv_des.setText(list.get(position).getDes());
        tv_price.setText("¥"+list.get(position).getPrice());
        tv_number.setText(list.get(position).getObjId());
        tv_num.setText("x"+list.get(position).getNumber());
        final String objId=list.get(position).getObjId();
        final int positions=position;

        rl_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(positions);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AVQuery<AVObject> query = new AVQuery<>("BuyGoodsEntity");
                        try {
                            AVObject object = query.get(objId);
                            object.put("state","待收货");
                            object.saveInBackground();
                                    Message message = new Message();
                                    message.what = 100;
                                    handler.sendMessage(message);
                        } catch (AVException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        return view;
    }

    public void remove(int position){
        list.remove(position);
        this.notifyDataSetChanged();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Toast.makeText(context,"提醒成功!",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
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
