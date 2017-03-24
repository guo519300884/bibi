package gjw.bibi.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.adapter.LVOriginalAdapter;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.bean.OriginalBean;
import gjw.bibi.utils.AppNetConfig;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/22.
 */

public class OriginalFragment extends BaseFragment {


    @InjectView(R.id.lv_original)
    ListView lvOriginal;
    private View view;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_original, null);
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
                .url(AppNetConfig.ALLSTATION)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "没网了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
            }
        });
    }

    private void processData(String response) {
        OriginalBean originalBean = JSON.parseObject(response, OriginalBean.class);
        List<OriginalBean.DataBean> originalBeanData = originalBean.getData();

        LVOriginalAdapter lvOriginalAdapter = new LVOriginalAdapter(context, originalBeanData);
        lvOriginal.setAdapter(lvOriginalAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
