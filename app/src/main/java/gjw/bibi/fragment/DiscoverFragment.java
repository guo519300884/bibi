package gjw.bibi.fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.base.BaseFragment;
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
    private View view;

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

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.tv_search, R.id.ib_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                break;
            case R.id.ib_scan:
                break;
        }
    }
}
