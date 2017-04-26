package com.my.easybuy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;


import com.my.easybuy.activity.SearchActivity;
import com.my.easybuy.activity.ShopListActivity;
import com.my.easybuy.R;
import com.my.easybuy.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{
    private View view;
    private GridView gridView;
    private Context context;
    private ViewPager viewPager;
    private int[] imageIds={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};
    private List<ImageView> imageList;
    private MyAdapter adapter;
    private int lastPosition = 0;
    private EditText et_search;
    private ImageView iv_search;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fgm_home,container,false);
        context=getActivity();
        imageList=new ArrayList<ImageView>();
        initView();

        for (int i=0;i<imageIds.length;i++){
            ImageView image=new ImageView(context);
            image.setBackgroundResource(imageIds[i]);
            imageList.add(image);
        }
        adapter=new MyAdapter();
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % imageList.size();
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 要求参数是一个足够大，且是imageList.size 的整数倍的数字
        int destPosition = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageList.size();

        viewPager.setCurrentItem(destPosition );

        isRunning = true;
        // 发送延时消息，2秒后，执行 handleMessage
        handler.sendEmptyMessageDelayed(FLUSH, 2000);




        return view;
    }

    private final int FLUSH = 100;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case FLUSH:
                    if(isRunning){
                        //  让viewPager 切换至下一页
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1 );
                        //  发送延时消息，2秒后，执行 handleMessage
                        handler.sendEmptyMessageDelayed(FLUSH, 2000);
                    }
                    break;
            }

        };
    };


    private boolean isRunning = false;

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning=false;
    }

    private void initView() {
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        gridView= (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(new HomeAdapter(context));
        gridView.setOnItemClickListener(this);

        et_search= (EditText) view.findViewById(R.id.et_search);
        iv_search= (ImageView) view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        switch (position){
            case 0:
                intent.putExtra("type","男装");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent.putExtra("type","女装");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.putExtra("type","男鞋");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent.putExtra("type","女鞋");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent.putExtra("type","内衣配饰");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent.putExtra("type","箱包手袋");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
            case 6:
                intent.putExtra("type","美妆个护");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent.putExtra("type","钟表珠宝");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
            case 8:
                intent.putExtra("type","手机数码");
                intent.setClass(context,ShopListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                String content=et_search.getText().toString().trim();
                Intent intent=new Intent();
                if (content!=null){
                    intent.putExtra("content",content);
                }
                intent.setClass(context, SearchActivity.class);
                startActivity(intent);

                break;
        }
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = imageList.get(position%imageList.size());
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if(view == object){
                return true;
            }
            return false;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

}
