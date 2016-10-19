package hr.oglasnik.perhpethua.oo;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class category extends AppCompatActivity {
	//field must be declared here not in JSONTask - can cause crash
	private TextView dataView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setIcon(R.mipmap.full_logo);

		dataView = (TextView)findViewById(R.id.categoryTextView);
		new JSONTask().execute("https://raw.githubusercontent.com/Perhpethua/json/32a18f3e64bb6c70871e51a5b8f822b314de2fea/jsonCategories");

	}
	private class JSONTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... urls) {

			BufferedReader bufferedReader = null;
			HttpURLConnection httpURLConnection = null;
			try {
				URL url = new URL(urls[0]);//sees 1st value of AsyncTastk and doInBackground
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.connect();

				InputStream inputStream = httpURLConnection.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				String line = "";
				StringBuffer buffer = new StringBuffer();
				while ((line = bufferedReader.readLine()) != null) {
					buffer.append(line);
				}
				String finalJSON = buffer.toString();
				JSONArray parentArray = new JSONArray(finalJSON); //prva zarada je uglata (array)

				StringBuffer finalBufferedData = new StringBuffer();
				for (int i = 0; i < parentArray.length(); i++) {
					JSONObject finalObject = parentArray.getJSONObject(i);
					String categoryName = finalObject.getString("name");
					finalBufferedData.append(categoryName + "\n");
				}
				return finalBufferedData.toString();
				//return buffer.toString();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				if (httpURLConnection != null) {
					httpURLConnection.disconnect();
				}
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dataView.setText(result);
		}
	}
}

