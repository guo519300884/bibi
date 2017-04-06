package gjw.bibi.view.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.adapter.RecommendAdapter;
import gjw.bibi.view.base.BaseFragment;
import gjw.bibi.bean.RecommendBean;
import gjw.bibi.utils.AppNetConfig;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class RecommendFragment extends BaseFragment {
    public View view;
    @InjectView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    @InjectView(R.id.swipe_refresh_recommed)
    SwipeRefreshLayout swipeRefreshRecommed;

    private RecommendAdapter recommendAdapter;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_recommend, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {

        recommendAdapter = new RecommendAdapter(context);
        rvRecommend.setAdapter(recommendAdapter);
        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        rvRecommend.setLayoutManager(gridLayoutManager);

        intitReview();

        getDataFromNet();
    }

    private void intitReview() {
        //下拉多少像素出效果
        swipeRefreshRecommed.setDistanceToTriggerSync(100);
        //设置背景颜色
        swipeRefreshRecommed.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置圈圈颜色
        swipeRefreshRecommed.setColorSchemeColors(Color.parseColor("#88FB7299"));
        swipeRefreshRecommed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(context, "干嘛", Toast.LENGTH_SHORT).show();
                getDataFromNet();
            }
        });

    }

    private void getDataFromNet() {
        OkHttpUtils.get()
                .id(100)
                .url(AppNetConfig.RECOMMEND)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "联网失败了哦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
                swipeRefreshRecommed.setRefreshing(false);
            }
        });
    }

    private void processData(String response) {

        RecommendBean recommendBean = JSON.parseObject(response, RecommendBean.class);
        List<RecommendBean.DataBean> recommendBeanData = recommendBean.getData();

        if (recommendBeanData != null && recommendBeanData.size() > 0) {

            if (recommendAdapter != null) {
                //設置適配器
                recommendAdapter.setDate(recommendBeanData);
                recommendAdapter.notifyDataSetChanged();
//                recommendAdapter = new RecommendAdapter(context, recommendBeanData);

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
