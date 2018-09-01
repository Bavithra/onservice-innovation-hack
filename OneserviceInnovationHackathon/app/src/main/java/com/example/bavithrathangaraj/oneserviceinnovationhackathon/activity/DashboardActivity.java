package com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.base.BaseSingleFragmentActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.fragment.DashboardFragment;

public class DashboardActivity extends BaseSingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    protected Fragment createFragment() {
        return new DashboardFragment();
    }
}
