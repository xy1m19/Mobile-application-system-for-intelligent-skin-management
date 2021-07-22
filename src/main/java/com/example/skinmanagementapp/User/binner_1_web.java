package com.example.skinmanagementapp.User;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.skinmanagementapp.R;
import com.example.skinmanagementapp.module.GoodItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class binner_1_web extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    private GoodItem goodItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        goodItem = (GoodItem) getIntent().getSerializableExtra("data");

        webview.loadUrl(goodItem.url);
        onSetTitle(goodItem.getName());

        webview.setWebChromeClient(new WebChromeClient());
        webview. setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //解决android7.0以后版本加载异常问题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }


        });

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webview.setVerticalScrollbarOverlay(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setDomStorageEnabled(true);
    }

    public void onSetTitle(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView mTitle = toolbar.findViewById(R.id.tv_title);
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }
}
