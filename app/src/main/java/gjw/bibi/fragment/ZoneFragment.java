package gjw.bibi.fragment;

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
import gjw.bibi.adapter.ZoneHeadAdapter;
import gjw.bibi.adapter.ZoneTypeAdapter;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.bean.ZoneHeadBean;
import gjw.bibi.bean.ZoneTypeBean;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.MyGridView;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class ZoneFragment extends BaseFragment {
    @InjectView(R.id.rv_zone)
    RecyclerView rvZone;
    @InjectView(R.id.gv_zone)
    MyGridView gvZone;
    private View view;

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
        //请求内容数据
        getContentFromNet();


    }

    private void getContentFromNet() {
        OkHttpUtils.get().id(100).url(AppNetConfig.ZONE_TYPE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "没网了！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processContentData(response);
            }
        });
    }

    private void processContentData(String response) {
        ZoneTypeBean zoneTypeBean = JSON.parseObject(response, ZoneTypeBean.class);
        List<ZoneTypeBean.DataBean> zoneTypeBeanData = zoneTypeBean.getData();

        ZoneTypeAdapter zoneTypeAdapter = new ZoneTypeAdapter(context, zoneTypeBeanData);
        rvZone.setAdapter(zoneTypeAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        rvZone.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
//                type =
//                if (type) {
//
//                }
                return 0;
            }
        });
    }


    private void getHeadDataFromNet() {
        OkHttpUtils.get().id(100).url(AppNetConfig.ZONE_HEAD).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processHeadData(response);
            }
        });
    }

    private void processHeadData(String response) {
        ZoneHeadBean zoneHeadBean = JSON.parseObject(response, ZoneHeadBean.class);
        List<ZoneHeadBean.DataBean> zoneHeadBeanData = zoneHeadBean.getData();

        ZoneHeadAdapter zoneHeadAdapter = new ZoneHeadAdapter(context, zoneHeadBeanData);
        gvZone.setAdapter(zoneHeadAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
