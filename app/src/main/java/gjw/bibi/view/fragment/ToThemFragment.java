package gjw.bibi.view.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.bean.ToThemBean;
import gjw.bibi.presenter.adapter.ToThemAdapter;
import gjw.bibi.presenter.adapter.ToThemZhAdapter;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.activity.LoginActivity;
import gjw.bibi.view.base.BaseFragment01;
import gjw.bibi.view.myview.MyGridView;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class ToThemFragment extends BaseFragment01 {

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

    private Intent intent;
    private boolean isRefresh;
    private ToThemAdapter toThemAdapter;
    private List<ToThemBean.ResultBean.PreviousBean.ListBean> listBeen;
    private List<ToThemBean.ResultBean.SerializingBean> serializingBeen;
    private ToThemZhAdapter toThemZhAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_tothem;
    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "ToThemFragment initData()" + error);
        } else {
            ToThemBean toThemBean = JSON.parseObject(json, ToThemBean.class);
            int code = toThemBean.getCode();
            if (code == 0) {
                listBeen = toThemBean.getResult().getPrevious().getList();
                serializingBeen = toThemBean.getResult().getSerializing();
                setAdapter(listBeen);
                setZhAdapter(serializingBeen);
                tothempage.setVisibility(View.VISIBLE);
            } else {
                Log.e("TAG", "ToThemFragment initData()--联网失败");
            }
        }
    }

    @Override
    protected void initView() {
        tothemRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.blue_30);
    }


    @Override
    protected void initListener() {
        tothemRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                refresh();
            }
        });
    }

    @Override
    public void showLoading() {
        tothemRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        tothemRefresh.setRefreshing(false);
    }

    @Override
    public String setUrl() {
        return AppNetConfig.TO_THEM;
    }


    private void setAdapter(List<ToThemBean.ResultBean.PreviousBean.ListBean> listBeen) {
        if (toThemAdapter == null) {
            toThemAdapter = new ToThemAdapter(context, listBeen);
        } else {
            toThemAdapter.notifyDataSetChanged();
        }
        if (!isRefresh) {
            gvTothem.setAdapter(toThemAdapter);
        } else {
            isRefresh = false;
        }
    }

    private void setZhAdapter(List<ToThemBean.ResultBean.SerializingBean> serializingBeen) {
        if (toThemZhAdapter == null) {
            toThemZhAdapter = new ToThemZhAdapter(context, serializingBeen);
        } else {
            toThemZhAdapter.notifyDataSetChanged();
        }
        if (!isRefresh) {
            gvZhTothem.setAdapter(toThemZhAdapter);
        } else {
            isRefresh = false;
        }
    }


//    @Override
//    protected void initData() {
//
//        //设置适配器
//        toThemAdapter = new ToThemAdapter(context, toThemBeanResult);
//        gvTothem.setAdapter(toThemAdapter);
//
//        toThemZhAdapter = new ToThemZhAdapter(context);
//        gvZhTothem.setAdapter(toThemZhAdapter);
//
//
//        getDataFromNet();
//
//        initReView();
//
//    }
//
//    private void initReView() {
//
//        //下拉多少像素出效果
//        tothemRefresh.setDistanceToTriggerSync(100);
//        //设置背景颜色
//        tothemRefresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
//        //设置圈圈颜色
//        tothemRefresh.setColorSchemeColors(Color.parseColor("#88FB7299"));
//        tothemRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(context, "干嘛", Toast.LENGTH_SHORT).show();
//                getDataFromNet();
//            }
//        });
//
//    }
//
//    private void getDataFromNet() {
//        OkHttpUtils.get()
//                .id(100)
//                .url(AppNetConfig.TO_THEM)
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                processData(response);
//                tothemRefresh.setRefreshing(false);
//            }
//        });
//    }
//
//    private void processData(String response) {
//        toThemBean = JSON.parseObject(response, ToThemBean.class);
//        previousBean = toThemBean.getResult().getPrevious().getList();
//        serializingBeen = toThemBean.getResult().getSerializing();
//
//        if (previousBean != null && previousBean.size() > 0
//                && serializingBeen != null && serializingBeen.size() > 0) {
//
//            toThemAdapter.setDateDrama(previousBean);
//            toThemZhAdapter.setDateCartoon(serializingBeen);
//
//            toThemAdapter.notifyDataSetChanged();
//            toThemZhAdapter.notifyDataSetChanged();
//
//            tothempage.setVisibility(View.VISIBLE);
//
//        } else {
//            tothempage.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.reset(this);
//    }

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
