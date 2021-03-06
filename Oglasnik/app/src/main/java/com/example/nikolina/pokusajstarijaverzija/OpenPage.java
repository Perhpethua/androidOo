package com.example.nikolina.pokusajstarijaverzija;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OpenPage extends AppCompatActivity {

	String idcat, urlsufix;
	WebView webViewOpenPage;
	String one, two, three;
	private Spinner spinner;
	Button nav_btn_back, nav_btn_1, nav_btn_2, nav_btn_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_page);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.mipmap.full_white_logo_m);

		addItemsOnSpinner();
//--------------------------------------------------------------------------------------------------
		idcat = getIntent().getExtras().getString("idcat");
		urlsufix = getIntent().getStringExtra("urlsufix"); // url nastavak
		//---------------- nav location ------------------------------------------------------------
		one = getIntent().getExtras().getString("1");
		two = getIntent().getExtras().getString("2");
		three = getIntent().getExtras().getString("3");
//--------------------------------------------------------------------------------------------------
		final TextView t3 = (TextView) findViewById(R.id.id_tv);
		final TextView t4 = (TextView) findViewById(R.id.id_tv2); //podkategorije
		final TextView t5 = (TextView) findViewById(R.id.id_tv3); //podkategorije
//--------------------------------------------------------------------------------------------------
		nav_btn_back = (Button) findViewById(R.id.id_btn_nav_1);
		nav_btn_1 = (Button) findViewById(R.id.id_btn_nav_2);
		nav_btn_2 = (Button) findViewById(R.id.id_btn_nav_3);
		nav_btn_next = (Button) findViewById(R.id.id_btn_nav_5);

		setButtonListeners();

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
		//"http://slaviceva40.zapto.org/mob/slug/" + urlsufix;
		//http://slaviceva40.zapto.org/mob?q=&category_id=2
		String urlFull = "http://slaviceva40.zapto.org/mob?q=&category_id=" + idcat;
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

	private void setButtonListeners() {
// ----------------- BACK btn ----------------------------------------------------------------------
		nav_btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                //PROČITATI VRIJEDNOST AKTIVNOG BOTUNA - UMANJITI JE ZA 1
                //DOBIVENA VRIJEDNOST -- U VARIJABLU VA
                //VA SE KORISTI ZA KREIRANJE LINKA KOJI SE POZIVA UNUTAR ISTOG WEBVIEW
			}
		});
// ----------------- PAGE 1 btn --------------------------------------------------------------------
		nav_btn_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                nav_btn_1.setText("3");
                nav_btn_2.setText("4");

                //webViewOpenPage.loadUrl(myCustomUrl); //radi
			}
		});
// ----------------- PAGE 2 btn --------------------------------------------------------------------
		nav_btn_2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                //webViewOpenPage.loadUrl(myCustomUrl); //radi
			}
		});
// ----------------- NEXT btn ----------------------------------------------------------------------
		nav_btn_next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                //PROČITATI VRIJEDNOST AKTIVNOG BOTUNA - UVEČATI JE ZA 1
                //DOBIVENA VRIJEDNOST -- U VARIJABLU VA
                //VA SE KORISTI ZA KREIRANJE LINKA KOJI SE POZIVA UNUTAR ISTOG WEBVIEW


                //webViewOpenPage.loadUrl(myCustomUrl); //radi
			}
		});
	}
//--------------------------------------------------------------------------------------------------
//							SPINNER
//--------------------------------------------------------------------------------------------------
	private void addItemsOnSpinner() {
		//spinner = (Spinner) findViewById(R.id.id_spinner);
		List<String> list = new ArrayList<String>();
		list.add("Cijena - najniža");
		list.add("Cijena - najviša");
		list.add("Datum objave - najstariji");
		list.add("Datum objave - najnoviji");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//spinner.setAdapter(dataAdapter);
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


// ---------------------------------- back arrow in menu -------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu); //this adds items to action bar if it is present
		activateSearchView(menu);
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
		MenuItem item = menu.findItem(R.id.id_menuSearch);
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
						Intent searchActivity = new Intent(OpenPage.this, Search.class);
						searchActivity.putExtra("query",query);
						searchActivity.putExtra("idcat", idcat);
						startActivity(searchActivity);
						return false;
					}
				}
		);
	}
}
