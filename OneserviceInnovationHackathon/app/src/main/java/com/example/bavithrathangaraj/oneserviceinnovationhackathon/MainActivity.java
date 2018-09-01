package com.example.bavithrathangaraj.oneserviceinnovationhackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.VolleyHelper;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VolleyHelper.GETStringAndJSONRequest(getApplicationContext());
    }
}
