package gjw.bibi.fragment;

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
import gjw.bibi.adapter.ToThemAdapter;
import gjw.bibi.adapter.ToThemZhAdapter;
import gjw.bibi.base.BaseFragment;
import gjw.bibi.bean.ToThemBean;
import gjw.bibi.utils.AppNetConfig;
import gjw.bibi.view.MyGridView;
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
    private View view;
    private ToThemAdapter toThemAdapter;
    private ToThemZhAdapter toThemZhAdapter;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_tothem, null);
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
                .url(AppNetConfig.TO_THEM)
                .build().execute(new StringCallback() {
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
        ToThemBean toThemBean = JSON.parseObject(response, ToThemBean.class);
        List<ToThemBean.ResultBean.PreviousBean.ListBean> previousBean = toThemBean.getResult().getPrevious().getList();
        //设置适配器
        toThemAdapter = new ToThemAdapter(context, previousBean);
        gvTothem.setAdapter(toThemAdapter);


        List<ToThemBean.ResultBean.SerializingBean> serializingBeen = toThemBean.getResult().getSerializing();
        toThemZhAdapter = new ToThemZhAdapter(context, serializingBeen);
        gvZhTothem.setAdapter(toThemZhAdapter);


        //设置布局管理器
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3);

//        RecyclerView.LayoutManager rvLayoutManager = rvTothem.getLayoutManager();
//        GridLayoutManager gridLayoutManager = (GridLayoutManager) rvLayoutManager;
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//
//                if () {
//
//                }
//                return 1;
//            }
//        });

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
