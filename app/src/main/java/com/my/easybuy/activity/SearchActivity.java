package com.my.easybuy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by Administrator on 2017/3/5 0005.
 */

public class SearchActivity extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private String content;
    private TextView tv_back;
    private EditText et_search;
    private ImageView iv_search;
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private List<GoodsDetail> list=new ArrayList<>();
    private ShopListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        content=getIntent().getStringExtra("content");
        Log.e("II",content);
        initView();
        initEvent();
    }

    private void initEvent() {
        tv_back.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRefresh();
        initData(content);
    }

    private void initData(String content) {
        list.clear();
        if (content!=null && content.length()>0){
            AVQuery<AVObject> query=new AVQuery<>("GoodsDetail");
            query.whereContains("des",content);
            query.include("user");
            query.orderByDescending("createAt");
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> objects, AVException e) {
                    if (objects!=null && objects.size()>0){
                        for (AVObject object : objects){
                            AVFile pic=object.getAVFile("pic");
                            String des=object.getString("des");
                            String price=object.getString("price");
                            String type=object.getString("type");
                            String objId=object.getObjectId();
                            String saleName=object.getString("saleName");
                            list.add(new GoodsDetail(pic,des,price,type,objId,saleName));
                        }
                        adapter.setData(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        stopRefresh();
    }


    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        et_search= (EditText) findViewById(R.id.et_search);
        iv_search= (ImageView) findViewById(R.id.iv_search);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv= (ListView) findViewById(R.id.lv);

        if (content!=null){
            et_search.setText(content);
        }

        adapter=new ShopListAdapter(list,this);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_search:
                String content=et_search.getText().toString().trim();
                if (content!=null){
                    initData(content);
                }else {
                    Toast.makeText(this,"请先输入要搜索的内容...",Toast.LENGTH_SHORT).show();
                }

                break;
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
        String content=et_search.getText().toString().trim();
        initData(content);
    }
}
