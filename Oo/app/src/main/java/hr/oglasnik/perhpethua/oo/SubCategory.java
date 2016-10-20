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

		final TextView t3 = (TextView) findViewById(R.id.id_tv);
		t3.setText(" >" + clicked);
		String fullurl = "http://slaviceva40.zapto.org/ajax/jsonCategories/" + idBotuna;
		new JSONTask().execute(fullurl);

		listView = (ListView) findViewById(R.id.id_listview_sub);
		categoryAdapter = new CategoryAdapter(this, R.layout.row_layout);


		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(SubCategory.this, "List item clicked at " + position, Toast.LENGTH_SHORT).show();
				Categories categories = (Categories) categoryAdapter.getItem(position);
				String ides = categories.getId();
				String clickedrow = categories.getName();
				
					String urls = "http://slaviceva40.zapto.org/ajax/jsonCategories/" + ides;
					new JSONTask().execute(urls);
					listView = (ListView) findViewById(R.id.id_listview_sub);
					categoryAdapter = new CategoryAdapter(getBaseContext(), R.layout.row_layout);
					t3.setText(" >" + clickedrow);
				/*String isEnd = categories.getCatEnd();
				if (isEnd != null){
				 	Intent in = new Intent(SubCategory.this, OpenPage.class);
					startActivity(in);
				}*/
			}
		});
	}
	private class JSONTask extends AsyncTask<String, String, String>{

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
//----------------------------------KREIRANJE OBJEKATA I NIZOVA------------------------------------------------------------
				try {
					//jsonObject = new JSONObject(json_string); //starts with [ not {
					jsonArray = new JSONArray(finalJSON); //get the array
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
				//ispis
				return finalJSON; // da bi ispisao podatke -> šalje se u onPostExecute
//------------------------------------endParsingJson------------------------------------------------------------------------
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {//provjera da je veza uspostavljena
					connection.disconnect();
				}
				if (bufferedReader != null) {//ako je nula--> trebalo bi ga inicjalizirat -->nijw potrebno zatvarati
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
}