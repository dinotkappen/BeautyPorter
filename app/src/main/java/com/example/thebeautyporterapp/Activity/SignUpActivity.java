package com.example.thebeautyporterapp.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

public class SignUpActivity extends AppCompatActivity {
    Button btnSignup;
    EditText edtFirstName, edtLastName, edtPhone, edtPwd, edtEmail;
    LinearLayout linearSignIn;
    Utilities utilities;
    private NetworkChangeReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPwd = (EditText) findViewById(R.id.edtPwd);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        linearSignIn = (LinearLayout) findViewById(R.id.linearSignIn);
        linearSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                submitForm();
            }
        });
    }

    private void submitForm() {

        if (!validateFirstName()) {
            return;
        }
        if (!validateSecondName()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

//        if (!validateCnfrmPassword()) {
//            return;
//        }

        UserRegistration_api();
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateFirstName() {
        if (edtFirstName.getText().toString().trim().isEmpty()) {
            edtFirstName.setError(getString(R.string.validFirstName));
            requestFocus(edtFirstName);
            return false;
        }

        return true;
    }

    private boolean validateSecondName() {
        if (edtFirstName.getText().toString().trim().isEmpty()) {
            edtFirstName.setError(getString(R.string.validSecondName));
            requestFocus(edtFirstName);
            return false;
        }

        return true;
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

    private boolean validatePhone() {
        if (edtPhone.getText().toString().trim().isEmpty()) {
            edtPhone.setError(getString(R.string.validPhone));
            requestFocus(edtPhone);
            return false;
        }
        int length=edtPhone.getText().toString().length();
        if ( length< 8) {
            edtPhone.setError("Please enter a valid phone number");
            requestFocus(edtPhone);
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        if (edtPwd.getText().toString().trim().isEmpty()) {
            edtPwd.setError(getString(R.string.validPassword));
            requestFocus(edtPwd);
            return false;
        }

        if (TextUtils.isEmpty(edtPwd.getText().toString()) || edtPwd.getText().toString().length() < 8) {
            edtPwd.setError(getString(R.string.validPwdLength));
            requestFocus(edtPwd);
            return false;
        }
        return true;
    }

    private void UserRegistration_api() {
        //creating a string request to send request to the url
        String UrlSignUp = utilities.GetUrl() + "register";
        // https://www.thebeautyporter.net/api/register

        final ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlSignUp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            dialog.dismiss();

                            String status = obj.getString("status");
                            if (status.equals("success")) {
                                String message = obj.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String message = obj.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("name", edtFirstName.getText().toString());
                params.put("email", edtEmail.getText().toString());
                params.put("contact_no", "+974"+edtPhone.getText().toString());
                params.put("password", edtPwd.getText().toString());
                params.put("device_type", "Android");
                params.put("device_token", "12345678454543545435465sdv");

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

}