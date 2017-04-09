package gjw.bibi.view.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.view.base.BaseActivity;

public class AllClassifyActivity extends BaseActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.head_title)
    TextView headTitle;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_classify;
    }

    @OnClick({R.id.ib_back, R.id.head_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.head_title:
                headTitle.setText("全部分类");
                break;
        }
    }
}
