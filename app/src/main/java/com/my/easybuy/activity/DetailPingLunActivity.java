package com.my.easybuy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.easybuy.Entity.PingLunEntity;
import com.my.easybuy.R;
import com.my.easybuy.adapter.PingLunAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class DetailPingLunActivity extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tv_back;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
//    private TextView tv_add;
    private String objId;
    private List<PingLunEntity> list=new ArrayList<>();
    private PingLunAdapter adapter;
    private String saleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpinglun);

        objId=getIntent().getStringExtra("objId");
        saleName=getIntent().getStringExtra("saleName");
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
        AVQuery<AVObject> query=new AVQuery<>("PingLunEntity");
        query.include("user");
        query.orderByDescending("createAt");
        query.whereEqualTo("objId",objId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String objId=object.getString("objId");
                        AVUser user=object.getAVUser("user");
                        String content=object.getString("content");
                        String saleName=object.getString("saleName");
                        String saleContent=object.getString("saleContent");
                        list.add(new PingLunEntity(objId,user,content,saleName,saleContent));
                    }
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                    lv.setAdapter(adapter);
                }
            }
        });
        stopRefresh();
    }

    private void initEvent() {
        tv_back.setOnClickListener(this);
//        tv_add.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv= (ListView) findViewById(R.id.lv);
//        tv_add= (TextView) findViewById(R.id.tv_add);
        adapter=new PingLunAdapter(this,list);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
//            case R.id.tv_add:
//                Intent intent=new Intent();
//                intent.putExtra("objId",objId);
//                intent.putExtra("saleName",saleName);
//                intent.setClass(this,AddPingLunActivity.class);
//                startActivity(intent);
//                break;
        }
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

    @Override
    public void onRefresh() {
        initData();
    }
}
