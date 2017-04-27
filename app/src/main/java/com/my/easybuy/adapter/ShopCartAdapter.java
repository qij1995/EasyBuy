package com.my.easybuy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.my.easybuy.Entity.ShopCartEntity;
import com.my.easybuy.R;
import com.my.easybuy.activity.EditGoodsActivity;
import com.my.easybuy.activity.ShopCartAddressActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import static android.R.attr.bitmap;
import static android.R.attr.id;
import static com.my.easybuy.R.drawable.a;
import static com.my.easybuy.R.id.imageView;
import static com.my.easybuy.R.id.rl_edit;

/**
 * Created by user999 on 2017/3/1.
 */

public class ShopCartAdapter extends BaseAdapter {
    private Context context;
    private List<ShopCartEntity> list;
    private ShopCartAdapter adapter;


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_shop_cart,null);
        ImageView iv_goods= (ImageView) view.findViewById(R.id.iv_goods);
        TextView tv_des= (TextView) view.findViewById(R.id.tv_des);
        TextView tv_price= (TextView) view.findViewById(R.id.tv_price);
        TextView tv_buy= (TextView) view.findViewById(R.id.tv_buy);
        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
        RelativeLayout rl_edit = (RelativeLayout) view.findViewById(R.id.rl_edit);
        RelativeLayout rl_delet = (RelativeLayout) view.findViewById(R.id.rl_delet);


        final String des=list.get(position).getDes();
        final String price=list.get(position).getPrice();
        final String number=list.get(position).getNumber();
        final String myId=list.get(position).getMyId();
        final int positions=position;
        tv_des.setText(des);
        tv_price.setText("¥"+price);
        tv_num.setText("x"+number);
        final String url=list.get(position).getUrl();
        ImageLoader.getInstance().displayImage(url,iv_goods,options);

//        String objId=list.get(position).getObjId();

        rl_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("url",url);
                intent.putExtra("des",des);
                intent.putExtra("price",price);
                intent.putExtra("number",number);
                intent.putExtra("myId",myId);
                intent.setClass(context, EditGoodsActivity.class);
                context.startActivity(intent);
            }
        });

        rl_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("确定删除？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(positions);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String objectId = myId;
                                AVQuery<AVObject> query = new AVQuery<AVObject>("ShopCartEntity");
                                try {
                                    AVObject object = query.get(objectId);
                                    object.deleteInBackground();
//                                    Message message = new Message();
//                                    message.what = 100;
//                                    handler.sendMessage(message);
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
                    }
                }).setCancelable(true).show();
            }

        });


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

    public void remove(int position){
        list.remove(position);
        this.notifyDataSetChanged();
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

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
////            super.handleMessage(msg);
//            switch (msg.what) {
//                case 100:
////                    initData();
//                    adapter.setData(list);
//                    adapter.notifyDataSetChanged();
//                    break;
//            }
//        }
//    };

}
