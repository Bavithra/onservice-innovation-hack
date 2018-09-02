package com.example.bavithrathangaraj.oneserviceinnovationhackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.VolleyHelper;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity.CreateProductActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity.ProfileActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity.SelectUserActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.base.BaseActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.SingletonRequestQueue;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemLandingActivity extends BaseActivity {

    RecyclerView recyclerView;
    private static final String BASE_URL = "http://192.168.1.169:3000/api/goods/Bob";
    RecyclerViewAdapter adapter;
    ImageView add;
    ImageView explore;
    ImageView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_landing);

        ImageView profile = (ImageView) findViewById(R.id.profile);
        ImageView notification = (ImageView) findViewById(R.id.notification);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SelectUserActivity.class);
                startActivity(intent);
            }
        });
        add = (ImageView) findViewById(R.id.addPage);
        explore = (ImageView) findViewById(R.id.explore);
        message = (ImageView) findViewById(R.id.message);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateProductActivity.class);
                startActivity(intent);
            }
        });
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ItemLandingActivity.class);
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        VolleyHelper.requestLoginDetails(getApplicationContext(), new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {

            }
        });

        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        final List<Item> itemList = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest(BASE_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Item item = new Item();
                                item.setName(String.valueOf(jsonObject.get("name")));
                                item.setDetails((String) jsonObject.get("details"));
                                if (jsonObject.get("pic") != null) {
                                    item.setPic((String) jsonObject.get("pic"));
                                }
                                itemList.add(item);
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor prefsEditor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(item);
                                prefsEditor.putString("item", json);
                                prefsEditor.apply();
                            } catch (JSONException e) {
                            }
                        }
                        adapter = new RecyclerViewAdapter(itemList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(req);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (adapter != null) adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}

