package com.example.nikolina.pokusajstarijaverzija;

import android.graphics.Bitmap;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Login extends AppCompatActivity {
    
    WebView webViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        webViewLogin = (WebView) findViewById(R.id.id_webview_login);
        WebSettings webSettings = webViewLogin.getSettings();
        webSettings.setJavaScriptEnabled(true);

//force links to open in webview only---------------
        webViewLogin.setWebViewClient(new myWebClient3());
//--------------------------------------------------
        webViewLogin.getSettings().setJavaScriptEnabled(true);
        String urlFull = "http://slaviceva40.zapto.org/user/signin";
        webViewLogin.loadUrl(urlFull);

        //improve WebView performance
        webViewLogin.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webViewLogin.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webViewLogin.getSettings().setAppCacheEnabled(true);
        webViewLogin.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
    }

    public class myWebClient3 extends WebViewClient {

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
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webViewLogin.canGoBack()){
            webViewLogin.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
// ---------------------------------- back arrow in menu ---------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu); //this adds items to action bar if it is present
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
// set back button in this activity to go back to main activity --> manifest
            case R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
