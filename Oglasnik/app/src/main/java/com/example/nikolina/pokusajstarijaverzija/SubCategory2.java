package com.example.nikolina.pokusajstarijaverzija;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubCategory2 extends AppCompatActivity implements AsyncResponse{

	JSONArray jsonArray;
	CategoryAdapter categoryAdapter;
	ListView listView;
	String idBotuna;
	String clicked, clicked2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_category2);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.mipmap.full_white_logo_m);

		idBotuna = getIntent().getExtras().getString("idcat");
		clicked = getIntent().getExtras().getString("clicked");
		clicked2 = getIntent().getExtras().getString("clickedrow2"); // zadnji

		//Toast.makeText(getApplicationContext(), idBotuna, Toast.LENGTH_SHORT).show();// works

		//final Button btnnavigate = (Button)findViewById(R.id.id_btn_navigate);
		final TextView t3 = (TextView) findViewById(R.id.id_tv);
		final TextView t4 = (TextView) findViewById(R.id.id_tv2); //podkategorije

		t3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.textview_animation));
		t3.setText(" > " + clicked);
		t4.startAnimation(AnimationUtils.loadAnimation(this, R.anim.textview_animation));
		t4.setText(" > " + clicked2);

		String fullurl = "http://slaviceva40.zapto.org/ajax/jsonCategories/" + idBotuna;

		noviJsonTask asyncTask = new noviJsonTask();
		asyncTask.delegate = this;
		asyncTask.execute(fullurl);

		listView = (ListView) findViewById(R.id.id_listview_sub2);
		categoryAdapter = new CategoryAdapter(this, R.layout.row_layout);
		listView.setAdapter(categoryAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(SubCategory.this, "List item clicked at " + position, Toast.LENGTH_SHORT).show();
				Categories categories = (Categories) categoryAdapter.getItem(position);
				String idkat = categories.getId();
				String clickedrow3 = categories.getName();
				String isEnd = categories.getCatEnd();
				String urlsufix = categories.getUrlsufix();

				String clicked3 = "";

				if ("null".equals(isEnd)) { // nije kraj ima jos lista
					Intent intent = new Intent(SubCategory2.this, SubCategory3.class);
					intent.putExtra("idcat", idkat); //id kategorije za url
					intent.putExtra("clicked", clicked); // prije klinuti
					intent.putExtra("clicked2", clicked2); // prije klinuti
					intent.putExtra("clickedrow3", clickedrow3); // zadnji kliknuti - ime kliknutog reda stavlja se u btn
					startActivity(intent);
				}else {
					Intent in = new Intent(SubCategory2.this, OpenPage.class);
					in.putExtra("1", clicked);
					in.putExtra("2", clicked2);
					in.putExtra("3", clickedrow3);
					in.putExtra("idcat", idkat);
					in.putExtra("urlsufix", urlsufix);
					startActivity(in);
				}
//--------------------------------------------------------------------------------------------------------------------------
				//	btnnavigate.setText(clicked);
			}
		});

	}

	// ---------------------------------- back arrow in menu ---------------------------------------------
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

	@Override
	public void processFinish(String output) {
		try {
			//jsonObject = new JSONObject(json_string); //starts with [ not {
			jsonArray = new JSONArray(output);
			int count = 0;
			String name;
			String urlFromJ;
			String parentId;
			String idKategorije;
			String jelikraj; //ako nije null onda neka otvori web wiew sa urlFromJ nastavkom
			String urlsufix;
			String json;

			while (count < jsonArray.length()) {
				JSONObject JO = jsonArray.getJSONObject(count);

				idKategorije = JO.getString("id");
				parentId = JO.getString("parent_id");
				name = JO.getString("name");
				urlFromJ = JO.getString("url");
				jelikraj = JO.getString("transaction_type_id");
				urlsufix = JO.getString("url");
				json = JO.getString("json");

				Categories categories = new Categories(idKategorije, parentId, name, urlFromJ, jelikraj, urlsufix, json);
				categoryAdapter.add(categories);
				count++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
						Intent searchActivity = new Intent(SubCategory2.this, Search.class);
						searchActivity.putExtra("query",query);
						searchActivity.putExtra("idcat", idBotuna);
						startActivity(searchActivity);
						return false;
					}
				}
		);
	}
}
