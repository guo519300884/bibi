package gjw.bibi.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.InjectView;
import gjw.bibi.R;
import gjw.bibi.presenter.adapter.TopicAdapter;
import gjw.bibi.utils.ClipboardUtil;
import gjw.bibi.view.base.BaseActivity;

public class CircumActivity extends BaseActivity {

    @InjectView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @InjectView(R.id.wv_circum)
    WebView wvCircum;
    @InjectView(R.id.share_toolbar)
    Toolbar shareToolbar;
    private Intent intent;
    private String titles;
    private String link;

    @Override
    protected void initListener() {

    }

    public void initData() {

        titles = getIntent().getStringExtra(TopicAdapter.CIR);
        link = getIntent().getStringExtra(TopicAdapter.CIRCUM);


        if (!TextUtils.isEmpty(link)) {
            wvCircum.loadUrl(link);
        }
        shareToolbar.setTitle(TextUtils.isEmpty(titles) ? "详情" : titles);

        setSupportActionBar(shareToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_circum;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topic_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_share:
                share();
                break;
            case R.id.menu_browseropen:
                intent = new Intent(Intent.ACTION_VIEW);
                startActivity(intent);
                break;
            case R.id.menu_copylink:
                ClipboardUtil.setText(CircumActivity.this, link);
                Toast.makeText(this, "已复制", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //分享法
    private void share() {
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "来自「哔哩哔哩」的分享:" + link);
        startActivity(Intent.createChooser(intent, titles));
    }

    @Override
    public void onBackPressed() {
        if (wvCircum.canGoBack() && wvCircum.copyBackForwardList().getSize() > 0
                && !wvCircum.getUrl().equals(wvCircum.copyBackForwardList().getItemAtIndex(0)
                .getOriginalUrl())) {
            wvCircum.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        wvCircum.destroy();
        super.onDestroy();
    }
}
