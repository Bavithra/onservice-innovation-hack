package com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.RecyclerItemClickListener;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.RecyclerViewAdapter;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.SingletonRequestQueue;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.VolleyHelper;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.adapter.NotificationRecyclerView;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.base.BaseActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectUserActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

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
        recyclerView = findViewById(R.id.notification_recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        String[] text = {"Bob has requested for your listing \"Milk\"", "Tina has requested for your listing \"Milk\""};
        String[] button = {"Accept", "Rate"};
        NotificationRecyclerView adapter = new NotificationRecyclerView(text,button,getApplicationContext());
        recyclerView.setAdapter(adapter);

        RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        final List<Item> itemList = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest("http://192.168.1.169:3000/api/goods/Bob",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Item item = new Item();
                                item.setId(String.valueOf(jsonObject.get("_id")));
                                item.setName(String.valueOf(jsonObject.get("name")));
                                item.setDetails((String) jsonObject.get("details"));
                                if (jsonObject.get("pic") != null) {
                                    // item.setPic((String) jsonObject.get("pic"));
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



}
