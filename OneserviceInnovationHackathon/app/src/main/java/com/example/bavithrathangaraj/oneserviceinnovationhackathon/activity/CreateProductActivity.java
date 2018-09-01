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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerCallback;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.VolleyHelper;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;

public class CreateProductActivity extends AppCompatActivity implements View.OnClickListener{

    EditText description;
    EditText text;
    ImageView submit;
    Button home;
    Button givingout;
    Item item = new Item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        description = (EditText) findViewById(R.id.description);
        text = (EditText) findViewById(R.id.title);
        submit = (ImageView) findViewById(R.id.submit);
        home = (Button) findViewById(R.id.home);
        givingout = (Button) findViewById(R.id.givingout);
        submit.setOnClickListener(this);
        home.setOnClickListener(this);
        givingout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.givingout:
                item.setType("Giving out");
                item.setStatus("available");
                break;
            case R.id.looking_for:
                item.setType("Looking for");
                item.setStatus("available");
                break;
            case R.id.home:
                item.setCategory("Home");
                break;
            case R.id.lifestyle:
                item.setCategory("Lifestyle");
                break;
            case R.id.perishable:
                item.setCategory("Perishable");
                break;
            case R.id.elctronics:
                item.setCategory("Electronics");
                break;
            case R.id.submit:
                item.setName(text.getText().toString());
                item.setDetails(description.getText().toString());
                item.setDatePosted("02/09/2019");
                item.setLocation("Clementi");
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.cat);

                VolleyHelper.POSTStringAndJSONRequest(getApplicationContext(), item, icon);

                break;
        }
    }
}
