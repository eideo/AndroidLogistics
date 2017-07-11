package com.kenny.logistics.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import com.kenny.logistics.R;
import com.kenny.logistics.ui.adapter.MyPagerAdapter;
import com.kenny.logistics.ui.base.BaseActivity;
import com.kenny.logistics.ui.component.NoScrollViewPager;
import com.kenny.logistics.ui.fragment.FragmentMine;

import java.util.ArrayList;

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

    private FragmentMine fragmentMine;
    //private FragmentMy fragmentMy;
    //private FragmentCar fragmentCar;
    private FragmentTransaction transaction;
    private FragmentTransaction beginTransaction;
    private ArrayList<Fragment> fgList;
    private ScaleAnimation animation;
    private TranslateAnimation translateAnimation;
    private boolean isLogin;
    private RadioButton main_my_button;
    private RadioGroup radioGroup;
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
        fgList = new ArrayList<Fragment>();
        fragmentMine = new FragmentMine();
        //fragmentCar = new FragmentCar();
        fgList.add(fragmentMine);
        //fgList.add(fragmentCar);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void initUI() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fgList);
        viewPager.setAdapter(myPagerAdapter);

        final String[] colors = getResources().getStringArray(R.array.default_preview);
        NavigationTabBar.OnTabBarSelectedIndexListener onTabBarSelectedIndexListener = navigationTabBar.getOnTabBarSelectedIndexListener();
        navigationTabBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("====", "被点击了" + v.getId());
            }
        });
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                Log.i("====", "开始被选择" + index);
                //if (index==2)drawer.openDrawer(navigationView);
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                Log.i("====", "结束被选择" + index);
            }
        });
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.homenormal),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.mipmap.homeselect))
                        .title("我的订单")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.sort_press),
                        Color.parseColor(colors[1]))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("一键下单")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.my_press),
                        Color.parseColor(colors[2]))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("个人中心")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
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
