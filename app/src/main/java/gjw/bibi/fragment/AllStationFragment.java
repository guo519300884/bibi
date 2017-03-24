package gjw.bibi.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import gjw.bibi.base.BaseFragment;

/**
 * Created by 皇上 on 2017/3/22.
 */

public class AllStationFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    protected void initData() {

        textView.setText("7777777777");
    }
}
