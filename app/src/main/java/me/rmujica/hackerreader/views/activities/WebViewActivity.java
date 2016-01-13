package me.rmujica.hackerreader.views.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.rmujica.hackerreader.R;

public class WebViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private WebView web;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        web = (WebView) findViewById(R.id.web);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                refresh.setRefreshing(false);
            }
        });
        setTitle(getIntent().getExtras().getString("TITLE"));

        // load page
        refresh.post(() -> {
            refresh.setRefreshing(true);
            web.loadUrl(getIntent().getExtras().getString("URL"));
        });
    }

    @Override
    public void onRefresh() {
        web.reload();
    }
}
