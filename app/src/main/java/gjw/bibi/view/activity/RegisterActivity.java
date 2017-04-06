package gjw.bibi.view.activity;

import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.view.base.BaseActivity;

import static gjw.bibi.app.MyApplication.context;
import static gjw.bibi.utils.CacheUtils.setString;
import static gjw.bibi.view.activity.LoginActivity.NUM;
import static gjw.bibi.view.activity.LoginActivity.PW;

public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.head_title)
    TextView headTitle;
    private static SharedPreferences sp;

    @Override
    protected void initListener() {
        headTitle.setText("注册");
    }

    @Override
    protected void initData() {

        String usname = getIntent().getStringExtra(NUM);
        String uspw = getIntent().getStringExtra(PW);

        setString(context, usname, uspw);
    }

    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.ib_back)
    public void onViewClicked() {
        finish();
    }
}
