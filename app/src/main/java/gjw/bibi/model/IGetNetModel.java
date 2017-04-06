package gjw.bibi.model;


import gjw.bibi.presenter.ResultListener;

/**
 * Created by 皇上 on 2017/4/5.
 * 作者：郭建伟
 * QQ:519300884
 */

public interface IGetNetModel {

    void getDataFromNet(String url);

    void cancelCall();

    void getDataFromNet(String url, ResultListener listener);
}
