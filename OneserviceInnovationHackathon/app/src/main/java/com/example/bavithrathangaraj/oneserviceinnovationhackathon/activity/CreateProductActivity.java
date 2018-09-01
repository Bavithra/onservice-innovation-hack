package com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerCallback;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.VolleyHelper;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;

public class CreateProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        VolleyHelper.requestLoginDetails(getApplicationContext(), new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                if(result != null) {
                    Gson gson = new Gson();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String json = prefs.getString("user", "");
                    User obj = gson.fromJson(json, User.class);
                    Item item = new Item();
                    item.setName(obj.getName());
                    item.setDetails("Item details here");
                    item.setDatePosted("01/09/2019");
                    item.setLocation("Clementi");
                    Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.cat);

                    VolleyHelper.POSTStringAndJSONRequest(getApplicationContext(), item, icon);
                }

            }
        });
    }

}
