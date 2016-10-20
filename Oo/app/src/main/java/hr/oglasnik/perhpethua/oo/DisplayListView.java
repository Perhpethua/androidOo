package hr.oglasnik.perhpethua.oo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class DisplayListView extends AppCompatActivity {

	String niz;
	JSONArray jsonArray;
	CategoryAdapter categoryAdapter;
	ListView listView;
	String prosljedeni;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_list_view);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.mipmap.full_logo);

		TextView t3 = (TextView) findViewById(R.id.id_tv);

		niz = getIntent().getExtras().getString("json_data");

		listView = (ListView) findViewById(R.id.listview);
		categoryAdapter = new CategoryAdapter(this, R.layout.row_layout);
		listView.setAdapter(categoryAdapter);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){

				Categories categories = (Categories) categoryAdapter.getItem(position);
				String idkat = categories.getId(); //dohvati id imena reda
				String clickedrow = categories.getName();

				//MainActivity mm = new MainActivity();
				//mm.new BackgroundTask(idkat);


				//new JSONTask().execute("http://slaviceva40.zapto.org/ajax/jsonCategories/"+idkat);

				//listView.invalidateViews();
				Intent intent = new Intent(DisplayListView.this, SubCategory.class);
				intent.putExtra("url",idkat);
				intent.putExtra("clickedrow", clickedrow);
				intent.putExtra("niz", niz);
				startActivity(intent);
			}
		});

		//json_string = getIntent().getExtras().getString("json_data");
		//inicjalizacija jsonObject
		try {
			//jsonObject = new JSONObject(json_string); //starts with [ not {
			jsonArray = new JSONArray(niz); //get the array
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
		categoryAdapter.notifyDataSetChanged();
	}
}
