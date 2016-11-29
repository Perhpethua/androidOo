package com.example.nikolina.pokusajstarijaverzija;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class OpenPage extends AppCompatActivity {

	String jelikraj, urlsufix;
	WebView webViewOpenPage;
	String one, two, three;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_page);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.mipmap.full_white_logo_m);

		jelikraj = getIntent().getExtras().getString("kraj");
		urlsufix = getIntent().getStringExtra("urlsufix"); // url nastavak
		//---------------- nav location ---------------------------------------------------
		one = getIntent().getExtras().getString("1");
		two = getIntent().getExtras().getString("2");
		three = getIntent().getExtras().getString("3");

		final TextView t3 = (TextView) findViewById(R.id.id_tv);
		final TextView t4 = (TextView) findViewById(R.id.id_tv2); //podkategorije
		final TextView t5 = (TextView) findViewById(R.id.id_tv3); //podkategorije

        t3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.textview_animation));
		t3.setText(" " + one);
        t4.startAnimation(AnimationUtils.loadAnimation(this, R.anim.textview_animation));
		t4.setText(" " + two);
        t5.startAnimation(AnimationUtils.loadAnimation(this, R.anim.textview_animation));
		t5.setText(" " + three);

		//TextView t1 = (TextView)findViewById(R.id.id_prikazi); // for debuging purposes
		webViewOpenPage = (WebView) findViewById(R.id.id_webview_openPage);
		WebSettings webSettings = webViewOpenPage.getSettings();
		webSettings.setJavaScriptEnabled(true);


//force links to open in webview only---------------------------
		webViewOpenPage.setWebViewClient(new myWebClient2());
//--------------------------------------------------------------
		webViewOpenPage.getSettings().setJavaScriptEnabled(true);
		String urlFull = "http://slaviceva40.zapto.org/mob/slug/" + urlsufix;
		//Toast.makeText(getApplicationContext(), urlFull, Toast.LENGTH_LONG).show();
		//t1.setText(urlFull);
		webViewOpenPage.loadUrl(urlFull);

		//improve WebView performance
// force WebView to show content not zoomed---------------------------------------------------------
        webViewOpenPage.getSettings().setLoadWithOverviewMode(true);
        webViewOpenPage.getSettings().setUseWideViewPort(true);
//--------------------------------------------------------------------------------------------------
		webViewOpenPage.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		webViewOpenPage.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webViewOpenPage.getSettings().setAppCacheEnabled(true);
		webViewOpenPage.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webSettings.setDomStorageEnabled(true);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setUseWideViewPort(true);
		webSettings.setSavePassword(true);
		webSettings.setSaveFormData(true);
		webSettings.setEnableSmoothTransition(true);
	}

	public class myWebClient2 extends WebViewClient {

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
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webViewOpenPage.canGoBack()){
			webViewOpenPage.goBack();
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
