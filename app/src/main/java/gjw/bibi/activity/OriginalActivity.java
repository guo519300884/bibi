package gjw.bibi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.adapter.OriginalAdapter;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.fragment.AllStationFragment;
import gjw.bibi.fragment.DramaSeriesFragment;
import gjw.bibi.fragment.OriginalFragment;

public class OriginalActivity extends AppCompatActivity {

    @InjectView(R.id.ib_original_back)
    ImageButton ibOriginalBack;
    @InjectView(R.id.ib_original_download)
    ImageButton ibOriginalDownload;
    @InjectView(R.id.ib_original_search)
    ImageButton ibOriginalSearch;
    @InjectView(R.id.toolbar_original)
    Toolbar toolbarOriginal;
    @InjectView(R.id.tl_original)
    TabLayout tlOriginal;
    @InjectView(R.id.appbar_original)
    AppBarLayout appbarOriginal;
    @InjectView(R.id.view_pager_original)
    ViewPager viewPagerOriginal;

    private List<BaseFragment> fragments;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original);
        ButterKnife.inject(this);

        initFragment();
        initData();
//        initListener();

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new OriginalFragment());
        fragments.add(new AllStationFragment());
        fragments.add(new DramaSeriesFragment());
    }

    private void initData() {
        //设置适配器
        OriginalAdapter originalAdapter = new OriginalAdapter(this.getSupportFragmentManager(), fragments);
        viewPagerOriginal.setAdapter(originalAdapter);
        //绑定ViewPager
        tlOriginal.setupWithViewPager(viewPagerOriginal);
        tlOriginal.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @OnClick({R.id.ib_original_back, R.id.ib_original_download, R.id.ib_original_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_original_back:
                finish();
                break;
            case R.id.ib_original_download:
                intent = new Intent(OriginalActivity.this,DownloadActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_original_search:
                intent = new Intent(OriginalActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
