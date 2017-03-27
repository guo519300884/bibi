package gjw.bibi.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.base.BaseActivity;
import gjw.bibi.utils.IEditTextChangeListener;
import gjw.bibi.utils.WorksSizeCheckUtil;

public class ResetActivity extends BaseActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.head_title)
    TextView headTitle;
    @InjectView(R.id.et_phonenumber)
    EditText etPhonenumber;
    @InjectView(R.id.authcode)
    Button authcode;

    @Override
    protected void initListener() {

        String phonenum = etPhonenumber.getText().toString().trim();

        if (!TextUtils.isEmpty(phonenum)) {
            authcode.setClickable(true);
            authcode.setBackgroundColor(Color.parseColor("#FB7299"));
        }


    }

    @Override
    protected void initData() {

        headTitle.setText("重置密码");

        //1.创建工具类对象 把要改变颜色的tv先传过去
        WorksSizeCheckUtil.textChangeListener textChangeListener =
                new WorksSizeCheckUtil.textChangeListener(authcode);

        //2.把所有要监听的edittext都添加进去
        textChangeListener.addAllEditText(etPhonenumber);

        //3.接口回调 在这里拿到boolean变量 根据isHasContent的值决定 tv 应该设置什么颜色
        WorksSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    authcode.setEnabled(true);
                } else {
                    authcode.setEnabled(false);
                }
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset;
    }


    @OnClick({R.id.ib_back, R.id.et_phonenumber, R.id.authcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.et_phonenumber:

                break;
            case R.id.authcode:

                break;
        }
    }
}
