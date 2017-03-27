package gjw.bibi.utils;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        ZXingLibrary.initDisplayOpinion(this);


    }
}
