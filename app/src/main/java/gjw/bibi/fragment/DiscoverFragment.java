package gjw.bibi.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.activity.CircumActivity;
import gjw.bibi.activity.OriginalActivity;
import gjw.bibi.activity.SearchActivity;
import gjw.bibi.activity.TopicActivity;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.bean.TagBean;
import gjw.bibi.search.IOnSearchClickListener;
import gjw.bibi.search.SearchFragment;
import gjw.bibi.utils.AppNetConfig;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class DiscoverFragment extends BaseFragment {

    public static final String CIRCUN = "666";
    public static final String SEARCH = "搜索";
    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.ib_scan)
    ImageButton ibScan;
    @InjectView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    @InjectView(R.id.sl_tag)
    NestedScrollView slTag;
    @InjectView(R.id.tv_more)
    TextView tvMore;
    @InjectView(R.id.interest)
    TextView interest;
    @InjectView(R.id.topic)
    TextView topic;
    @InjectView(R.id.activity)
    TextView activity;
    @InjectView(R.id.original)
    TextView original;
    @InjectView(R.id.area)
    TextView area;
    @InjectView(R.id.gamecenter)
    TextView gamecenter;
    @InjectView(R.id.circum)
    TextView circum;
    @InjectView(R.id.ll_discover)
    LinearLayout llDiscover;
    private View view;
    private List<TagBean.DataBean.ListBean> tagBeanData;
    private TagAdapter adapter;
    private boolean isOpen = false;
    private Intent intent;
    private String keyw;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_discover, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .id(100)
                .url(AppNetConfig.TAG)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "联网失败咯", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);

            }
        });
    }

    private void processData(String response) {
        TagBean tagBean = JSON.parseObject(response, TagBean.class);
        tagBeanData = tagBean.getData().getList();

        if (tagBeanData != null && tagBeanData.size() > 0) {
            adapter = new TagAdapter(tagBeanData) {

                @Override
                public View getView(FlowLayout parent, int position, Object o) {

                    View view = View.inflate(context, R.layout.item_tag_gridview, null);
                    TextView textView = (TextView) view.findViewById(R.id.tv_tag);

                    GradientDrawable gradientDrawable = (GradientDrawable) textView.getBackground();

                    textView.setText(tagBeanData.get(position).getKeyword());
                    textView.setTextColor(Color.GRAY);
                    gradientDrawable.setColor(Color.WHITE);


                    idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {

                            keyw = tagBeanData.get(position).getKeyword();

                            intent = new Intent(context, SearchActivity.class);
                            intent.putExtra(SEARCH, keyw);
                            startActivity(intent);

                            Toast.makeText(context, "哈哈哈哈", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    return view;
                }
            };
            if (idFlowlayout != null) {
                idFlowlayout.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.topic, R.id.tv_more, R.id.activity, R.id.original, R.id.area, R.id.gamecenter, R.id.circum, R.id.tv_search, R.id.ib_scan})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_search:
                // 搜索
                //第一句 , 实例化:
                SearchFragment searchFragment = SearchFragment.newInstance();
                //第二句 , 设置回调:
                searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
                    @Override
                    public void OnSearchClick(String keyword) {

                        intent = new Intent(context, SearchActivity.class);
                        intent.putExtra(SEARCH, keyword);
                        startActivity(intent);
                        //这里处理逻辑
                        Toast.makeText(context, keyword, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnScanClick() {
                        //扫描二维码
                        intent = new Intent(context, CaptureActivity.class);
                        startActivityForResult(intent, 3);
                    }
                });
                //第三句, 显示搜索框:
                searchFragment.show(getFragmentManager(), SearchFragment.TAG);
                break;
            case R.id.ib_scan:

                //扫描二维码
                intent = new Intent(context, CaptureActivity.class);
                startActivityForResult(intent, 3);

                break;
            case R.id.topic:
                intent = new Intent(context, TopicActivity.class);
                startActivity(intent);
                break;
            case R.id.activity:
                intent = new Intent(context, TopicActivity.class);
                startActivity(intent);
                break;
            case R.id.original:
                intent = new Intent(context, OriginalActivity.class);
                startActivity(intent);
                break;
            case R.id.area:
                intent = new Intent(context, OriginalActivity.class);
                startActivity(intent);
                break;
            case R.id.gamecenter:

                break;
            case R.id.circum:
                String url = "http://bmall.bilibili.com/#!/";
                intent = new Intent(context, CircumActivity.class);
                intent.putExtra(CIRCUN, url);
                startActivity(intent);
                break;
            case R.id.tv_more:
                more();
                break;
        }
    }

    private void more() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) slTag.getLayoutParams();

        if (!isOpen) {
            params.height = 500;
            slTag.setLayoutParams(params);
            tvMore.setText("收起");

            Drawable img = getResources().getDrawable(R.drawable.ic_arrow_up_gray_round);
            // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            tvMore.setCompoundDrawables(img, null, null, null); //设置左图标
            isOpen = true;
        } else {
            params.height = 180;
            slTag.setLayoutParams(params);
            tvMore.setText("查看更多");

            Drawable img = getResources().getDrawable(R.drawable.ic_arrow_down_gray_round);
            // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            tvMore.setCompoundDrawables(img, null, null, null); //设置左图标
            isOpen = false;
        }
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
