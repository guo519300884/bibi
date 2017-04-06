package gjw.bibi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;

public class AttentionActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.ib_back)
    public void onViewClicked() {
        finish();
    }
}
