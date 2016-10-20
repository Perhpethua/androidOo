/*package hr.oglasnik.perhpethua.oo;

import android.os.AsyncTask;
import android.widget.ListView;

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

/**
 * Created by Nikolina on 20.10.2016..
 */

/*public class JSONTask extends AsyncTask<String, Void, String> {

	ListView listView;
	public static String json_string;
	//String finalJSON;
	//CategoryAdapter categoryAdapter;
	JSONArray jsonArray;
	String finalJSON;
	CategoryAdapter categoryAdapter;


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

				String line = "";
				StringBuffer stringBuffer = new StringBuffer();
				while ((line = bufferedReader.readLine()) != null) {
					stringBuffer.append(line);
				}
//++++++++++++++++++++++++++++++ parsing json ++++++++++++++++++++++++++++++++++++++++++++++++++++++
				finalJSON = stringBuffer.toString();

				parseJson();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String s) {
			//super.onPostExecute(s);
			listView.setAdapter(categoryAdapter);
			json_string = s;
		}

	private void parseJson(){
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
	}
}*/