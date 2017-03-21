package gjw.bibi.fragment;

import android.view.View;
import android.widget.GridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.base.BaseFragment;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class RecommendFragment extends BaseFragment {
    public View view;
    @InjectView(R.id.gv_recommend)
    GridView gvRecommend;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_find, null);

        return view;
    }

    @Override
    protected void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
//        OkHttpUtils.get()
//                .id(100)
//                .url(AppNetConfig.FIND)
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(context, "联网失败了哦", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                processData(response);
//            }
//        });

    }

    private void processData(String response) {


        //設置適配器
//        new recommendAdapter(context,);
//        gvRecommend.setAdapter();


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
