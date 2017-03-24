package gjw.bibi.utils;

import android.content.Context;
import android.net.TrafficStats;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by 皇 上 on 2017/1/8.
 */

public class TimeUtils {

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public TimeUtils() {
        //转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    /**
     * 将毫秒转换为时分秒：1：20：30形式
     *
     * @param timeMs
     * @return
     */

    public String stringForTime(int timeMs) {

        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();

        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public String play(int play) {
        if (play > 9999) {
            float playf = ((float) play - (float) play % 1000) / 10000;
            return playf + "万";
        } else {
            return play + "";
        }
    }


    /**
     * 判断是否是网络资源
     *
     * @param url
     * @return
     */
    public boolean isNetUrl(String url) {
        boolean result = false;
        if (url != null) {
            if (url.toLowerCase().startsWith("http") ||
                    url.toLowerCase().startsWith("mms") ||
                    url.toLowerCase().startsWith("rtsp")) {

                result = true;
            }
        }
        return result;
    }

    //显示网络速度
    public String showNetSpeed(Context context) {

        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        String netSpeed = String.valueOf(speed) + " kb/s";
        return netSpeed;
    }
}
