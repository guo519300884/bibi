package gjw.bibi.fragment;

import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.adapter.GoodsAdapter;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.bean.GoodsBean;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.MyGridView;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/22.
 */

public class DramaSeriesFragment extends BaseFragment {

    @InjectView(R.id.gv_shopcar)
    MyGridView gvShopcar;
    private View view;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_shopcar, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        getGoodsFromNet();


    }

    private void getGoodsFromNet() {
        OkHttpUtils.get().id(100)
                .url(AppNetConfig.GOODS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);
                    }
                });
    }

    private void processData(String response) {

        GoodsBean goodsBean = JSON.parseObject(response, GoodsBean.class);
        List<GoodsBean.ResultBean.RecordsBean> recordsBeen = goodsBean.getResult().getRecords();


        if (recordsBeen != null && recordsBeen.size() > 0) {
            GoodsAdapter goodsAdapter = new GoodsAdapter(context, recordsBeen);
            gvShopcar.setAdapter(goodsAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
