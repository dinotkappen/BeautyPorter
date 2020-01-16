package com.example.thebeautyporterapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.thebeautyporterapp.Activity.LogInActivity;
import com.example.thebeautyporterapp.Activity.MainActivity;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;


public class ChangePasswordFragment extends Fragment {

    View rootView;
    Button btnSave;
    Utilities utilities;
    int logedIn;
    String access_token,user_id,logInMetod;
    EditText edtOldPwd,edtNewPwd,edtNewCnfrmPwd;
    String newPassword,cnfrmPwd,oldPwd;
    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {


            rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
            btnSave = (Button) rootView.findViewById(R.id.btnSave);

            logedIn = Hawk.get("logedIn", 0);
            access_token = Hawk.get("access_token", "");
            user_id = Hawk.get("user_id", "");
            logInMetod = Hawk.get("logInMetod", "");

            if (logedIn == 1) {

                edtOldPwd = (EditText) rootView.findViewById(R.id.edtOldPwd);
                edtNewPwd = (EditText) rootView.findViewById(R.id.edtNewPwd);
                edtNewCnfrmPwd = (EditText) rootView.findViewById(R.id.edtNewCnfrmPwd);


                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitForm();
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    }
                });
            } else {

                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);


            }
        } catch (Exception ex) {
            String msg = ex.getMessage().toString();
            String c = msg;
        }
        return rootView;
    }
        private void submitForm () {
            if (!validateOldPwd()) {
                return;
            }


            if (!validateNewPwd()) {
                return;
            }
            if (!validateCnfrmNewPwd()) {
                return;
            }
            newPassword = edtNewPwd.getText().toString();
            cnfrmPwd = edtNewCnfrmPwd.getText().toString();
            oldPwd = edtOldPwd.getText().toString();

            if (newPassword.equals(cnfrmPwd)) {
                resetPwdApi();
            } else {
                edtNewPwd.setError(getString(R.string.validEmail));
                edtNewPwd.setText("");
                edtNewCnfrmPwd.setText("");
            }

            Log.v("LK", "LK");

        }
        private void requestFocus (View view){
            if (view.requestFocus()) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

        private boolean validateOldPwd () {

            if (edtOldPwd.getText().toString().trim().isEmpty()) {
                edtOldPwd.setError(getString(R.string.validPassword));
                requestFocus(edtOldPwd);
                return false;
            }


            return true;
        }

        private boolean validateNewPwd () {
            String email = edtNewPwd.getText().toString().trim();
            if (edtNewPwd.getText().toString().trim().isEmpty()) {
                edtNewPwd.setError(getString(R.string.validPassword));
                requestFocus(edtNewPwd);
                return false;
            }


            return true;
        }


        private boolean validateCnfrmNewPwd () {
            String email = edtNewCnfrmPwd.getText().toString().trim();

            if (edtNewCnfrmPwd.getText().toString().trim().isEmpty()) {
                edtNewCnfrmPwd.setError(getString(R.string.validPassword));
                requestFocus(edtNewCnfrmPwd);
                return false;
            }


            return true;
        }

        private void resetPwdApi () {

            final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                    .build();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            String UrlServices = utilities.GetUrl() + "change_password";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            try {
                                if (response != null && !response.isEmpty() && !response.equals("null")) {
                                    JSONObject obj = new JSONObject(response);

                                    String status = obj.getString("status");
                                    String message = obj.getString("message");
                                    if (status.equals("success")) {

                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        getActivity().startActivity(intent);
                                        dialog.dismiss();

                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                        edtOldPwd.setText("");
                                        edtNewPwd.setText("");
                                        edtNewCnfrmPwd.setText("");
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            dialog.dismiss();
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
                    headers.put("Authorization", "Bearer " + access_token);
                    headers.put("Accept", "application/json");

                    return headers;
                }


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", user_id);
                    params.put("current_password", oldPwd);
                    params.put("password", newPassword);


                    return params;
                }
            };

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        }
    }
