package com.example.bavithrathangaraj.oneserviceinnovationhackathon.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity.ProfileActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity.SelectUserActivity;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

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
    }
}
