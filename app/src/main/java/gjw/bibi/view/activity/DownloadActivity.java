package gjw.bibi.view.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.presenter.adapter.DLAdapter;
import gjw.bibi.search.IOnSearchClickListener;
import gjw.bibi.search.SearchFragment;
import gjw.bibi.view.base.BaseActivity;
import gjw.bibi.view.base.BaseFragment;
import gjw.bibi.view.fragment.CacheResultsFragment;
import gjw.bibi.view.fragment.IsCacheFragment;

import static gjw.bibi.view.fragment.DiscoverFragment.SEARCH;


public class DownloadActivity extends BaseActivity {

    @InjectView(R.id.iv_dl_back)
    ImageButton ivDlBack;
    @InjectView(R.id.dl_toolbar)
    Toolbar dlToolbar;
    @InjectView(R.id.tl_dl)
    TabLayout tlDl;
    @InjectView(R.id.dl_appbar)
    AppBarLayout dlAppbar;
    @InjectView(R.id.vp_dl)
    ViewPager vpDl;
    @InjectView(R.id.cl_dl_main)
    CoordinatorLayout clDlMain;

    private List<BaseFragment> fms;
    private Intent intent;


    @Override
    public int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    protected void initData() {
        dlToolbar.inflateMenu(R.menu.tool_dl);
        initFragment();

    }

    public void initFragment() {
        fms = new ArrayList<>();
        fms.add(new CacheResultsFragment());
        fms.add(new IsCacheFragment());
    }

    @Override
    protected void initListener() {

        DLAdapter dlAdapter = new DLAdapter(getSupportFragmentManager(), fms);
        vpDl.setAdapter(dlAdapter);

        tlDl.setupWithViewPager(vpDl);
        tlDl.setTabMode(TabLayout.MODE_FIXED);

        dlToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.dl_edit:

                        break;
                    case R.id.dl_setting:

                        break;
                    case R.id.dl_search:

                        // 搜索
                        //第一句 , 实例化:
                        SearchFragment searchFragment = SearchFragment.newInstance();
                        //第二句 , 设置回调:
                        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
                            @Override
                            public void OnSearchClick(String keyword) {

                                intent = new Intent(DownloadActivity.this,SearchActivity.class);
                                intent.putExtra(SEARCH, keyword);
                                startActivity(intent);
                                //这里处理逻辑
                                Toast.makeText(DownloadActivity.this, keyword, Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void OnScanClick() {
                                //扫描二维码
                                intent = new Intent(DownloadActivity.this, CaptureActivity.class);
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

    @OnClick(R.id.iv_dl_back)
    public void onViewClicked() {
        finish();
    }
}
