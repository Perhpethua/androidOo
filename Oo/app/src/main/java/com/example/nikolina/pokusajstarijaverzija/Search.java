package com.example.nikolina.pokusajstarijaverzija;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Search extends AppCompatActivity {
    WebView webViewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.full_white_logo_m);

        //fetch string value
        String urlsufix = getIntent().getStringExtra("url");

        webViewSearch = (WebView) findViewById(R.id.id_webview_search);
        WebSettings webSettings = webViewSearch.getSettings();
        webSettings.setJavaScriptEnabled(true);

//force links to open in webview only---------------
        webViewSearch.setWebViewClient(new myWebClient1());
//--------------------------------------------------
        webViewSearch.getSettings().setJavaScriptEnabled(true);
        String urlFull = "http://www.oglasnik.hr/search?q=" + urlsufix + "#classifieds";
        webViewSearch.loadUrl(urlFull);

        //improve WebView performance
        webViewSearch.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webViewSearch.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webViewSearch.getSettings().setAppCacheEnabled(true);
        webViewSearch.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
    }
    public class myWebClient1 extends WebViewClient {

        @Override
        public void	onPageFinished(WebView view, String url){
            super.onPageFinished(view, url);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webViewSearch.canGoBack()){
            webViewSearch.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

