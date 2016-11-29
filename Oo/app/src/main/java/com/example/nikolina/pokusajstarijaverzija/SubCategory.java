package com.example.nikolina.pokusajstarijaverzija;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SubCategory extends AppCompatActivity {

	String json_string;
	JSONArray jsonArray;
	CategoryAdapter categoryAdapter;
	ListView listView;
	String idBotuna;
	String clicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_category);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.mipmap.full_white_logo_m);

		idBotuna = getIntent().getExtras().getString("url");
		clicked = getIntent().getExtras().getString("clickedrow");

	//	final Button btnnavigate = (Button)findViewById(R.id.id_btn_navigate);
		final TextView t3 = (TextView) findViewById(R.id.id_tv);

		/*btnnavigate.setOnClickListener(new AdapterView.OnClickListener(){

			@Override
			public void onClick(View v) {
				if (btnnavigate.getText().toString().equals("Kategorije")){

				}
				Toast.makeText(SubCategory.this, "Botun kliknut" + idBotuna, Toast.LENGTH_SHORT).show(); //radi
				String urlbtn = "http://slaviceva40.zapto.org/ajax/jsonCategories/" + idBotuna;
				new JSONTask().execute(urlbtn);
				listView = (ListView) findViewById(R.id.id_listview_sub);
				categoryAdapter = new CategoryAdapter(getBaseContext(), R.layout.row_layout);
			}
		});*/
		t3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.textview_animation));
		t3.setText("> " + clicked);
		String fullurl = "http://slaviceva40.zapto.org/ajax/jsonCategories/" + idBotuna;
		new JSONTask().execute(fullurl);

		listView = (ListView) findViewById(R.id.id_listview_sub);
		categoryAdapter = new CategoryAdapter(this, R.layout.row_layout);


		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(SubCategory.this, "List item clicked at " + position, Toast.LENGTH_SHORT).show();
				Categories categories = (Categories) categoryAdapter.getItem(position);
				String idkat = categories.getId();
				String clickedrow2 = categories.getName();
				String isEnd = categories.getCatEnd().toString();

				if ("null".equals(isEnd)) {
					Intent intent = new Intent(SubCategory.this, SubCategory2.class);
					intent.putExtra("url", idkat); //id kategorije za url
                    intent.putExtra("clicked", clicked); // prije klinuti
                    intent.putExtra("clickedrow2", clickedrow2); // zadnji kliknuti - ime kliknutog reda stavlja se u btn
					startActivity(intent);
				}else {
					Intent in = new Intent(SubCategory.this, OpenPage.class);
					in.putExtra("kraj", isEnd);
					startActivity(in);
				}
//--------------------------------------------------------------------------------------------------------------------------
			//	btnnavigate.setText(clicked);
			}
		});
	}
public class JSONTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... urls) {

			//spajanje na server
			HttpURLConnection connection = null;
			BufferedReader bufferedReader = null; //inicjalizacija da bi se vidio u finally bloku
			//url
			try {
				URL url = new URL(urls[0]); //new URL(urls[0]); gleda prvi ulazni vrijednost od AsyncTask i doInBackground
				connection = (HttpURLConnection) url.openConnection();//otvaranje veze po zadanom url
				connection.connect();//spajanje

				//spremanje
				InputStream inputStream = connection.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				String line="";
				StringBuffer stringBuffer = new StringBuffer();
				while ((line = bufferedReader.readLine()) != null){
					stringBuffer.append(line);
				}
//----------------------------------parsingJSON----------------------------------------------------------------------------
				String finalJSON = stringBuffer.toString();
				json_string = finalJSON;
//----------------------------------KREIRANJE OBJEKATA I NIZOVA------------------------------------------------------------
				parseJson(finalJSON);
//------------------------------------endParsingJson------------------------------------------------------------------------
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {//provjera da je veza uspostavljena
					connection.disconnect();
				}
				if (bufferedReader != null) {//ako je nula--> trebalo bi ga inicjalizirat -->nije potrebno zatvarati
					try {
						bufferedReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {//izvodi se u main thread
			super.onPostExecute(result);
			listView.setAdapter(categoryAdapter);
		}
	}
	private String parseJson(String finalJSON){
		try {
			//jsonObject = new JSONObject(json_string); //starts with [ not {
			jsonArray = new JSONArray(finalJSON); //get the array
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
		//ispis
		return finalJSON; // da bi ispisao podatke -> šalje se u onPostExecute
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
						Intent searchActivity = new Intent(SubCategory.this, Search.class);
						searchActivity.putExtra("url",query);
						startActivity(searchActivity);
						return false;
					}
				}
		);
	}
}
