package gjw.bibi.view.fragment;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.adapter.ZoneTypeAdapter;
import gjw.bibi.bean.ZoneHeadBean;
import gjw.bibi.bean.ZoneTypeBean;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.base.BaseFragment;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class ZoneFragment extends BaseFragment {
    @InjectView(R.id.rv_zone)
    RecyclerView rvZone;
    @InjectView(R.id.pb)
    ProgressBar pb;

    private View view;
    private ZoneHeadBean zoneHeadBean;
    private List<ZoneHeadBean.DataBean> zoneHeadBeanData;
    private List<ZoneTypeBean.DataBean> zoneTypeBeanData;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_zone, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        //請求頭部數據
        getHeadDataFromNet();

    }

    private void getHeadDataFromNet() {
        OkHttpUtils.get().id(100).url(AppNetConfig.ZONE_HEAD).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {

                zoneHeadBean = JSON.parseObject(response, ZoneHeadBean.class);
                zoneHeadBeanData = zoneHeadBean.getData();
                //请求内容数据
                getContentFromNet();
            }
        });
    }

    private void getContentFromNet() {
        OkHttpUtils.get().id(100).url(AppNetConfig.ZONE_TYPE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "没网了！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processZoneData(response);
            }
        });
    }

    private void processZoneData(String response) {

        ZoneTypeBean zoneTypeBean = JSON.parseObject(response, ZoneTypeBean.class);
        zoneTypeBeanData = zoneTypeBean.getData();

        if (zoneHeadBean != null && zoneTypeBean != null) {
            ZoneTypeAdapter zoneTypeAdapter = new ZoneTypeAdapter(context, zoneHeadBeanData, zoneTypeBeanData);
            rvZone.setAdapter(zoneTypeAdapter);

            pb.setVisibility(View.GONE);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
            rvZone.setLayoutManager(gridLayoutManager);
        } else {
            pb.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
