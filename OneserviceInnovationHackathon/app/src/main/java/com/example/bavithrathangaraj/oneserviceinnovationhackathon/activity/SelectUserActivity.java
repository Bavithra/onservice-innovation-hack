package com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.RecyclerViewAdapter;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.adapter.NotificationRecyclerView;

public class SelectUserActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        String[] text = {"Bob has requested for your listing \"Milk\"", "Tina has requested for your listing \"Milk\""};
        String[] button = {"Accept", "Rate"};
        NotificationRecyclerView adapter = new NotificationRecyclerView(text,button,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

}
