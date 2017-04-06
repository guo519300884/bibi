package gjw.bibi.presenter;


import gjw.bibi.model.GetNetModel;
import gjw.bibi.model.IGetNetModel;
import gjw.bibi.view.IGetNetView;

/**
 * Created by 皇上 on 2017/4/5.
 * 作者：郭建伟
 * QQ:519300884
 */

public class GetNetPresenter implements IGetNetPresenter {

    private IGetNetView iGetNetView;
    private IGetNetModel iGetNetModel;

    public GetNetPresenter(IGetNetView iGetNetView) {
        this.iGetNetView = iGetNetView;
        this.iGetNetModel = new GetNetModel(this);
    }

    @Override
    public void onSuccess(String json) {
        if (iGetNetView.isShowView()) {
            iGetNetView.hideLoading();
            iGetNetView.onSuccess(json);
        }
    }

    @Override
    public void onError(String error) {
        if (iGetNetView.isShowView()) {
            iGetNetView.hideLoading();
            iGetNetView.onError(error);
        }
    }


    @Override
    public void getDataFromNet() {
        if (iGetNetView.isShowView()) {
            iGetNetView.showLoading();
            iGetNetModel.getDataFromNet(iGetNetView.setUrl());
        }
    }

    @Override
    public void cancelCall() {
        iGetNetModel.cancelCall();
    }

    @Override
    public void getDataFromNet(String url, ResultListener listener) {
        iGetNetModel.getDataFromNet(url, listener);
    }


}
