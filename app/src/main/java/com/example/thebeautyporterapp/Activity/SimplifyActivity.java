package com.example.thebeautyporterapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thebeautyporterapp.R;
import com.simplify.android.sdk.Simplify;

public class SimplifyActivity extends AppCompatActivity {
    Simplify simplify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplify);
    }
}
