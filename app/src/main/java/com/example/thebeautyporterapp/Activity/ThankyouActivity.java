package com.example.thebeautyporterapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.thebeautyporterapp.R;

public class ThankyouActivity extends AppCompatActivity {
    CardView btnCardThankyou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_thankyou);
        btnCardThankyou=(CardView) findViewById(R.id.btnCardThankyou);
        btnCardThankyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ThankyouActivity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent in=new Intent(ThankyouActivity.this,MainActivity.class);
        startActivity(in);
        finish();
        // your code.
    }
}
