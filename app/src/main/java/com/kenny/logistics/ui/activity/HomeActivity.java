package com.kenny.logistics.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.kenny.logistics.R;
import com.kenny.logistics.ui.adapter.MyPagerAdapter;
import com.kenny.logistics.ui.base.BaseActivity;
import com.kenny.logistics.ui.component.NoScrollViewPager;
import com.kenny.logistics.ui.fragment.FragmentCrateOrder;
import com.kenny.logistics.ui.fragment.FragmentMine;
import com.kenny.logistics.ui.fragment.FragmentOrder;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import devlight.io.library.ntb.NavigationTabBar;


@SuppressLint("ResourceAsColor")
public class HomeActivity extends BaseActivity {
    @BindView(R.id.ntb_horizontal)
    NavigationTabBar navigationTabBar;
    @BindView(R.id.vp_horizontal_ntb)
    NoScrollViewPager viewPager;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindDrawable(R.mipmap.ic_ntbbar_order)
    Drawable ic_ntbbar_order;
    @BindDrawable(R.mipmap.ic_ntbbar_customer)
    Drawable ic_ntbbar_customer;
    @BindDrawable(R.mipmap.ic_ntbbar_mine)
    Drawable ic_ntbbar_mine;

    @BindArray(R.array.default_preview)
    String[] colors;

    private FragmentMine fragmentMine;
    private FragmentOrder fragmentOrder;
    private ArrayList<Fragment> fgList;
    private Context context;
    private DrawerLayout drawer;


    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentpager();
        context = this;
        initDrawer();
        initUI();
    }

    private void initFragmentpager() {
        fgList = new ArrayList<>();
        fragmentOrder = new FragmentOrder();
        fragmentMine = new FragmentMine();
        fgList.add(fragmentOrder);
        fgList.add(new FragmentCrateOrder());
        fgList.add(fragmentMine);
    }

    private void initUI() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fgList);
        viewPager.setAdapter(myPagerAdapter);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                Log.i("====", "开始被选择" + index);
                StatusBarUtil.setColor(HomeActivity.this, Color.parseColor(colors[index]),0);
            }
            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
            }
        });

        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(ic_ntbbar_order,Color.parseColor(colors[0]))
                        .title("我的订单")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(ic_ntbbar_customer,Color.parseColor(colors[1]))
                        .title("一键下单")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(ic_ntbbar_mine,Color.parseColor(colors[2]))
                        .title("个人中心")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setViewPager(viewPager, 0);
    }

    /**
     * 初始化抽屉
     */

    private void initDrawer() {
        //https://segmentfault.com/a/1190000004151222
        if (navigationView != null) {
            //navigationView.setNavigationItemSelectedListener(this);
            View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
            RelativeLayout headerBackground = (RelativeLayout) headerLayout.findViewById(R.id.header_background);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		/*	ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
			drawer.addDrawerListener(toggle);
			toggle.syncState();*/
        }
    }
}
