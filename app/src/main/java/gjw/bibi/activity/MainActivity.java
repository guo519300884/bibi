package gjw.bibi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.adapter.MainAdapter;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.fragment.DiscoverFragment;
import gjw.bibi.fragment.DynamicStateFragment;
import gjw.bibi.fragment.RecommendFragment;
import gjw.bibi.fragment.LiveStreamingFragment;
import gjw.bibi.fragment.ToThemFragment;
import gjw.bibi.fragment.ZoneFragment;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.navigation_layout)
    LinearLayout navigationLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tablayout)
    TabLayout tablayout;
    @InjectView(R.id.appbar)
    AppBarLayout appbar;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.cl_main)
    CoordinatorLayout clMain;
    @InjectView(R.id.navigation_view)
    NavigationView navigationView;
    @InjectView(R.id.dl_main)
    DrawerLayout dlMain;

    private List<BaseFragment> fragments;
    private boolean isDoulbe = false;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        toolbar.inflateMenu(R.menu.tool_option);

        initFragment();
        initData();
        initListener();
    }

    private void initListener() {

        View leftHeaderView = navigationView.getHeaderView(0);
        ImageView leftHead = (ImageView) leftHeaderView.findViewById(R.id.iv_left_head);

        navigationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlMain.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                dlMain.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        leftHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlMain.closeDrawer(GravityCompat.START);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.game_toolbar:
                        intent = new Intent(MainActivity.this, GameActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.download_toolbar:
                        intent = new Intent(MainActivity.this, DownloadActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search_toolbar:
                        intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new LiveStreamingFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new ToThemFragment());
        fragments.add(new ZoneFragment());
        fragments.add(new DynamicStateFragment());
        fragments.add(new DiscoverFragment());

    }

    private void initData() {
        //设置适配器
        MainAdapter mainAdapter = new MainAdapter(this.getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mainAdapter);
        //绑定viewpager
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
    }

    //双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Toast.makeText(this, "再点我分手", Toast.LENGTH_SHORT).show();

            if (isDoulbe) {
                finish();
            }

            isDoulbe = true;
            //定时器
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isDoulbe = false;
                }
            }, 2000);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
