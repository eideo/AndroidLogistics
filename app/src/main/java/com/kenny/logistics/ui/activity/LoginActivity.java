package com.kenny.logistics.ui.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.kenny.logistics.R;
import com.kenny.logistics.ui.adapter.TabViewPagerAdapter;
import com.kenny.logistics.ui.base.BaseActivity;
import com.kenny.logistics.ui.fragment.FragmentLogin;
import com.kenny.logistics.ui.fragment.FragmentRegist;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_viewpager)
    ViewPager login_viewpager;
    @BindView(R.id.login_tabs)
    TabLayout login_tabs;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setupViewPager();
    }

    //设置tab下的viewpager
    private void setupViewPager() {
        setupViewPager(login_viewpager);
        login_tabs.setupWithViewPager(login_viewpager);
        login_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int f = tab.getPosition();
                login_viewpager.setCurrentItem(f);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentLogin(), "登录");
        adapter.addFrag(new FragmentRegist(), "注册");
        viewPager.setAdapter(adapter);
    }
}
