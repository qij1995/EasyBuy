package com.my.easybuy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.my.easybuy.Entity.GoodsDetail;
import com.my.easybuy.R;
import com.my.easybuy.adapter.ShopListAdapter;

import java.util.ArrayList;
import java.util.List;



public class ShopListActivity extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tv_back;
    private TextView tv_type;
    private String type;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private List<GoodsDetail> list=new ArrayList<>();
    private ShopListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist);

        type=getIntent().getStringExtra("type");
        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("GoodsDetail");
        query.include("user");
        query.whereEqualTo("type",type);
        query.whereEqualTo("state","售卖中");
        query.orderByDescending("createAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        AVFile pic=object.getAVFile("pic");
                        String des=object.getString("des");
                        String price=object.getString("price");
                        String objId=object.getString("objId");
                        String objectId=object.getObjectId();
                        String saleName=object.getAVUser("user").getUsername();
                        list.add(new GoodsDetail(pic,des,price,type,objId,saleName,objectId));
                    }
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


    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_type= (TextView) findViewById(R.id.tv_type);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv= (ListView) findViewById(R.id.lv);
        tv_type.setText(type);
        adapter=new ShopListAdapter(list,this);
        lv.setAdapter(adapter);

    }


    private void initEvent() {
        tv_back.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(ShopListActivity.this,GoodsDetailActivity.class);
                AVFile pic=list.get(position).getPic();
                if (pic!=null){
                    String url=pic.getUrl();
                    intent.putExtra("url",url);
                }
                intent.putExtra("des",list.get(position).getDes());
                intent.putExtra("price",list.get(position).getPrice());
                intent.putExtra("objId",list.get(position).getObjId());
                intent.putExtra("objectId",list.get(position).getObjectId());
                intent.putExtra("saleName",list.get(position).getSaleName());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
       initData();
    }
}
