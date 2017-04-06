package gjw.bibi.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.view.activity.LoginActivity;
import gjw.bibi.adapter.ToThemAdapter;
import gjw.bibi.adapter.ToThemZhAdapter;
import gjw.bibi.view.base.BaseFragment;
import gjw.bibi.bean.ToThemBean;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.myview.MyGridView;
import okhttp3.Call;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class ToThemFragment extends BaseFragment {


    @InjectView(R.id.dramaseries)
    RelativeLayout dramaseries;
    @InjectView(R.id.thediffuse)
    RelativeLayout thediffuse;
    @InjectView(R.id.schedule)
    LinearLayout schedule;
    @InjectView(R.id.index)
    LinearLayout index;
    @InjectView(R.id.tothem_login)
    ImageButton tothemLogin;
    @InjectView(R.id.gv_tothem)
    MyGridView gvTothem;
    @InjectView(R.id.tothem_title)
    LinearLayout tothemTitle;
    @InjectView(R.id.thediffuse_title)
    LinearLayout thediffuseTitle;
    @InjectView(R.id.gv_zh_tothem)
    MyGridView gvZhTothem;
    @InjectView(R.id.tothempage)
    LinearLayout tothempage;
    @InjectView(R.id.tothem_refresh)
    SwipeRefreshLayout tothemRefresh;
    private View view;
    private ToThemAdapter toThemAdapter;
    private ToThemZhAdapter toThemZhAdapter;
    private Intent intent;
    private ToThemBean toThemBean;
    private List<ToThemBean.ResultBean.PreviousBean.ListBean> previousBean;
    private List<ToThemBean.ResultBean.SerializingBean> serializingBeen;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_tothem, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {

        //设置适配器
        toThemAdapter = new ToThemAdapter(context);
        gvTothem.setAdapter(toThemAdapter);

        toThemZhAdapter = new ToThemZhAdapter(context);
        gvZhTothem.setAdapter(toThemZhAdapter);


        getDataFromNet();

        initReView();

    }

    private void initReView() {

        //下拉多少像素出效果
        tothemRefresh.setDistanceToTriggerSync(100);
        //设置背景颜色
        tothemRefresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置圈圈颜色
        tothemRefresh.setColorSchemeColors(Color.parseColor("#88FB7299"));
        tothemRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                .url(AppNetConfig.TO_THEM)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                processData(response);
                tothemRefresh.setRefreshing(false);
            }
        });
    }

    private void processData(String response) {
        toThemBean = JSON.parseObject(response, ToThemBean.class);
        previousBean = toThemBean.getResult().getPrevious().getList();
        serializingBeen = toThemBean.getResult().getSerializing();

        if (previousBean != null && previousBean.size() > 0
                && serializingBeen != null && serializingBeen.size() > 0) {

            toThemAdapter.setDateDrama(previousBean);
            toThemZhAdapter.setDateCartoon(serializingBeen);

            toThemAdapter.notifyDataSetChanged();
            toThemZhAdapter.notifyDataSetChanged();

            tothempage.setVisibility(View.VISIBLE);

        } else {
            tothempage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.dramaseries, R.id.thediffuse, R.id.schedule, R.id.index, R.id.tothem_login, R.id.tothem_title, R.id.thediffuse_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dramaseries:
                Toast.makeText(context, "00", Toast.LENGTH_SHORT).show();
                break;
            case R.id.thediffuse:
                Toast.makeText(context, "01", Toast.LENGTH_SHORT).show();
                break;
            case R.id.schedule:
                Toast.makeText(context, "02", Toast.LENGTH_SHORT).show();
                break;
            case R.id.index:
                Toast.makeText(context, "03", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tothem_login:
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(context, "04", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tothem_title:
                Toast.makeText(context, "05", Toast.LENGTH_SHORT).show();
                break;
            case R.id.thediffuse_title:
                Toast.makeText(context, "06", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
