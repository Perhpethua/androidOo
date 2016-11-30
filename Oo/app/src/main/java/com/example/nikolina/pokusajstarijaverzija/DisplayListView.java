package com.example.nikolina.pokusajstarijaverzija;
/*
        TODO: First ListView categories display, parsing JSON
 */

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity {

    String niz;
    JSONArray jsonArray;
    CategoryAdapter categoryAdapter;
    ListView listView;
    String jsonstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.full_white_logo_m);

        TextView t3 = (TextView) findViewById(R.id.id_tv);
        ImageView icons = (ImageView) findViewById(R.id.id_icon);

        niz = getIntent().getExtras().getString("json_data");

        listView = (ListView) findViewById(R.id.listview);
        categoryAdapter = new CategoryAdapter(this, R.layout.row_layout);

        listView.setAdapter(categoryAdapter);

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Categories categories = (Categories) categoryAdapter.getItem(position);
                String idkat = categories.getId(); //dohvati id imena reda
                String clickedrow = categories.getName();
                //String jsonicon = categories.getJsonicon();
                //new JSONTask().execute("http://slaviceva40.zapto.org/ajax/jsonCategories/"+idkat);

                Intent intent = new Intent(DisplayListView.this, SubCategory.class);
                intent.putExtra("idkat", idkat);
                intent.putExtra("clickedrow", clickedrow);
                //intent.putExtra("iconposition", jsonicon); // all rows will have this icon in SubCategory
                startActivity(intent);
            }
        });

        String json = null;
        try {
            //jsonObject = new JSONObject(json_string); //starts with [ not {
            jsonArray = new JSONArray(niz); //get the array
            int count = 0;
            String name;
            String urlFromJ;
            String parentId;
            String idKategorije;
            String jelikraj; //ako nije null onda neka otvori web wiew sa urlFromJ nastavkom
            String urlsufix;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);

                idKategorije = JO.getString("id");
                parentId = JO.getString("parent_id");
                name = JO.getString("name");
                urlFromJ = JO.getString("url");
                jelikraj = JO.getString("transaction_type_id");
                urlsufix = JO.getString("url");
                json = JO.getString("json"); // bit će isti za odabranu kategoriju, tj null za podkategoriju

                Categories categories = new Categories(idKategorije, parentId, name, urlFromJ, jelikraj, urlsufix, json);
                categoryAdapter.add(categories);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//--------------------------------------------------------------------------------------------------
//------------------------------- parse json string in json file -----------------------------------
//--------------------------------------------------------------------------------------------------
       /*
        String[] separated = json.split(",");
        Categories categories = (Categories) categoryAdapter.getItem(0);
        jsonstring = categories.getJsonicon();
        Toast.makeText(getApplicationContext(),jsonstring, Toast.LENGTH_LONG).show(); // za dobivanje ikone na 6 poziciji u JSON file
        */
//--------------------------------------------------------------------------------------------------
        categoryAdapter.notifyDataSetChanged();
    }

//--------------------------------------------------------------------------------------------------
// ---------------------------------- back arrow in ActionMenu -------------------------------------
//--------------------------------------------------------------------------------------------------
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
//--------------------------------------------------------------------------------------------------
//---------------------------------------- SEARCH --------------------------------------------------
//--------------------------------------------------------------------------------------------------
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
                        String idkat = "1";
                        Intent searchActivity = new Intent(DisplayListView.this, Search.class);
                        searchActivity.putExtra("query",query);
                        searchActivity.putExtra("idcat", idkat);
                        startActivity(searchActivity);
                        return false;
                    }
                }
        );
    }
}
