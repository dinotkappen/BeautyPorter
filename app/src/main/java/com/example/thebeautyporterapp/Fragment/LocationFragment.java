package com.example.thebeautyporterapp.Fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

public class LocationFragment extends Fragment {

    View rootView;
    EditText edtBuildingNo, edtStreetName, edtZoneNo, edtLocation, edtStreetNo;
    Button btnSave;
    Utilities utilities;
    String access_token, name, phone, email, user_id, street_name, street_number, zone_number, building_number, location;
    String subListAdrz = "";
    ArrayList<Uri> img_List = new ArrayList<>();

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_location, container, false);
        edtBuildingNo = (EditText) rootView.findViewById(R.id.edtBuildingNo);
        edtZoneNo = (EditText) rootView.findViewById(R.id.edtZoneNo);
        edtLocation = (EditText) rootView.findViewById(R.id.edtLocation);
        edtStreetName = (EditText) rootView.findViewById(R.id.edtStreetName);
        edtStreetNo = (EditText) rootView.findViewById(R.id.edtStreetNo);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);

        user_id = Hawk.get("user_id", "");
        access_token = Hawk.get("access_token", "");

        name = getArguments().getString("name");
        phone = getArguments().getString("phone");
        email = getArguments().getString("email");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });
        return rootView;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void submitForm() {
        if (!validateBildingNo()) {
            return;
        }


        if (!validateStreetNo()) {
            return;
        }
        if (!validateStreetName()) {
            return;
        }
        if (!validateZoneNo()) {
            return;
        }
        if (!validateLocation()) {
            return;
        }
        update_profile_Adrz();

    }

    private boolean validateBildingNo() {
        if (edtBuildingNo.getText().toString().trim().isEmpty()) {
            edtBuildingNo.setError("Please enter building number...");
            requestFocus(edtBuildingNo);
            return false;
        }

        return true;
    }

    private boolean validateStreetNo() {
        if (edtStreetNo.getText().toString().trim().isEmpty()) {
            edtStreetNo.setError("Please enter street number...");
            requestFocus(edtStreetNo);
            return false;
        }

        return true;
    }

    private boolean validateStreetName() {
        if (edtStreetName.getText().toString().trim().isEmpty()) {
            edtStreetName.setError("Please enter street name...");
            requestFocus(edtStreetName);
            return false;
        }

        return true;
    }

    private boolean validateZoneNo() {
        if (edtZoneNo.getText().toString().trim().isEmpty()) {
            edtZoneNo.setError("Please enter Zone number...");
            requestFocus(edtZoneNo);
            return false;
        }

        return true;
    }

    private boolean validateLocation() {
        if (edtLocation.getText().toString().trim().isEmpty()) {
            edtLocation.setError("Please enter location");
            requestFocus(edtLocation);
            return false;
        }

        return true;
    }

    private void update_profile_Adrz() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        img_List.clear();
        street_name=edtStreetName.getText().toString().trim();
        street_number=edtStreetNo.getText().toString().trim();
        zone_number=edtZoneNo.getText().toString().trim();
        building_number=edtBuildingNo.getText().toString().trim();
        location=edtLocation.getText().toString().trim();

        String URL_UPDATE_PROFILE = utilities.GetUrl() + "update_profile";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_UPDATE_PROFILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

//                        avi.hide();
//                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {

                            JSONObject obj = new JSONObject(new String(response.data));


                            String success = obj.getString("status");
                            if (success.equals("success")) {
                                String message = obj.getString("message");
                                String lkg;
                                // Toast.makeText(getContext(),""+message,Toast.LENGTH_SHORT).show();
                                JSONObject jsonObjectcontent = obj.getJSONObject("content");
                                if (jsonObjectcontent.length() > 0) {
                                    String user_id = jsonObjectcontent.getString("user_id");
                                    String email = jsonObjectcontent.getString("email");
                                    street_name=jsonObjectcontent.getString("street_name");
                                    street_number=jsonObjectcontent.getString("street_number");
                                    zone_number=jsonObjectcontent.getString("zone_number");
                                    building_number=jsonObjectcontent.getString("building_number");
                                    location=jsonObjectcontent.getString("location");

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
                                       Hawk.put("HawkAddress",subListAdrz);
                                    }
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    ProfileFragment profileFragment = new ProfileFragment();
                                    fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    dialog.dismiss();
                                }




                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("contact_no", phone);
                params.put("user_id", user_id);
                params.put("street_name", street_name);
                params.put("street_number", street_number);
                params.put("zone_number", zone_number);
                params.put("building_number", building_number);
                params.put("location", location);


                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                for (int i = 0; i < img_List.size(); i++) {
                    InputStream iStream = null;
                    try {
                        Log.v("msg_att", "");
                        iStream = getActivity().getContentResolver().openInputStream(img_List.get(i));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    params.put("photo", new DataPart("file_avatar.jpg",
                            getFileDataFromDrawable(getActivity(), img_List.get(i))));
                    // params.put("images[" + i + "]", new DataPart("image" + i + ".jpg", inputData, "image/jpeg"));


                }


                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + access_token);
                headers.put("Accept", "application/json");

                return headers;
            }
        };

//        //adding the request to volley
//        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);


        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the string request to request queue
        requestQueue.add(volleyMultipartRequest);


    }

    public byte[] getFileDataFromDrawable(Context context, Uri uri) {
        String sd = uri.toString();
        Log.v("sd", sd);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Log.v("image", "" + uri.toString().contains("image"));
        if (uri.toString().contains("image")) {

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();


        }


        return byteArrayOutputStream.toByteArray();
    }
}
