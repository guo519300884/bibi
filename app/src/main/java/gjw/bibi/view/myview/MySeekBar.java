package gjw.bibi.view.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 皇上 on 2017/3/31.
 * 作者：郭建伟
 * QQ:519300884
 */

public class MySeekBar extends android.support.v7.widget.AppCompatSeekBar {
    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        super.onTouchEvent(event);
        return true;
    }
}
