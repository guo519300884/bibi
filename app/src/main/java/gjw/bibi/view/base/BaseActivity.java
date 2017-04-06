package gjw.bibi.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by 皇上 on 2017/3/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
        initListener();
    }

    protected abstract void initListener();

    protected abstract void initData();

    public abstract int getLayoutId();
}

