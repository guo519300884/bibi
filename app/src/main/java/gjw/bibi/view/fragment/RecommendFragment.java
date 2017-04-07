package gjw.bibi.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.List;

import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.RecommendBean;
import gjw.bibi.presenter.adapter.RecommendAdapter;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.base.BaseFragment01;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class RecommendFragment extends BaseFragment01 {
    public View view;
    @InjectView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    @InjectView(R.id.swipe_refresh_recommed)
    SwipeRefreshLayout swipeRefreshRecommed;

    private RecommendAdapter recommendAdapter;
    private List<RecommendBean.DataBean> recommendBeanData;
    private boolean isRefresh;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView() {
        swipeRefreshRecommed.setColorSchemeResources(R.color.colorPrimary, R.color.blue_30);
    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "RecommendFragment initData()" + error);
        } else {
            RecommendBean recommendBean = JSON.parseObject(json, RecommendBean.class);
            int code = recommendBean.getCode();
            if (code == 0) {
                recommendBeanData = recommendBean.getData();
                setAdapter(recommendBeanData);
            } else {
                Log.e("TAG", "联网失败了");
            }
        }
    }

    @Override
    protected void initListener() {
        swipeRefreshRecommed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                refresh();
            }
        });
    }

    @Override
    public void showLoading() {
        swipeRefreshRecommed.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshRecommed.setRefreshing(false);
    }


    private void setAdapter(List<RecommendBean.DataBean> recommendBeanData) {
        if (recommendAdapter == null) {
            recommendAdapter = new RecommendAdapter(context, recommendBeanData);
        } else {
            recommendAdapter.notifyDataSetChanged();
        }
        if (!isRefresh) {
            rvRecommend.setAdapter(recommendAdapter);
            rvRecommend.setLayoutManager(new LinearLayoutManager(context));
        } else {
            isRefresh = false;
        }
    }

    @Override
    public String setUrl() {
        return AppNetConfig.RECOMMEND;
    }



//    @Override
//    protected void initData() {
//        recommendAdapter = new RecommendAdapter(context);
//        rvRecommend.setAdapter(recommendAdapter);
//        //设置布局管理器
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
//        rvRecommend.setLayoutManager(gridLayoutManager);
//
//        intitReview();
//        getDataFromNet();
//    }

//    private void intitReview() {
//        //下拉多少像素出效果
//        swipeRefreshRecommed.setDistanceToTriggerSync(100);
//        //设置背景颜色
//        swipeRefreshRecommed.setProgressBackgroundColorSchemeResource(android.R.color.white);
//        //设置圈圈颜色
//        swipeRefreshRecommed.setColorSchemeColors(Color.parseColor("#88FB7299"));
//        swipeRefreshRecommed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(context, "干嘛", Toast.LENGTH_SHORT).show();
//                getDataFromNet();
//            }
//        });
//    }
//
//    private void getDataFromNet() {
//        OkHttpUtils.get()
//                .id(100)
//                .url(AppNetConfig.RECOMMEND)
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(context, "联网失败了哦", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                processData(response);
//                swipeRefreshRecommed.setRefreshing(false);
//            }
//        });
//    }
//
//    private void processData(String response) {
//
//        RecommendBean recommendBean = JSON.parseObject(response, RecommendBean.class);
//        List<RecommendBean.DataBean> recommendBeanData = recommendBean.getData();
//
//        if (recommendBeanData != null && recommendBeanData.size() > 0) {
//
//            if (recommendAdapter != null) {
//                //設置適配器
//                recommendAdapter.setDate(recommendBeanData);
//                recommendAdapter.notifyDataSetChanged();
////                recommendAdapter = new RecommendAdapter(context, recommendBeanData);
//            }
//        }
//    }
}
