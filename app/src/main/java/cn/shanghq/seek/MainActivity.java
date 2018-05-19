package cn.shanghq.seek;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.shanghq.seek.fragment.ImgsFragment;
import cn.shanghq.seek.fragment.JokeFragment;
import cn.shanghq.seek.Adapter.MyFragmentAdapter;
import cn.shanghq.seek.fragment.NewsFragment;
import cn.shanghq.seek.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener{

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = drawer.getChildAt(0);
                View menu = drawerView;

                float scale = 1 - slideOffset;//1~0
                content.setTranslationX(menu.getMeasuredWidth() * (1 - scale));
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        StatusBarUtil.setTranslucentForDrawerLayout(MainActivity.this,drawer,50);

        appBarLayout=findViewById(R.id.appbar);

        viewPager=findViewById(R.id.viewPager);
        initViewPager();
    }



    private void initViewPager() {
        titles=new ArrayList<>();
        titles.add("New");
        titles.add("Picture");
        titles.add("Joke");
        titles.add("Video");


        final List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new ImgsFragment());
        fragmentList.add(new JokeFragment());
        fragmentList.add(new VideoFragment());

        FragmentPagerAdapter fragmentPagerAdapter= new MyFragmentAdapter(getSupportFragmentManager(),fragmentList,titles);

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);//设置底部导航栏
                getSupportActionBar().setTitle(titles.get(position));
                appBarLayout.setExpanded(true);//设置appbar出现
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            viewPager.setCurrentItem(0);

        } else if (id == R.id.nav_gallery) {
            viewPager.setCurrentItem(1);

        } else if (id == R.id.nav_slideshow) {
            viewPager.setCurrentItem(2);

        } else if (id == R.id.nav_manage) {
            viewPager.setCurrentItem(3);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }
}
