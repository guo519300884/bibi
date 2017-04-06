package gjw.bibi.model;

import com.zhy.http.okhttp.request.RequestCall;

import gjw.bibi.presenter.IGetNetPresenter;
import gjw.bibi.presenter.ResultListener;
import gjw.bibi.utils.NetUtils;

/**
 * Created by 皇上 on 2017/4/5.
 * 作者：郭建伟
 * QQ:519300884
 */

public class GetNetModel implements IGetNetModel {

    private IGetNetPresenter iGetNetPresenter;
    private RequestCall requestCall;

    public GetNetModel(IGetNetPresenter iGetNetPresenter) {
        this.iGetNetPresenter = iGetNetPresenter;
    }

    @Override
    public void getDataFromNet(String url) {
        requestCall = NetUtils.getInstance().okhttpUtilsGet(url, new NetUtils.resultJson() {
            @Override
            public void onResponse(String json) {
                if (iGetNetPresenter != null) {
                    iGetNetPresenter.onSuccess(json);
                }
            }

            @Override
            public void onError(String error) {
                if (iGetNetPresenter != null) {
                    iGetNetPresenter.onError(error);
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if (requestCall != null) {
            requestCall.cancel();
        }
    }

    @Override
    public void getDataFromNet(String url, final ResultListener listener) {
        requestCall = NetUtils.getInstance().okhttpUtilsGet(url, new NetUtils.resultJson() {
            @Override
            public void onResponse(String json) {
                if (listener != null) {
                    listener.onSuccess(json);
                }
            }

            @Override
            public void onError(String error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        });
    }

}
