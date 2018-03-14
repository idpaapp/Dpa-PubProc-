package dpa_me.com.dpa_pubproc.Activitys;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class WebViewActivity extends AppCompatActivity {
    Context mContext;
    WebView mWebView;
    String mLink;
    String mTitle;
    RelativeLayout ProgressLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mLink = bundle.getString("mLink");
            mTitle = bundle.getString("mTitle");
        }

        mContext = PubProc.HandleApplication.SetActivityParams(this, R.layout.activity_web_view, false, mTitle);

        mWebView = findViewById(R.id.mWebView);
        ProgressLayout = findViewById(R.id.ProgressLayout);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                ProgressLayout.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                ProgressLayout.setVisibility(View.GONE);
            }
        });

        mWebView.loadUrl(mLink);
    }

}
