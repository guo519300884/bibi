package gjw.bibi.listener;

/**
 * Created by 皇上 on 2017/3/31.
 * 作者：郭建伟
 * QQ:519300884
 */

public interface ResponseListener {

    /**
     * @param progress 已经下载或上传字节数
     * @param total    总字节数
     * @param done     是否完成
     */
    void onProgress(long progress, long total, boolean done);

    void onResponse();

    void onFailure(String error);
}
