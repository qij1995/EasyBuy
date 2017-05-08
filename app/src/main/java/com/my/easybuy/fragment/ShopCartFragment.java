package com.my.easybuy.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.easybuy.Entity.ShopCartEntity;
import com.my.easybuy.R;
import com.my.easybuy.adapter.ShopCartAdapter;

import java.util.ArrayList;
import java.util.List;



public class ShopCartFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private List<ShopCartEntity> list=new ArrayList<>();
    private ShopCartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fgm_shop_cart,container,false);

        initView();
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    private void initEvent() {
        refreshLayout.setOnRefreshListener(this);

//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
//                dialog.setItems(new String[]{"删除", "编辑"}, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0:
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        String objectId = list.get(position).getMyId();
//                                        AVQuery<AVObject> query = new AVQuery<AVObject>("ShopCartEntity");
//                                        try {
//                                            AVObject object = query.get(objectId);
//                                            object.deleteInBackground();
//                                            list.remove(position);
//                                            Message message = new Message();
//                                            message.what = 100;
//                                            handler.sendMessage(message);
//                                        } catch (AVException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }).start();
//                                break;
//                            case 1:
//                                Intent intent=new Intent();
//                                intent.putExtra("url",list.get(position).getUrl());
//                                intent.putExtra("des",list.get(position).getDes());
//                                intent.putExtra("price",list.get(position).getPrice());
//                                intent.putExtra("number",list.get(position).getNumber());
//                                intent.putExtra("myId",list.get(position).getMyId());
//                                intent.setClass(getActivity(), EditGoodsActivity.class);
//                                startActivity(intent);
//                                break;
//                        }
//                    }
//                }).show();
//
//                return true;
//            }
//        });
    }

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

    private void initView() {
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        lv= (ListView) view.findViewById(R.id.lv);
        adapter=new ShopCartAdapter(getActivity(),list);
        lv.setAdapter(adapter);
    }


    @Override
    public void onRefresh() {
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("ShopCartEntity");
        query.include("user");
        query.whereEqualTo("user", AVUser.getCurrentUser());
        query.whereEqualTo("car_state","未处理");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String url=object.getString("url");
                        String des=object.getString("des");
                        String price=object.getString("price");
                        String number=object.getString("number");
                        String objId=object.getString("objId");
                        String saleName=object.getString("saleName");
                        String objectId=object.getObjectId();
                        String myId=object.getObjectId();
                        list.add(new ShopCartEntity(AVUser.getCurrentUser(),url,des,price,number,objId,myId,objectId,saleName));
                    }
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }else {
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        stopRefresh();
    }

    /**
     * 开始刷新
     */
    public void startRefresh(){
        refreshLayout.setRefreshing(false);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setProgressViewOffset(false, 0, 50);
        refreshLayout.setRefreshing(true);
    }

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
