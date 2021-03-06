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
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.my.easybuy.adapter.AddressAdapter;
import com.my.easybuy.Entity.AddressEntity;
import com.my.easybuy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user999 on 2017/2/28.
 */

public class AddressListActivity extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tv_back;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private TextView tv_add_address;
    private AddressAdapter adapter;
    private List<AddressEntity> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initView();
        initEvent();
    }



    private void initEvent() {
        tv_back.setOnClickListener(this);
        tv_add_address.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("address",list.get(position).getAddress());
                intent.putExtra("phone",list.get(position).getPhone());
                intent.putExtra("name",list.get(position).getName());
                setResult(100,intent);
                finish();
            }
        });
    }

    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv= (ListView) findViewById(R.id.lv);
        tv_add_address= (TextView) findViewById(R.id.tv_add_address);
        adapter=new AddressAdapter(this,list);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        initData();
    }

    private void initData() {
        list.clear();
        AVQuery<AVObject> query=new AVQuery<>("AddressEntity");
        query.include("user");
        query.whereEqualTo("user", AVUser.getCurrentUser());
        query.orderByDescending("createAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> objects, AVException e) {
                if (objects!=null && objects.size()>0){
                    for (AVObject object : objects){
                        String address=object.getString("address");
                        AVUser user=object.getAVUser("user");
                        String phone=object.getString("phone");
                        String name=object.getString("name");
                        list.add(new AddressEntity(user,address,phone,name));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent=new Intent();
                intent.setClass(this,AddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }
}
