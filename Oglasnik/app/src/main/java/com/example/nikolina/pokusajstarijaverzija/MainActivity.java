package com.example.nikolina.pokusajstarijaverzija;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static String json_string;
    String urlEnd;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.full_white_logo_m);
        actionBar.setTitle("");

        TextView t2 = (TextView) findViewById(R.id.forgot_password_link);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        getJson("1");
    }
    //------------------------------------- end onCreate -------------------------------------------
    public void getJson(String idkategorije){
        new BackgroundTask(idkategorije).execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        private final String idkategorije;
        String json_url;
        String line;

        public BackgroundTask(String idkategorije) {
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

                json_string = stringBuilder.toString().trim();
                return json_string;

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
    }

    //**********************************************************************************************
    public String setUrl(String urlNastavak){
        String json_url;
        json_url = "http://slaviceva40.zapto.org/ajax/jsonCategories/" + urlNastavak;
        urlEnd = urlNastavak;
        return json_url;
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void parseJSON(View view){
        urlEnd = "1";
        if (json_string == null){
            Toast.makeText(getApplicationContext(), "Provjeri Internet vezu.", Toast.LENGTH_SHORT).show();
        }else{
            if (urlEnd.toString().equals("1")) {
                Intent intent = new Intent(this, DisplayListView.class);
                intent.putExtra("json_data", json_string);
                startActivity(intent);
            }/*else {
				Intent intent = new Intent(this, SubCategory.class);
				intent.putExtra("niz", json_string);
				startActivity(intent);
			}*/
        }
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void forgotPassword(View view) {
        //specify an intent --> directs to new activity --> ForgotPassword.class
        Intent startForgotPassActivity = new Intent(this, ForgotPassword.class);
        startActivity(startForgotPassActivity);
    }

    public void registration(View view) {
        Intent registrationActivity = new Intent(this, Registration.class);
        startActivity(registrationActivity);
    }

    public void addfile(View view) {
        Intent addfileActivity = new Intent(this, Addfile.class);
        startActivity(addfileActivity);
    }
    public void login(View view) {
        Intent loginActivity = new Intent(this, Login.class);
        startActivity(loginActivity);
    }

    /* // called when searchView is in aplication "BODY" not actionBar
    public void Searchme(View view) {
        EditText searchInput = (EditText)findViewById(R.id.id_search_input);
        String searchString = searchInput.getText().toString(); //URL nastavak

        Intent searchActivity = new Intent(this, Search.class);
        searchActivity.putExtra("url",searchString);
        startActivity(searchActivity);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu); //this adds items to action bar if it is present
        activateSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void activateSearchView(Menu menu) {
        final MenuItem item = menu.findItem(R.id.id_menuSearch);
        //SearchView sv = (SearchView) menu.findItem(R.id.id_menuSearch).getActionView(); //other way to getActtionView exmpl2 below
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setOnQueryTextListener(
            new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    String idkat = "1";
                    item.collapseActionView();
                    Intent searchActivity = new Intent(MainActivity.this, Search.class);
                    searchActivity.putExtra("query",query);
                    searchActivity.putExtra("idcat", idkat);
                    startActivity(searchActivity);
                    return false;
                }
            }
        );
    }
}

