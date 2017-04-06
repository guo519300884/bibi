package gjw.bibi.view;

/**
 * Created by 皇上 on 2017/4/5.
 * 作者：郭建伟
 * QQ:519300884
 */

public interface IGetNetView {

    void onSuccess(String json);

    void onError(String error);

    void showLoading();

    void hideLoading();

    String setUrl();

    boolean isShowView();

}
