package hr.oglasnik.perhpethua.oo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
		actionBar.setIcon(R.mipmap.full_logo);

		idBotuna = getIntent().getExtras().getString("url");
		clicked = getIntent().getExtras().getString("clickedrow");

		TextView t3 = (TextView) findViewById(R.id.id_tv);
		t3.setText(" >" + clicked);

		MainActivity m = new MainActivity();
		//m.getJson(idBotuna);
		m.new BackgroundTask(idBotuna).execute();
		String niz = m.json_string;

		listView = (ListView) findViewById(R.id.id_listview_sub);
		categoryAdapter = new CategoryAdapter(this, R.layout.row_layout);
		listView.setAdapter(categoryAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(SubCategory.this, "List item clicked at " + position, Toast.LENGTH_SHORT).show();
			}
		});

		try {
			//jsonObject = new JSONObject(json_string); //starts with [ not {
			jsonArray = new JSONArray(niz); //get the array
			int count = 0;
			String name;
			String urlFromJ;
			String parentId;
			String idKategorije;

			while (count < jsonArray.length()) {
				JSONObject JO = jsonArray.getJSONObject(count);

				idKategorije = JO.getString("id");
				parentId = JO.getString("parent_id");
				name = JO.getString("name");
				urlFromJ = JO.getString("url");

				Categories categories = new Categories(idKategorije, parentId, name, urlFromJ);
				categoryAdapter.add(categories);
				count++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
/*
	public String setUrl(String urlNastavak){
		String json_url;
		json_url = "http://slaviceva40.zapto.org/ajax/jsonCategories/" + urlNastavak;
		return json_url;
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public void getJson(String idkategorije){
		new BackgroundTask1(idkategorije).execute();
	}

	class BackgroundTask1 extends AsyncTask<Void, Void, String> {

		private final String idkategorije;
		String json_url;
		String line;

		public BackgroundTask1(String idkategorije) {
			this.idkategorije = idkategorije;
		}

		@Override
		protected void onPreExecute() {
			json_url = setUrl(idkategorije);
		}

		@Override
		protected String doInBackground(Void... voids) {
			try{
				URL url = new URL(json_url);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.connect();

				InputStream inputStream = httpURLConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				StringBuilder stringBuilder = new StringBuilder();
				while ((line = bufferedReader.readLine()) != null){
					stringBuilder.append(line);
				}
				bufferedReader.close();
				inputStream.close();
				httpURLConnection.disconnect();

				return stringBuilder.toString().trim();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			json_string = result;
		}
	}*/
}
