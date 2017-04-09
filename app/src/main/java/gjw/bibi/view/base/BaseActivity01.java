package gjw.bibi.view.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;

import butterknife.ButterKnife;
import gjw.bibi.presenter.GetNetPresenter;
import gjw.bibi.presenter.ResultListener;
import gjw.bibi.view.IGetNetView;

/**
 * Created by 皇上 on 2017/3/26.
 */

public abstract class BaseActivity01 extends AppCompatActivity implements IGetNetView {

    private boolean isShow;
    protected GetNetPresenter getNetPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        //默认竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getLayoutId());
        ButterKnife.inject(this);

        getNetPresenter = new GetNetPresenter(this);
        isShow = true;

        initView();
        initDataNet();
        initListener();
    }


    private void initDataNet() {
        if (TextUtils.isEmpty(setUrl())) {
            initData(null, "url是空的,无法请求数据");
            return;
        }
        getNetPresenter.getDataFromNet();
    }

    protected void getDataFromNet(String url, ResultListener listener) {
        getNetPresenter.getDataFromNet(url, listener);
    }

    @Override
    protected void onDestroy() {
        isShow = false;
        getNetPresenter.cancelCall();
        super.onDestroy();
    }


    protected void refresh() {
        initDataNet();
    }

    public abstract String setUrl();


    @Override
    public boolean isShowView() {
        return isShow;
    }

    public abstract
    @LayoutRes
    int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();


    protected abstract void initData(String json, String error);

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
}

