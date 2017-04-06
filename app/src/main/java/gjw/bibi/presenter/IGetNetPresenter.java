package gjw.bibi.presenter;


/**
 * Created by 皇上 on 2017/4/5.
 * 作者：郭建伟
 * QQ:519300884
 */

public interface IGetNetPresenter {

    void onSuccess(String json);

    void onError(String error);

    void getDataFromNet();

    void cancelCall();

    void getDataFromNet(String url, ResultListener listener);
}
