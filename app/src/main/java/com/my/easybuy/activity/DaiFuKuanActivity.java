package com.my.easybuy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.easybuy.Entity.BuyGoodsEntity;
import com.my.easybuy.R;
import com.my.easybuy.adapter.MyBuyAdapter;

import java.util.ArrayList;
import java.util.List;


public class DaiFuKuanActivity extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tv_back;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private List<BuyGoodsEntity> list=new ArrayList<>();
    private MyBuyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daifukuan);
        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("onResume","OnResume有作用");
        startRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        Log.e("onRefresh","onRefresh有作用");
        initData();
    }

    private void initData() {
        Log.e("initData","initData");
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("BuyGoodsEntity");
        query.orderByDescending("createAt");
        query.include("user");
        query.whereEqualTo("user", AVUser.getCurrentUser());
//        query.whereEqualTo("isFuKuan",false);
        query.whereEqualTo("state","待付款");
//        query.whereEqualTo("car_state","已处理");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String url=object.getString("url");
                        String des=object.getString("des");
                        String price=object.getString("price");
                        String address=object.getString("address");
                        String number=object.getString("number");
                        String phone=object.getString("phone");
                        String name=object.getString("name");
                        String objId=object.getString("objId");
                        String saleName=object.getString("saleName");
                        String objectId=object.getObjectId();

                        list.add(new BuyGoodsEntity(AVUser.getCurrentUser(),url,des,price,address,number,phone,name,objId,saleName,objectId));

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

    private void initEvent() {
        tv_back.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);//设置监听，需要重写onRefresh()方法，顶部下拉时会调用这个方法，

    }

    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv= (ListView) findViewById(R.id.lv);
        adapter=new MyBuyAdapter(list,this);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }

    /**
     * 开始刷新
     */
    public void startRefresh(){
        Log.e("startRefresh","startRefresh有作用");
        refreshLayout.setRefreshing(false);//设置刷新状态
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉进度条的颜色主题
        refreshLayout.setProgressViewOffset(false, 0, 50);//设置进度条是否缩放，以及相对Y轴位置
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
