package gjw.bibi.presenter;

/**
 * Created by 皇上 on 2017/4/5.
 * 作者：郭建伟
 * QQ:519300884
 */

public interface ResultListener {

    void onSuccess(String json);

    void onError(String error);
}
