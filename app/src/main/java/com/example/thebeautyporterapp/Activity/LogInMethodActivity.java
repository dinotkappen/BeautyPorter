package com.example.thebeautyporterapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.thebeautyporterapp.R;

public class LogInMethodActivity extends AppCompatActivity {
LinearLayout linearUser,linearVendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in_method);
        linearUser=(LinearLayout)findViewById(R.id.linearUser);
        linearVendor=(LinearLayout)findViewById(R.id.linearVendor);
        linearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogInMethodActivity.this,LogInActivity.class);
                startActivity(intent);


            }
        });
        linearVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogInMethodActivity.this, VendorActivity.class);
                startActivity(intent);



            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
       // System.exit(0);
        // your code.
    }
}
