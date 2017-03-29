package gjw.bibi.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.base.BaseActivity;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.fragment.DramaFragment;
import gjw.bibi.fragment.MovieFragment;
import gjw.bibi.fragment.SynFragment;
import gjw.bibi.fragment.UpFragment;

import static gjw.bibi.fragment.DiscoverFragment.SEARCH;

public class SearchActivity extends BaseActivity {

    @InjectView(R.id.ib_search_back)
    ImageButton ibSearchBack;
    @InjectView(R.id.ib_search_scan)
    ImageButton ibSearchScan;
    @InjectView(R.id.et_search_edt)
    EditText etSearchEdt;
    @InjectView(R.id.ib_search_search)
    ImageButton ibSearchSearch;
    @InjectView(R.id.ib_search_del)
    ImageButton ibSearchDel;
    @InjectView(R.id.tl_search)
    TabLayout tlSearch;
    @InjectView(R.id.vp_search)
    ViewPager vpSearch;

    private String seachcontent;
    private String keyword;
    private List<BaseFragment> fms;

    public void initListener() {

        keyword = getIntent().getStringExtra(SEARCH);


        seachcontent = etSearchEdt.getText().toString().trim();
        etSearchEdt.setText(keyword);


        etSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(seachcontent) && TextUtils.isEmpty(keyword)) {
                    ibSearchDel.setVisibility(View.GONE);
                } else {
                    ibSearchDel.setVisibility(View.VISIBLE);

                    ibSearchDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etSearchEdt.setText("");
                            ibSearchDel.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void initData() {
        initFragment();
//        getDataFromNet();


    }

    private void initFragment() {
        fms = new ArrayList<>();
        fms.add(new SynFragment());
        fms.add(new DramaFragment());
        fms.add(new UpFragment());
        fms.add(new MovieFragment());
    }

//    private void getDataFromNet() {
//        OkHttpUtils.get()
//                .id(100)
//                .url(AppNetConfig.KEYWORD_SEARCH(keyword))
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(SearchActivity.this, "联网失败了呢", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Toast.makeText(SearchActivity.this, "联网成功", Toast.LENGTH_SHORT).show();
//                Log.e("TAG", "SearchActivity onResponse()" + response);
//                processData(response);
//            }
//        });
//
//    }
//
//    private void processData(String response) {
//        TagSearchBean tagSearchBean = JSON.parseObject(response, TagSearchBean.class);
//        TagSearchBean.DataBean tagSearchBeanData = tagSearchBean.getData();
//
//        SearchAdapter searchAdapter = new SearchAdapter(getSupportFragmentManager(), fms, tagSearchBeanData);
//        vpSearch.setAdapter(searchAdapter);
//
//        tlSearch.setupWithViewPager(vpSearch);
//        tlSearch.setTabMode(TabLayout.MODE_SCROLLABLE);
//    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @OnClick({R.id.ib_search_back, R.id.ib_search_scan, R.id.et_search_edt, R.id.ib_search_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_search_back:
                finish();
                break;
            case R.id.ib_search_scan:
                break;
            case R.id.et_search_edt:
                break;
            case R.id.ib_search_search:
                break;
            case R.id.ib_search_del:
                etSearchEdt.setText("");
                break;
        }
    }
}
