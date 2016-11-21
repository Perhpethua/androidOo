package com.example.nikolina.pokusajstarijaverzija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
	String clicked;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_category2);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.mipmap.full_white_logo_m);

		idBotuna = getIntent().getExtras().getString("url");
		clicked = getIntent().getExtras().getString("clickedrow");

		final Button btnnavigate = (Button)findViewById(R.id.id_btn_navigate);
		final TextView t3 = (TextView) findViewById(R.id.id_tv);

		t3.setText("> " + clicked);
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
				String clickedrow = categories.getName();
				String isEnd = categories.getCatEnd().toString();

				if ("null".equals(isEnd)) {
					Intent intent = new Intent(SubCategory2.this, SubCategory3.class);
					intent.putExtra("url", idkat); //id kategorije za url
					intent.putExtra("clickedrow", clickedrow); //ime kliknutog reda stavlja se u btn
					startActivity(intent);
				}else {
					Intent in = new Intent(SubCategory2.this, OpenPage.class);
					in.putExtra("kraj", isEnd);
					startActivity(in);
				}
//--------------------------------------------------------------------------------------------------------------------------
				//	btnnavigate.setText(clicked);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu); //this adds items to action bar if it is present
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

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

			while (count < jsonArray.length()) {
				JSONObject JO = jsonArray.getJSONObject(count);

				idKategorije = JO.getString("id");
				parentId = JO.getString("parent_id");
				name = JO.getString("name");
				urlFromJ = JO.getString("url");
				jelikraj = JO.getString("transaction_type_id");

				Categories categories = new Categories(idKategorije, parentId, name, urlFromJ, jelikraj);
				categoryAdapter.add(categories);
				count++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
