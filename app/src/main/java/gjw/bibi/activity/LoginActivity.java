package gjw.bibi.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;
import gjw.bibi.base.BaseActivity;
import gjw.bibi.bean.User;
import gjw.bibi.gen.UserDao;
import gjw.bibi.utils.IEditTextChangeListener;
import gjw.bibi.utils.MyApplication;
import gjw.bibi.utils.WorksSizeCheckUtil;

import static gjw.bibi.utils.MyApplication.context;

public class LoginActivity extends BaseActivity {


    public static final String NUM = "账号";
    public static final String PW = "密码";
    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.forgetpd)
    TextView forgetpd;
    @InjectView(R.id.tologin1)
    ImageView tologin1;
    @InjectView(R.id.pwtologin1)
    ImageView pwtologin1;
    @InjectView(R.id.username)
    EditText username;
    @InjectView(R.id.tlusername)
    TextInputLayout tlusername;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.tlpw)
    TextInputLayout tlpw;
    @InjectView(R.id.register)
    Button register;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    private Intent intent;
    private String us;
    private String pw;
    private UserDao userDao;

    @Override
    protected void initData() {
        us = username.getText().toString().trim();
        pw = password.getText().toString().trim();

        userDao = MyApplication.instances.getDaoSession().getUserDao();
    }

    public void initListener() {

        tlusername.setHint("你的手机号/邮箱");
        tlpw.setHint("输入密码");

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tologin1.setImageResource(R.drawable.ic_22);
                    pwtologin1.setImageResource(R.drawable.ic_33);

                    //自动弹出软键盘
                    new Timer().schedule(new TimerTask() {
                        public void run() {
                            InputMethodManager inputManager = (InputMethodManager) username.getContext()
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.showSoftInput(username, 0);
                        }
                    }, 200);


                } else {
                    tologin1.setImageResource(R.drawable.ic_22_hide);
                    pwtologin1.setImageResource(R.drawable.ic_33_hide);
                }
            }
        });


        //1.创建工具类对象 把要改变颜色的tv先传过去
        WorksSizeCheckUtil.textChangeListener textChangeListener =
                new WorksSizeCheckUtil.textChangeListener(btnLogin);

        //2.把所有要监听的edittext都添加进去
        textChangeListener.addAllEditText(username, password);

        //3.接口回调 在这里拿到boolean变量 根据isHasContent的值决定 tv 应该设置什么颜色
        WorksSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }
        });

//        username.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if (!TextUtils.isEmpty(us) && !TextUtils.isEmpty(pw)) {
//                    btnLogin.setEnabled(true);
//                    btnLogin.setBackgroundColor(Color.parseColor("#FB7299"));
//                }
//                if (!TextUtils.isEmpty(us) && !TextUtils.isEmpty(pw)) {
//                    btnLogin.setEnabled(false);
//                    btnLogin.setBackgroundColor(Color.parseColor("#000000"));
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!TextUtils.isEmpty(us) && !TextUtils.isEmpty(pw)) {
//                    btnLogin.setEnabled(false);
//                    btnLogin.setBackgroundColor(Color.parseColor("#000000"));
//                }
//
//                if (TextUtils.isEmpty(us) && TextUtils.isEmpty(pw)) {
//                    btnLogin.setEnabled(true);
//                    btnLogin.setBackgroundColor(Color.parseColor("#FB7299"));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.ib_back, R.id.forgetpd, R.id.register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.forgetpd:
                intent = new Intent(this, ResetActivity.class);
                startActivity(intent);
                break;
            case R.id.register:

                User user = new User((long) 1, us, pw);
                userDao.insert(user);

                Toast.makeText(this, "开户口了", Toast.LENGTH_SHORT).show();

//                CacheUtils.setString(context, NUM, us);
//                CacheUtils.setString(context, PW, pw);

//                intent = new Intent(this, RegisterActivity.class);
//                intent.putExtra(NUM, us);
//                intent.putExtra(PW, pw);
//                startActivity(intent);
                break;
            case R.id.btn_login:

                List<User> users = userDao.loadAll();

                for (int i = 0; i < users.size(); i++) {

                    if (us.equals(users.get(i).getUsername())) {
                        if (pw.equals(users.get(i).getPasswrod())) {
                            intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "密码错了", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } else {
                        Toast.makeText(this, "账号错了", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }


//                String usstr = CacheUtils.getString(context, NUM);
//                String pwstr = CacheUtils.getString(context, PW);
//
//                if (us == usstr && pw == pwstr) {
//                    intent = new Intent(context, MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, "错了", Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }
}
