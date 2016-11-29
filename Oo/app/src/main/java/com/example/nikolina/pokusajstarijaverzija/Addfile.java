package com.example.nikolina.pokusajstarijaverzija;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Addfile extends AppCompatActivity {

    WebView webViewAddFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfile);

        webViewAddFile = (WebView) findViewById(R.id.id_webview_addFile);
        WebSettings webSettings = webViewAddFile.getSettings();
        webSettings.setJavaScriptEnabled(true);

//force links to open in webview only---------------
        webViewAddFile.setWebViewClient(new myWebClient3());
//--------------------------------------------------
        webViewAddFile.getSettings().setJavaScriptEnabled(true);
        String urlFull = "http://slaviceva40.zapto.org/predaja-oglasa";
        webViewAddFile.loadUrl(urlFull);

        //improve WebView performance
        webViewAddFile.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webViewAddFile.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webViewAddFile.getSettings().setAppCacheEnabled(true);
        webViewAddFile.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
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
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webViewAddFile.canGoBack()){
            webViewAddFile.goBack();
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
    private void activateSearchView(Menu menu) {
        final MenuItem item = menu.findItem(R.id.id_menuSearch);
        //SearchView sv = (SearchView) menu.findItem(R.id.id_menuSearch).getActionView(); //other way to getActtionView exmpl2 below
         SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        item.collapseActionView();
                        Intent searchActivity = new Intent(Addfile.this, Search.class);
                        searchActivity.putExtra("url",query);
                        startActivity(searchActivity);
                        return false;
                    }
                }
        );
    }
}
