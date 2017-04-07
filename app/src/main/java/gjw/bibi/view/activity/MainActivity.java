package gjw.bibi.view.activity;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.presenter.adapter.MainAdapter;
import gjw.bibi.search.IOnSearchClickListener;
import gjw.bibi.search.SearchFragment;
import gjw.bibi.utils.CacheUtils;
import gjw.bibi.view.base.BaseFragment01;
import gjw.bibi.view.fragment.DiscoverFragment;
import gjw.bibi.view.fragment.DynamicStateFragment;
import gjw.bibi.view.fragment.LiveStreamingFragment;
import gjw.bibi.view.fragment.RecommendFragment;
import gjw.bibi.view.fragment.ToThemFragment;
import gjw.bibi.view.fragment.ZoneFragment;
import gjw.bibi.view.myview.CircleImageView;

import static gjw.bibi.app.MyApplication.context;
import static gjw.bibi.view.activity.LoginActivity.KEY;
import static gjw.bibi.view.activity.LoginActivity.NUM;
import static gjw.bibi.view.fragment.DiscoverFragment.SEARCH;


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
    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.head_title)
    TextView headTitle;
    @InjectView(R.id.lv_history)
    ListView lvHistory;
    @InjectView(R.id.fm_history)
    LinearLayout fmHistory;
    @InjectView(R.id.main_photo)
    CircleImageView mainPhoto;
    @InjectView(R.id.main_name)
    TextView mainName;

    private List<BaseFragment01> fragments;
    private boolean isDoulbe = false;
    private Intent intent;
    public static boolean isLogin = false;
    private boolean isDaytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // 去掉窗口标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        toolbar.inflateMenu(R.menu.tool_option);

        initFragment();
        initData();
        initListener();
    }

    private void initListener() {

        boolean dl = CacheUtils.getBoolean(context, KEY);

        if (dl) {
            String usstr = CacheUtils.getString(context, NUM);

            mainName.setText(usstr);
            mainPhoto.setImageResource(R.drawable.ll);

        } else {

            View leftHeaderView = navigationView.getHeaderView(0);
            ImageView leftHead = (ImageView) leftHeaderView.findViewById(R.id.iv_left_head);
            final ImageView choicemodel = (ImageView) leftHeaderView.findViewById(R.id.choicemodel);

            //默认选中左侧第一条item
            navigationView.getMenu().getItem(0).setChecked(true);

            navigationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dlMain.openDrawer(GravityCompat.START);
                }
            });

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.left_home:
                            intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.left_vip:
                            intent = new Intent(MainActivity.this, BigVipActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.left_integral:
                            //判断是否登录过  未登录就跳转登录页面
                            if (MainActivity.isLogin) {
                                intent = new Intent(MainActivity.this, IntegralActivity.class);
                                startActivity(intent);
                            } else {
                                intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            break;
                        case R.id.left_cache:
                            intent = new Intent(MainActivity.this, DownloadActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.left_review:
                            if (MainActivity.isLogin) {


                            } else {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            break;
                        case R.id.left_collect:
                            if (MainActivity.isLogin) {

                            } else {
                                intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            break;
                        case R.id.left_history:
                            clMain.setVisibility(View.GONE);
                            fmHistory.setVisibility(View.VISIBLE);
                            break;
                        case R.id.left_attention:
                            if (MainActivity.isLogin) {

                            } else {
                                intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            break;
                        case R.id.left_wallet:
                            if (MainActivity.isLogin) {

                            } else {
                                intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            break;
                        case R.id.left_themeselection:
                            if (MainActivity.isLogin) {

                            } else {
                                intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            break;
                        case R.id.left_setting:
                            intent = new Intent(MainActivity.this, SettingActivity.class);
                            startActivity(intent);
                            break;
                    }

                    dlMain.setClickable(true);
                    dlMain.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
            leftHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.isLogin) {

                    } else {
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    dlMain.closeDrawer(GravityCompat.START);
                }
            });

            choicemodel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDaytime) {
                        choicemodel.setImageResource(R.drawable.ic_switch_daily);
                        isDaytime = false;
                    } else {
                        choicemodel.setImageResource(R.drawable.ic_switch_night);
                        isDaytime = true;
                    }
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
                            // 搜索
                            //第一句 , 实例化:
                            SearchFragment searchFragment = SearchFragment.newInstance();
                            //第二句 , 设置回调:
                            searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
                                @Override
                                public void OnSearchClick(String keyword) {

                                    intent = new Intent(MainActivity.this, SearchActivity.class);
                                    intent.putExtra(SEARCH, keyword);
                                    startActivity(intent);
                                    //这里处理逻辑
                                    Toast.makeText(MainActivity.this, keyword, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void OnScanClick() {
                                    //扫描二维码
                                    intent = new Intent(MainActivity.this, CaptureActivity.class);
                                    startActivityForResult(intent, 3);
                                }

                            });
                            //第三句, 显示搜索框:
                            searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                            break;
                    }
                    return false;
                }
            });
        }
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


    /**
     * 处理二维码扫描结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(context, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
