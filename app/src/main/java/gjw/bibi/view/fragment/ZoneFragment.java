package gjw.bibi.view.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.bean.ZoneHeadBean;
import gjw.bibi.bean.ZoneTypeBean;
import gjw.bibi.presenter.ResultListener;
import gjw.bibi.presenter.adapter.ZoneTypeAdapter;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.base.BaseFragment01;


/**
 * Created by 皇上 on 2017/3/21.
 */

public class ZoneFragment extends BaseFragment01 {
    @InjectView(R.id.rv_zone)
    RecyclerView rvZone;

    private List<ZoneHeadBean.DataBean> zoneHeadBeanData;
    private ZoneHeadBean zoneHeadBean;
    private int code;
    private ZoneTypeAdapter zoneTypeAdapter;
    private List<ZoneTypeBean.DataBean> zoneTypeBeanData;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_zone;
    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "ZoneFragment initData()" + error);
        } else {
            zoneHeadBean = JSON.parseObject(json, ZoneHeadBean.class);
            code = zoneHeadBean.getCode();
            if (code == 0) {
                zoneHeadBeanData = zoneHeadBean.getData();
                getDataFromNet(AppNetConfig.ZONE_TYPE, new ResultListener() {
                    @Override
                    public void onSuccess(String json) {
                        ZoneTypeBean zoneTypeBean = JSON.parseObject(json, ZoneTypeBean.class);
                        code = zoneTypeBean.getCode();
                        if (code == 0) {
                            zoneTypeBeanData = zoneTypeBean.getData();
                            setAdapter(zoneHeadBeanData, zoneTypeBeanData);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("TAG", "ZoneFragment onError()" + error);
                    }
                });
            }
        }

    }

    private void setAdapter(List<ZoneHeadBean.DataBean> zoneHeadBeanData, List<ZoneTypeBean.DataBean> zoneTypeBeanData) {
        if (zoneTypeAdapter == null) {
            zoneTypeAdapter = new ZoneTypeAdapter(context, zoneHeadBeanData, zoneTypeBeanData);
            rvZone.setAdapter(zoneTypeAdapter);
            rvZone.setLayoutManager(new LinearLayoutManager(context));
        } else {
            zoneTypeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String setUrl() {
        return AppNetConfig.ZONE_HEAD;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    //    @Override
//    protected void initData() {
//        //請求頭部數據
//        getHeadDataFromNet();
//
//    }
//
//    private void getHeadDataFromNet() {
//        OkHttpUtils.get().id(100).url(AppNetConfig.ZONE_HEAD).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//
//                zoneHeadBean = JSON.parseObject(response, ZoneHeadBean.class);
//                zoneHeadBeanData = zoneHeadBean.getData();
//                //请求内容数据
//                getContentFromNet();
//            }
//        });
//    }

    //    private void getContentFromNet() {
//        OkHttpUtils.get().id(100).url(AppNetConfig.ZONE_TYPE).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(context, "没网了！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                processZoneData(response);
//            }
//        });
//    }
//
//    private void processZoneData(String response) {
//
//        ZoneTypeBean zoneTypeBean = JSON.parseObject(response, ZoneTypeBean.class);
//        zoneTypeBeanData = zoneTypeBean.getData();
//
//        if (zoneHeadBean != null && zoneTypeBean != null) {
//
//            zoneTypeAdapter = new ZoneTypeAdapter(context, zoneHeadBeanData, zoneTypeBeanData);
//            rvZone.setAdapter(zoneTypeAdapter);
//
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
//            rvZone.setLayoutManager(gridLayoutManager);
//        }
//    }
}
