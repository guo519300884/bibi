package gjw.bibi.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.adapter.SearchAdapter;
import gjw.bibi.bean.TagSearchBean;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.base.BaseActivity;
import gjw.bibi.view.base.BaseFragment;
import gjw.bibi.view.fragment.DramaFragment;
import gjw.bibi.view.fragment.MovieFragment;
import gjw.bibi.view.fragment.SynFragment;
import gjw.bibi.view.fragment.UpFragment;
import okhttp3.Call;

import static gjw.bibi.view.fragment.DiscoverFragment.SEARCH;


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

    private String keyword;
    private List<BaseFragment> fms;
    public String srWord;
    public SearchAdapter searchAdapter;
    private TagSearchBean.DataBean tagSearchBeanData;
    private TagSearchBean tagSearchBean;

    public void initData() {

        keyword = getIntent().getStringExtra(SEARCH);

        etSearchEdt.setText(keyword);
        etSearchEdt.setSelection(etSearchEdt.getText().length());

        srWord = etSearchEdt.getText().toString().trim();

        getDataFromNet();
    }

    public void initListener() {

        etSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(etSearchEdt.getText().toString().trim())) {
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


    private void initFragment() {
        fms = new ArrayList<>();
        fms.add(new SynFragment(tagSearchBeanData));
        fms.add(new DramaFragment());
        fms.add(new UpFragment());
        fms.add(new MovieFragment());
    }

    private void getDataFromNet() {

        OkHttpUtils.get()
                .id(100)
                .url(AppNetConfig.KEYWORD_SEARCH(srWord))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(SearchActivity.this, "联网失败了呢", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Toast.makeText(SearchActivity.this, "联网成功", Toast.LENGTH_SHORT).show();
                processData(response);
            }
        });

    }

    private void processData(String response) {

        if (response == null) {
            Toast.makeText(this, "联网有错", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("TAG", "SearchActivity processData()000000000000000:" + response);
            tagSearchBean = JSON.parseObject(response, TagSearchBean.class);
            int code = tagSearchBean.getCode();
            if (code == 0) {
                tagSearchBeanData = tagSearchBean.getData();

                initFragment();

                searchAdapter = new SearchAdapter(getSupportFragmentManager(), fms, tagSearchBeanData);
                vpSearch.setAdapter(searchAdapter);

                tlSearch.setupWithViewPager(vpSearch);
                tlSearch.setTabMode(TabLayout.MODE_SCROLLABLE);


            } else {
                Toast.makeText(this, "搜索有错", Toast.LENGTH_SHORT).show();
            }
        }

    }

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
                srWord = etSearchEdt.getText().toString().trim();
                getDataFromNet();
                break;
            case R.id.ib_search_del:
                etSearchEdt.setText("");
                break;
        }
    }
}
