package com.my.easybuy.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.my.easybuy.fragment.HomeFragment;
import com.my.easybuy.fragment.ShopCartFragment;
import com.my.easybuy.R;
import com.my.easybuy.fragment.SettingFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private HomeFragment homeFragment;
    private ShopCartFragment shopCartFragment;
//    private MessageFragment messageFragment;
    private SettingFragment meFragment;
    private Fragment[] fragments;
    private RelativeLayout[] TAB;
    private ImageView[] IMAGE;
    private int index;
    private int currentTabIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    private void initView() {
        if (null == homeFragment) {
            homeFragment = new HomeFragment();
        }
        if (null == shopCartFragment) {
            shopCartFragment = new ShopCartFragment();
        }
//        if (null == messageFragment){
//            messageFragment=new MessageFragment();
//        }
        if (null == meFragment){
            meFragment=new SettingFragment();
        }

        fragments = new Fragment[]{homeFragment,shopCartFragment,
                meFragment};
        TAB = new RelativeLayout[3];
        IMAGE = new ImageView[3];
        TAB[0] = (RelativeLayout) findViewById(R.id.rl_home);
        TAB[1] = (RelativeLayout) findViewById(R.id.rl_shop_cart);
//        TAB[2] = (RelativeLayout) findViewById(R.id.rl_message);
        TAB[2] = (RelativeLayout) findViewById(R.id.rl_me);
        IMAGE[0] = (ImageView) findViewById(R.id.iv_home);
        IMAGE[1] = (ImageView) findViewById(R.id.iv_shop_cart);
//        IMAGE[2] = (ImageView) findViewById(R.id.iv_message);
        IMAGE[2] = (ImageView) findViewById(R.id.iv_me);
        IMAGE[0].setSelected(true);
        TAB[0].setOnClickListener(this);
        TAB[1].setOnClickListener(this);
        TAB[2].setOnClickListener(this);
//        TAB[3].setOnClickListener(this);

        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment, "home")
                .add(R.id.fragment_container, shopCartFragment, "shopcart")
                .add(R.id.fragment_container,meFragment,"me")
                .hide(shopCartFragment).hide(meFragment)
                .show(homeFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home:
                index = 0;
                break;
            case R.id.rl_shop_cart:
                index = 1;
                break;

            case R.id.rl_me:
                index =2;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        IMAGE[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        IMAGE[index].setSelected(true);
        currentTabIndex = index;
    }
}
