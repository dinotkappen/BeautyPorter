package com.example.thebeautyporterapp.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v4.content.LocalBroadcastManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thebeautyporterapp.Other.NetworkChangeReceiver;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.Other.VolleySingleton;
import com.example.thebeautyporterapp.R;
import com.example.thebeautyporterapp.service.Config;
import com.example.thebeautyporterapp.service.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.orhanobut.hawk.Hawk;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

public class LogInActivity extends AppCompatActivity {

    Button btnSignIn;
    TextView txtForgotPwd;
    EditText edtEmail, edtPwd;
    LinearLayout linearSignUp;
    public static Utilities utilities;
    String subListAdrz = "";
    String userNameVerifyed, passwordVerifyed;
    ImageView imgBack;
    static LogInActivity loginActivty;
    private NetworkChangeReceiver receiver;
    static  ACProgressFlower dialogLogIn;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = LogInActivity.class.getSimpleName();
    public static String firebasseRegID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);
        dialogLogIn = new ACProgressFlower.Builder(this)
                .build();
        loginActivty = LogInActivity.this;
        imgBack = (ImageView) findViewById(R.id.imgBack);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);


        userNameVerifyed = Hawk.get("userNameVerifyed", userNameVerifyed);
        passwordVerifyed = Hawk.get("passwordVerifyed", passwordVerifyed);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        edtPwd = (EditText) findViewById(R.id.edtPwd);
        txtForgotPwd = (TextView) findViewById(R.id.txtForgotPwd);
        linearSignUp = (LinearLayout) findViewById(R.id.linearSignUp);

        if (userNameVerifyed != null && !userNameVerifyed.isEmpty() && !userNameVerifyed.equals("null")) {
            edtEmail.setText(userNameVerifyed);
        }
        if (passwordVerifyed != null && !passwordVerifyed.isEmpty() && !passwordVerifyed.equals("null")) {
            edtPwd.setText(passwordVerifyed);
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linearSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });
        txtForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgotPwdActivity.class);
                startActivity(intent);
            }
        });


        //FireBase Notification

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    Log.v("message", message);

                }
            }
        };

        displayFirebaseRegId();
    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", "");
        firebasseRegID = regId;

        Log.e(TAG, "Firebase reg id: " + regId);

//        if (!TextUtils.isEmpty(regId))
////            txtRegId.setText("Firebase Reg Id: " + regId);
//        else
        //txtRegId.setText("Firebase Reg Id is not received yet!");
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

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword() {
        if (edtPwd.getText().toString().trim().isEmpty()) {
            edtPwd.setError(getString(R.string.validPassword));
            requestFocus(edtPwd);
            return false;
        }

        return true;
    }

    private void submitForm() {
        if (!validateEmail()) {
            return;
        }


        if (!validatePassword()) {
            return;
        }
        Log.v("LK", "LK");
        LogInCheckApi();
    }

    public static void DeviceChkApi(final Context mContext) {
        String user_id = Hawk.get("user_id");
        String access_token = "Bearer "+Hawk.get("access_token");
        String URL_DEVICE_REG = utilities.GetUrl() + "registerdevice";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DEVICE_REG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                            //converting response to json object
                            JSONObject obj = new JSONObject(response);


                            String status = obj.getString("status");
                            if (status.equals("200")) {
                                String success = obj.getString("success");

                                if (success.equals("1")) {


                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    mContext.startActivity(intent);
                                    loginActivty.finish();
                                    dialogLogIn.dismiss();


                                } else {


                                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {

                            if (e != null && !e.equals("null")) {
                                Log.v("errorgetMessage()", "errorCatch");
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.v("TimeoutError", "TimeoutError");
                            //This indicates that the reuest has either time out or there is no connection
                        } else if (error instanceof AuthFailureError) {
                            Log.v("AuthFailureError", "AuthFailureError");
                            //Error indicating that there was an Authentication Failure while performing the request
                        } else if (error instanceof ServerError) {
                            Log.v("ServerError", "ServerError");
                            //Indicates that the server responded with a error response
                        } else if (error instanceof NetworkError) {
                            Log.v("NetworkErrorParseError", "NetworkError");
                            //Indicates that there was network error while performing the request
                        } else if (error instanceof ParseError) {
                            Log.v("ParseError", "ParseError");
                            // Indicates that the server response could not be parsed
                        }
                    }
                }) {
            /** Passing some request headers* */
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization",access_token );

                return headers;
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("device_id", firebasseRegID);
                params.put("device_type", "2");
                params.put("device_os", "Android");
                params.put("user_id", user_id);


                return params;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    private void LogInCheckApi() {
        //creating a string request to send request to the url
        String UrlLogIn = utilities.GetUrl() + "login";

        dialogLogIn.show();
        userNameVerifyed = edtEmail.getText().toString();
        passwordVerifyed = edtPwd.getText().toString();


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
                                    JSONObject jsonObjectContent = obj.getJSONObject("content");
                                    if (jsonObjectContent.length() > 0) {

                                        String user_id = jsonObjectContent.getString("user_id");
                                        String name = jsonObjectContent.getString("name");
                                        String photo = jsonObjectContent.getString("photo");
                                        String email = jsonObjectContent.getString("email");
                                        String phone = jsonObjectContent.getString("phone");
                                        String access_token = jsonObjectContent.getString("access_token");
                                        String street_name = jsonObjectContent.getString("street_name");
                                        String street_number = jsonObjectContent.getString("street_number");
                                        String zone_number = jsonObjectContent.getString("zone_number");
                                        String building_number = jsonObjectContent.getString("building_number");
                                        String location = jsonObjectContent.getString("location");

                                        if (building_number != null && !building_number.isEmpty() && !building_number.equals("null")) {
                                            if (subListAdrz.length() > 0) {
                                                subListAdrz = subListAdrz + "," + building_number;
                                            } else {
                                                subListAdrz = subListAdrz + building_number;
                                            }

                                        }
                                        if (street_number != null && !street_number.isEmpty() && !street_number.equals("null")) {
                                            if (subListAdrz.length() > 0) {
                                                subListAdrz = subListAdrz + "," + street_number;
                                            } else {
                                                subListAdrz = subListAdrz + street_number;
                                            }

                                        }
                                        if (street_name != null && !street_name.isEmpty() && !street_name.equals("null")) {
                                            if (subListAdrz.length() > 0) {
                                                subListAdrz = subListAdrz + "," + street_name;
                                            } else {
                                                subListAdrz = subListAdrz + street_name;
                                            }

                                        }
                                        if (location != null && !location.isEmpty() && !location.equals("null")) {
                                            if (subListAdrz.length() > 0) {
                                                subListAdrz = subListAdrz + "," + location;
                                            } else {
                                                subListAdrz = subListAdrz + location;
                                            }

                                        }
                                        if (subListAdrz != null && !subListAdrz.isEmpty() && !subListAdrz.equals("null")) {
                                            Hawk.put("HawkAddress", subListAdrz);
                                        }


                                        Hawk.put("user_id", user_id);
                                        Hawk.put("name", name);
                                        Hawk.put("photo", photo);
                                        Hawk.put("email", email);
                                        Hawk.put("phone", phone);
                                        Hawk.put("access_token", access_token);
                                        Hawk.put("logInMetod", "normal");
                                        Hawk.put("logedIn", 1);
                                        Hawk.put("userNameVerifyed", userNameVerifyed);
                                        Hawk.put("passwordVerifyed", passwordVerifyed);

                                        DeviceChkApi(LogInActivity.this);


                                    }


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
                        dialogLogIn.dismiss();
                        Toast.makeText(LogInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", userNameVerifyed);
                params.put("password", passwordVerifyed);
                params.put("device_type", "Android");
                params.put("device_token", firebasseRegID);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        finish();
        // your code.
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}