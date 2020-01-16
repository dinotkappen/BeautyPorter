package com.example.thebeautyporterapp.Activity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thebeautyporterapp.Other.NetworkChangeReceiver;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

public class ForgotPwdActivity extends AppCompatActivity {
    Button btnForgotPwd;
    EditText edtEmail;
    Utilities utilities;
    private NetworkChangeReceiver receiver;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_forgot_pwd);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);
        btnForgotPwd = findViewById(R.id.btnForgotPwd);
        edtEmail = findViewById(R.id.edtEmail);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    submitForm();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void submitForm() {
        if (!validateEmail()) {
            return;
        }


        Log.v("LK", "LK");
        ForgotPwdApi();
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            edtEmail.setError(getString(R.string.validEmail));
            requestFocus(edtEmail);
            return false;
        }

        return true;
    }

    private void ForgotPwdApi() {
        //creating a string request to send request to the url
        String UrlLogIn = utilities.GetUrl() + "forgot";
        final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlLogIn,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);

                                String status = obj.getString("status");
                                if (status.equals("success")) {


                                    String message = obj.getString("message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();


                                } else {
                                    String message = obj.getString("message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPwdActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", edtEmail.getText().toString().trim());


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }
}
