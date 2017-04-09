package gjw.bibi.view.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;

import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.LiveStreamingBean;
import gjw.bibi.presenter.adapter.LiveStreamingAdapter;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.base.BaseFragment01;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class LiveStreamingFragment extends BaseFragment01 {

    @InjectView(R.id.rv_livestreaming)
    public RecyclerView rvLivestreaming;
    @InjectView(R.id.btn_more)
    Button btnMore;
//    @InjectView(R.id.rl_live)
//    RelativeLayout rlLive;
    @InjectView(R.id.swipe_refresh_live)
    SwipeRefreshLayout swipeRefreshLive;

    public boolean isRefresh;
    private LiveStreamingAdapter liveStreamingAdapter;
    private LiveStreamingBean.DataBean liveStreamingBeanData;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_livestreaming;
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        rvLivestreaming.setLayoutManager(layoutManager);
        rvLivestreaming.setHasFixedSize(true);
        rvLivestreaming.setNestedScrollingEnabled(false);

        swipeRefreshLive.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    protected void initListener() {
        swipeRefreshLive.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                refresh();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void showLoading() {
        swipeRefreshLive.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLive.setRefreshing(false);

    }

    @Override
    public String setUrl() {
        return AppNetConfig.LIVE_STREAMING;
    }


    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "LiveStreamingFragment initData()" + error);
        } else {
//            JSONObject jsonObject = JSONObject.parseObject(json);
//            Integer code = jsonObject.getInteger("code");
            LiveStreamingBean liveStreamingBean = JSON.parseObject(json, LiveStreamingBean.class);
            int code = liveStreamingBean.getCode();
            if (code == 0) {
                liveStreamingBeanData = liveStreamingBean.getData();
                setAdapter(liveStreamingBeanData);
//                setAdapter(JSON.parseObject(json, LiveStreamingBean.class).getData());
            } else {
                Log.e("TAG", "LiveStreamingFragment initData()联网失败");
            }
        }
    }

    private void setAdapter(LiveStreamingBean.DataBean liveStreamingBeanData) {
        if (liveStreamingAdapter == null) {
            liveStreamingAdapter = new LiveStreamingAdapter(context, liveStreamingBeanData);
        } else {
            liveStreamingAdapter.notifyDataSetChanged();
        }
        if (!isRefresh) {
            rvLivestreaming.setAdapter(liveStreamingAdapter);
            rvLivestreaming.setLayoutManager(new LinearLayoutManager(context));

            btnMore.setVisibility(View.VISIBLE);
        } else {
            isRefresh = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    //    @Override
//    protected void initData() {
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setSmoothScrollbarEnabled(true);
//        layoutManager.setAutoMeasureEnabled(true);
//
//        rvLivestreaming.setLayoutManager(layoutManager);
//        rvLivestreaming.setHasFixedSize(true);
//        rvLivestreaming.setNestedScrollingEnabled(false);
//
//        initialView();
//        livePresenter.FromNet(AppNetConfig.LIVE_STREAMING);
//
////        getDataFromNet();
//
//    }

//    private void initialView() {
//        //下拉多少像素出效果
//        swipeRefreshLive.setDistanceToTriggerSync(100);
//        //设置背景颜色
//        swipeRefreshLive.setProgressBackgroundColorSchemeResource(android.R.color.white);
//        //设置圈圈颜色
//        swipeRefreshLive.setColorSchemeColors(Color.parseColor("#88FB7299"));
//        swipeRefreshLive.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(context, "干嘛", Toast.LENGTH_SHORT).show();
//
////                getDataFromNet();
//            }
//        });
//    }

//    private void getDataFromNet() {
//        OkHttpUtils.get()
//                .id(100)
//                .url(AppNetConfig.LIVE_STREAMING)
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                processData(response);
//                swipeRefreshLive.setRefreshing(false);
//            }
//        });
//    }

//    private void processData(String response) {
//        liveStreamingBean = JSON.parseObject(response, LiveStreamingBean.class);
//        liveStreamingBeanData = liveStreamingBean.getData();
//
//        if (liveStreamingBeanData != null) {
//
//            btnMore.setVisibility(View.VISIBLE);
//            if (liveStreamingAdapter == null) {
//                //设置适配器
//                liveStreamingAdapter = new LiveStreamingAdapter(context, liveStreamingBeanData);
//                rvLivestreaming.setAdapter(liveStreamingAdapter);
//                //设置布局管理器
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
//                rvLivestreaming.setLayoutManager(gridLayoutManager);
//            } else {
//                liveStreamingAdapter.setData(liveStreamingBeanData);
//                liveStreamingAdapter.notifyDataSetChanged();
//            }
//        } else {
//            rlLive.setVisibility(View.GONE);
//        }
//    }


//    @Override
//    public void onSuccess(LiveStreamingBean liveStreamingBean) {
//
//        swipeRefreshLive.setRefreshing(false);
//
//        liveStreamingBeanData = liveStreamingBean.getData();
//
//        if (liveStreamingBeanData != null) {
//
//            btnMore.setVisibility(View.VISIBLE);
//            if (liveStreamingAdapter == null) {
//                //设置适配器
//                liveStreamingAdapter = new LiveStreamingAdapter(context, liveStreamingBeanData);
//                rvLivestreaming.setAdapter(liveStreamingAdapter);
//                //设置布局管理器
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
//                rvLivestreaming.setLayoutManager(gridLayoutManager);
//            } else {
//                liveStreamingAdapter.setData(liveStreamingBeanData);
//                liveStreamingAdapter.notifyDataSetChanged();
//            }
//        } else {
//            rlLive.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void showFailed(Throwable e) {
//        Toast.makeText(context, "错了" + e.getMessage(), Toast.LENGTH_SHORT).show();
//    }

}
