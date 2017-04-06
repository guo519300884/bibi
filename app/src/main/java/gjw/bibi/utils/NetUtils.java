package gjw.bibi.utils;

import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Call;

/**
 * Created by 皇上 on 2017/4/5.
 * 作者：郭建伟
 * QQ:519300884
 */

public class NetUtils {

    private NetUtils() {
    }

    private static class Tool {
        private static NetUtils okhtpUtils = new NetUtils();
    }

    public static NetUtils getInstance() {
        return Tool.okhtpUtils;
    }

    public RequestCall okhttpUtilsGet(String url, final resultJson result) {
        if (result == null) {
            return null;
        }
        if (TextUtils.isEmpty(url)) {
            result.onError("url是空的,无法请求");
            return null;
        }

        RequestCall build = OkHttpUtils.get().url(url).build();

        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                result.onError(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if (TextUtils.isEmpty(response)) {
                    result.onError("请求结果是空的,无法解析");
                    return;
                }
                result.onResponse(response);
            }
        });
        return build;
    }

    public interface resultJson {

        void onResponse(String json);

        void onError(String error);
    }
}


