package gjw.bibi.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import gjw.bibi.base.BaseFragment;

/**
 * Created by 皇上 on 2017/3/21.
 */

public class DynamicStateFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);

        return textView;
    }

    @Override
    protected void initData() {
        textView.setText("33333333");
    }
}
