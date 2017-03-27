package gjw.bibi.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.adapter.LiveStreamingAdapter;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.bean.LiveStreamingBean;
import gjw.bibi.utils.AppNetConfig;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class LiveStreamingFragment extends BaseFragment {

    @InjectView(R.id.rv_livestreaming)
    RecyclerView rvLivestreaming;
    @InjectView(R.id.btn_more)
    Button btnMore;
    @InjectView(R.id.rl_live)
    RelativeLayout rlLive;
    @InjectView(R.id.swipe_refresh_live)
    SwipeRefreshLayout swipeRefreshLive;
    private View view;
    private LiveStreamingBean liveStreamingBean;
    private LiveStreamingBean.DataBean liveStreamingBeanData;
    private LiveStreamingAdapter liveStreamingAdapter;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_livestreaming, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        rvLivestreaming.setLayoutManager(layoutManager);
        rvLivestreaming.setHasFixedSize(true);
        rvLivestreaming.setNestedScrollingEnabled(false);

        initialView();
        getDataFromNet();

    }

    private void initialView() {
        //下拉多少像素出效果
        swipeRefreshLive.setDistanceToTriggerSync(100);
        //设置背景颜色
        swipeRefreshLive.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置圈圈颜色
        swipeRefreshLive.setColorSchemeColors(Color.parseColor("#88FB7299"));
        swipeRefreshLive.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                .url(AppNetConfig.LIVE_STREAMING)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
                swipeRefreshLive.setRefreshing(false);
            }
        });
    }

    private void processData(String response) {
        liveStreamingBean = JSON.parseObject(response, LiveStreamingBean.class);
        liveStreamingBeanData = liveStreamingBean.getData();

        if (liveStreamingBeanData != null) {

            btnMore.setVisibility(View.VISIBLE);
            if (liveStreamingAdapter == null) {
                //设置适配器
                liveStreamingAdapter = new LiveStreamingAdapter(context, liveStreamingBeanData);
                rvLivestreaming.setAdapter(liveStreamingAdapter);
                //设置布局管理器
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
                rvLivestreaming.setLayoutManager(gridLayoutManager);
            } else {
                liveStreamingAdapter.setData(liveStreamingBeanData);
                liveStreamingAdapter.notifyDataSetChanged();
            }
        } else {
            rlLive.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
