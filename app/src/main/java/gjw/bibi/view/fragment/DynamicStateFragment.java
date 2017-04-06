package gjw.bibi.fragment;

import android.view.View;

import butterknife.ButterKnife;
import gjw.bibi.R;
import gjw.bibi.view.base.BaseFragment;

import static gjw.bibi.app.MyApplication.context;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class DynamicStateFragment extends BaseFragment {

    private View view;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.fragment_dynamicstate, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
