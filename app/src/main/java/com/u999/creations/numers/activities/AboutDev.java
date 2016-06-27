package com.u999.creations.numers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.u999.creations.numers.R;

public class AboutDev extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dev);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
