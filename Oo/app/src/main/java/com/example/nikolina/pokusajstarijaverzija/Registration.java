package com.example.nikolina.pokusajstarijaverzija;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    // ---------------------------------- back arrow in menu ---------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu); //this adds items to action bar if it is present
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
        final MenuItem item = menu.findItem(R.id.id_menuSearch);
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
                        item.collapseActionView();
                        Intent searchActivity = new Intent(Registration.this, Search.class);
                        searchActivity.putExtra("url",query);
                        startActivity(searchActivity);
                        return false;
                    }
                }
        );
    }
}
