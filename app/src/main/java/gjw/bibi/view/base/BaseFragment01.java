package gjw.bibi.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import gjw.bibi.presenter.GetNetPresenter;
import gjw.bibi.presenter.ResultListener;
import gjw.bibi.view.IGetNetView;

/**
 * Created by 皇上 on 2017/3/20.
 */

public abstract class BaseFragment01 extends Fragment implements IGetNetView {

    public Context context;
    private boolean isShow;
    private GetNetPresenter getNetPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(setLayoutId(), null);
        ButterKnife.inject(this, view);
        isShow = true;
        getNetPresenter = new GetNetPresenter(this);
        return view;
    }

    protected abstract
    @LayoutRes
    int setLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initNetData();
        initListener();
    }

    public void refresh() {
        initNetData();
    }

    private void initNetData() {
        if (TextUtils.isEmpty(setUrl())) {
            initData(null, "url是空的,无法请求数据");
            return;
        }
        getNetPresenter.getDataFromNet();
    }

    public void getDataFromNet(String url, ResultListener listener) {
        getNetPresenter.getDataFromNet(url, listener);
    }


    protected abstract void initData(String json, String error);

    protected abstract void initView();

    protected abstract void initListener();


    @Override
    public void onSuccess(String json) {
        initData(json, null);
    }

    @Override
    public void onError(String error) {
        initData(null, error);
    }

    @Override
    public abstract void showLoading();

    @Override
    public abstract void hideLoading();

    @Override
    public abstract String setUrl();

    @Override
    public boolean isShowView() {
        return isShow;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isShow = false;
        getNetPresenter.cancelCall();
        ButterKnife.reset(this);
    }
}
