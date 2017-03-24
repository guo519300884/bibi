package gjw.bibi.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gjw.bibi.R;

public class CircumActivity extends AppCompatActivity {

    @InjectView(R.id.ib_circum_back)
    ImageButton ibCircumBack;
    @InjectView(R.id.ib_circum_more)
    ImageButton ibCircumMore;
    @InjectView(R.id.wv_circum)
    WebView wvCircum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circum);
        ButterKnife.inject(this);

        initData();
    }

    private void initData() {

        WebSettings webSettings = wvCircum.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);

        wvCircum.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //判断版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }

                return true;
            }

        });


    }

    @OnClick({R.id.ib_circum_back, R.id.ib_circum_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_circum_back:
                finish();
                break;
            case R.id.ib_circum_more:
                break;
        }
    }
}
