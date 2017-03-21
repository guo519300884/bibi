package gjw.bibi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gjw.bibi.R;

public class WelcomeActivity extends AppCompatActivity {

    @InjectView(R.id.rl_welcome)
    RelativeLayout rlWelcome;
    @InjectView(R.id.tv_welcome)
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.inject(this);

        initListener();
    }

    private void initListener() {

//        Glide.with(WelcomeActivity.this).load(R.drawable.ml).into();
        rlWelcome.setBackgroundResource(R.drawable.ml);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(3000);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rlWelcome.startAnimation(aa);
    }
}
