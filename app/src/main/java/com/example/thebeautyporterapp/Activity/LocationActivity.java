package com.example.thebeautyporterapp.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

public class LocationActivity extends AppCompatActivity {
int locationFlagFirst=0;
CardView btnEnableLocation;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    TextView txtDoItLater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();

        locationFlagFirst=Hawk.get("locationFlagFirst",0);
        if(locationFlagFirst==1)
        {
            Intent in =new Intent(LocationActivity.this,SplashActivity.class);
            startActivity(in);
            Hawk.put("locationFlagFirst",1);
        }
        else
        {
            setContentView(R.layout.activity_location);
            btnEnableLocation=(CardView)findViewById(R.id.btnEnableLocation);
            txtDoItLater=(TextView)findViewById(R.id.txtDoItLater);
            permissionStatus =getSharedPreferences("permissionStatus", MODE_PRIVATE);
            btnEnableLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();

                    Hawk.put("locationFlagFirst",1);
                }
            });
            txtDoItLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Hawk.put("locationFlagFirst",1);
                    Intent in =new Intent(LocationActivity.this,SplashActivity.class);
                    startActivity(in);
                    finish();
                }
            });

        }

    }
    private void selectImage() {
        try {
            if (ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(LocationActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                            Intent in =new Intent(LocationActivity.this,SplashActivity.class);
                            startActivity(in);
                            finish();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("This app needs Location permissions.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getApplicationContext(), "Go to Permissions to Grant  Location", Toast.LENGTH_LONG).show();
                            Intent in =new Intent(LocationActivity.this,SplashActivity.class);
                            startActivity(in);
                            finish();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    //just request the permission
                    ActivityCompat.requestPermissions(this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }


                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0], true);
                editor.commit();
            } else {
                Intent in =new Intent(LocationActivity.this,SplashActivity.class);
                startActivity(in);
                finish();
            }
        } catch (Exception ex) {
            Log.v("cv", ex.getMessage().toString());
        }
    }
}
