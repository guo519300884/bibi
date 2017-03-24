package gjw.bibi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;

public class SearchActivity extends AppCompatActivity {

    @InjectView(R.id.ib_search_back)
    ImageButton ibSearchBack;
    @InjectView(R.id.ib_search_scan)
    ImageButton ibSearchScan;
    @InjectView(R.id.et_search_edt)
    EditText etSearchEdt;
    @InjectView(R.id.ib_search_search)
    ImageButton ibSearchSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.ib_search_back, R.id.ib_search_scan, R.id.et_search_edt, R.id.ib_search_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_search_back:
                finish();
                break;
            case R.id.ib_search_scan:
                break;
            case R.id.et_search_edt:
                break;
            case R.id.ib_search_search:
                break;
        }
    }
}
