package gjw.bibi.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
import gjw.bibi.activity.SearchActivity;
import gjw.bibi.activity.TopicActivity;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.bean.TagBean;
import gjw.bibi.utils.AppNetConfig;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class DiscoverFragment extends BaseFragment {

    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.ib_scan)
    ImageButton ibScan;
    @InjectView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
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
    private View view;
    private List<TagBean.DataBean.ListBean> tagBeanData;
    private TagAdapter adapter;

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
                    return view;
                }
            };

            idFlowlayout.setAdapter(adapter);

            idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    Toast.makeText(context, "哈哈哈哈", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.topic, R.id.activity, R.id.original, R.id.area, R.id.gamecenter, R.id.circum, R.id.tv_search, R.id.ib_scan})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_search:
                Intent sintent = new Intent(context, SearchActivity.class);
                startActivity(sintent);
                break;
            case R.id.ib_scan:
                break;

            case R.id.topic:
                Intent intent = new Intent(context, TopicActivity.class);
                startActivity(intent);
                break;
            case R.id.activity:
                Intent intent1 = new Intent(context, TopicActivity.class);
                startActivity(intent1);
                break;
            case R.id.original:

                break;
            case R.id.area:
                break;
            case R.id.gamecenter:
                break;
            case R.id.circum:
                break;
        }
    }
}
