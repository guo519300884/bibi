package gjw.bibi.activity;

import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.head_title)
    TextView headTitle;

    @Override
    protected void initListener() {
        headTitle.setText("注册");
    }

    @Override
    protected void initData() {




    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.ib_back)
    public void onViewClicked() {
        finish();
    }
}
