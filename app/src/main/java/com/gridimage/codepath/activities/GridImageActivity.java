package com.gridimage.codepath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.gridimage.codepath.adapters.ImageResultsAdapter;
import com.gridimage.codepath.models.ImageResult;
import com.gridimage.codepath.models.SearchOpts;
import com.gridimage.codepath.net.GoogleClient;
import com.gridimage.codepath.util.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GridImageActivity extends AppCompatActivity {
    private GridView gvResults;
    private List<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private SearchOpts searchOpts;
    private String query;
    private EndlessScrollListener endlessScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_image);
        gvResults = (GridView) findViewById(R.id.gvResults);
        imageResults = new ArrayList<>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GridImageActivity.this, ImageDisplayActivity.class);
                intent.putExtra("imageResult", imageResults.get(position));
                startActivity(intent);
            }
        });
        // Attach the listener to the AdapterView onCreate
        endlessScrollListener = new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page-1);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        };
        gvResults.setOnScrollListener(endlessScrollListener);
    }

    private void customLoadMoreDataFromApi(int page) {
        if (searchOpts == null) {
            searchOpts = new SearchOpts();
        }
        searchOpts.startRow = page*8;
        Log.d("DEBUG", "Scroll - Fetching with startRow = " + searchOpts.startRow);
        fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(GridImageActivity.this, "Searching with query : " + query, Toast.LENGTH_SHORT).show();
                GridImageActivity.this.query = query;
                if (searchOpts != null)
                    searchOpts.startRow = 0;
                // perform query here
                aImageResults.clear();
                Log.d("DEBUG", "Query - Fetching with query = " + query);
                fetchData();
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        MenuItem advSearchMenu = menu.findItem(R.id.mnuAdvanced);
        advSearchMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(GridImageActivity.this, AdvancedSearchActivity.class);
                if (searchOpts != null)
                    intent.putExtra("searchOpts", searchOpts);
                startActivityForResult(intent, 20);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void fetchData() {
        if (query == null) return;
        new GoogleClient().getImages(query, searchOpts, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONObject("responseData").getJSONArray("results");
                    aImageResults.addAll(ImageResult.fromJson(jsonArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(GridImageActivity.this, "Error fetching results", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(GridImageActivity.this, "Error : " + statusCode + " : " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", "Error : " + statusCode + " : " + errorResponse);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 20) {
            searchOpts = (SearchOpts) data.getSerializableExtra("searchOpts");
        }
        super.onActivityResult(requestCode, resultCode, data);
        aImageResults.clear();
        Log.d("DEBUG", "ActivityResult - Fetching with startRow = " + searchOpts.startRow);
        fetchData();
    }
}
