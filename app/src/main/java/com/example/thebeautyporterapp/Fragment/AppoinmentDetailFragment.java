package com.example.thebeautyporterapp.Fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Adapter.AppoinmentDetailAdapter;
import com.example.thebeautyporterapp.Model.AppoinmentDetailModel;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppoinmentDetailFragment extends Fragment {

    View rootView;
    LinearLayout linearCancel, linearCall, linearMap;
    private RecyclerView recyclerViewOrderHistory;
    String access_token, user_id, booking_id;
    int logedIn;
    String bookdedDate;
    TextView txtOrderName;
    ImageView imgOrderHistoryDetail;
    String selected_lat, selectedLog, mode, modePrice;

    Utilities utilities;
    private AppoinmentDetailAdapter appoinmentDetailAdapter;
    ArrayList<AppoinmentDetailModel> appoinmentDetailModel = new ArrayList<AppoinmentDetailModel>();
    String service_name, selectedBookingHistoryID, selectedName, selectedImg, statusBooking;

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CALL_PHONE};
    String contact_no;
    String phoneCall;


    public AppoinmentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_appoinment_detail, container, false);

        access_token = Hawk.get("access_token", "");
        user_id = Hawk.get("user_id", "");
        logedIn = Hawk.get("logedIn", 0);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedBookingHistoryID = bundle.getString("selectedBookingHistoryID", "");
            booking_id = selectedBookingHistoryID;
            Log.v("booking_id", booking_id);
            selectedName = bundle.getString("selectedName", "");
            selectedImg = bundle.getString("selectedImg", "");
            statusBooking = bundle.getString("statusBooking", "");
            contact_no = bundle.getString("contact_no", "");
            bookdedDate = bundle.getString("bookdedDate", "");
            Log.v("bookdedDateBundle", bookdedDate);

        }
        imgOrderHistoryDetail = (ImageView) rootView.findViewById(R.id.imgOrderHistoryDetail);
        txtOrderName = (TextView) rootView.findViewById(R.id.txtOrderName);
        if (selectedName != null && !selectedName.isEmpty() && !selectedName.equals("null")) {
            txtOrderName.setText(selectedName);
        }
        if (selectedImg != null && !selectedImg.isEmpty() && !selectedImg.equals("null")) {
            try {
                Glide.with(getActivity())
                        .load(selectedImg)
                        .into(imgOrderHistoryDetail);
            } catch (Exception ex) {
                Log.v("ex", ex.getMessage().toString());
            }
        }

        linearCancel = (LinearLayout) rootView.findViewById(R.id.linearCancel);
        linearCall = (LinearLayout) rootView.findViewById(R.id.linearCall);
        linearMap = (LinearLayout) rootView.findViewById(R.id.linearMap);

        linearMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MapAppoinmentDetailFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("selectedLat", selected_lat);
                bundle.putString("selectedLong", selectedLog);

                fragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });

        permissionStatus = getActivity().getSharedPreferences("permissionStatus", MODE_PRIVATE);
        linearCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionCheck();

            }
        });
        linearCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date oneWayTripDate = null;
                String hj = null;
                try {

                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    try {
                        oneWayTripDate = input.parse(bookdedDate);                 // parse input
                        hj = output.format(oneWayTripDate);
                        Log.v("hj", hj);// format output
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");

                    try {
                        Date c = Calendar.getInstance().getTime();
                        Date date1 = c;
                        Date date2 = simpleDateFormat.parse(hj);

                        long diff = date1.getTime() - date2.getTime();
                        long seconds = diff / 1000;
                        long minutes = seconds / 60;
                        long hours = minutes / 60;
                        long days = hours / 24;

                        if (days <= 1 && days >= -1) {
                            if (user_id != null && !user_id.isEmpty() && !user_id.equals("null")) {
                                if (booking_id != null && !booking_id.isEmpty() && !booking_id.equals("null")) {
                                    cancelBookingApi();
                                }

                            }
                        } else {
                            Toast.makeText(getContext(), "Cancellation time exceeded.Please go through the cancellation policy for more details", Toast.LENGTH_SHORT).show();
                        }

                        Log.v("daysdays", "" + days);

                        //  printDifference(date1, date2);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

//1 minute = 60 seconds
//1 hour = 60 x 60 = 3600
//1 day = 3600 x 24 = 86400


//
                } catch (Exception ex) {
                    Log.v("xyz", ex.getMessage().toString());
                }

            }
        });

        if (statusBooking != null && !statusBooking.isEmpty() && !statusBooking.equals("null")) {
            if (statusBooking.equals("Completed") || statusBooking.equals("Cancel")) {
                linearCancel.setVisibility(View.GONE);
            } else {
                linearCancel.setVisibility(View.VISIBLE);
            }

        }

        recyclerViewOrderHistory = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewOrderHistory.setLayoutManager(mLayoutManager);
        recyclerViewOrderHistory.setItemAnimator(new DefaultItemAnimator());


        if (user_id != null && !user_id.isEmpty() && !user_id.equals("null")) {
            if (access_token != null && !access_token.isEmpty() && !access_token.equals("null")) {
                loadBookingHistoryApi();
            }
        }

        return rootView;
    }


    private void permissionCheck() {
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[0])) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Permissions");
                    builder.setMessage("This app needs Call permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Permissions");
                    builder.setMessage("This app needs Call permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getContext(), "Go to Permissions to Grant  Call phone", Toast.LENGTH_LONG).show();
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
                    ActivityCompat.requestPermissions(getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }


                SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0], true);
                editor.commit();
            } else {
                //You already have the permission, just go ahead.
                proceedAfterPermission();
            }
        } catch (Exception ex) {
            Log.v("cv", ex.getMessage().toString());
        }
    }

    public void proceedAfterPermission() {
        Intent intent = new Intent(Intent.ACTION_CALL);

        if (phoneCall != null && !phoneCall.isEmpty() && !phoneCall.equals("null")) {
            intent.setData(Uri.parse("tel:" + phoneCall));
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Sorry not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBookingHistoryApi() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String UrlServices = utilities.GetUrl() + "booking_history";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                JSONObject obj = new JSONObject(response);

                                String status = obj.getString("status");
                                if (status.equals("success")) {

                                    JSONArray contentsArray = obj.getJSONArray("content");

                                    if (contentsArray.length() > 0) {


                                        for (int i = 0; i < contentsArray.length(); i++) {
                                            JSONObject jsondata = contentsArray.getJSONObject(i);
                                            String id = jsondata.getString("id");

                                            Log.v("idBooking", id);

                                            if (!jsondata.isNull("business")) {
                                                JSONObject businessObj = jsondata.getJSONObject("business");
                                                String business_name = businessObj.getString("business_name");
                                                String photo = businessObj.getString("photo");
                                                String contact_no = businessObj.getString("contact_no");
                                                Log.v("business_name", business_name);


                                                JSONArray booking_service_chargeObj = jsondata.getJSONArray("booking_service_charge");
                                                if (booking_service_chargeObj.length() > 0) {
                                                    for (int h = 0; h < booking_service_chargeObj.length(); h++) {
                                                        JSONObject jsonObjectbooking_service_charge = booking_service_chargeObj.getJSONObject(h);
                                                        String booking_date = jsonObjectbooking_service_charge.getString("booking_date");
                                                        String booking_time = jsonObjectbooking_service_charge.getString("booking_time");
                                                        mode = jsonObjectbooking_service_charge.getString("mode");


                                                        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                                                        Date newDate = null;
                                                        try {
                                                            newDate = spf.parse(booking_date);
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }
                                                        spf = new SimpleDateFormat("dd-MM-yyyy");
                                                        booking_date = spf.format(newDate);

                                                        if (!jsonObjectbooking_service_charge.isNull("business_services")) {
                                                            JSONObject business_servicesObj = jsonObjectbooking_service_charge.getJSONObject("business_services");
                                                            if (business_servicesObj.length() > 0) {
                                                                service_name = business_servicesObj.getString("service_name");
                                                                if (mode.equals("Salon")) {
                                                                    modePrice = business_servicesObj.getString("price");
                                                                } else if (mode.equals("Spa & Clinics")) {
                                                                    modePrice = business_servicesObj.getString("price1");
                                                                } else if (mode.equals("Home Service")) {
                                                                    modePrice = business_servicesObj.getString("price2");
                                                                }
                                                            }
                                                            if (selectedBookingHistoryID.equals(id)) {
                                                                selected_lat = "25.28";
                                                                selectedLog = "51.53";
                                                                appoinmentDetailModel.add(new AppoinmentDetailModel(id, photo, business_name, booking_time, booking_date, service_name, modePrice, selected_lat, selectedLog, contact_no));
                                                                phoneCall = contact_no;
                                                                Log.v("booking_time", booking_time);
                                                            }

                                                        }
                                                    }

                                                }
                                            }


                                        }
                                    }
                                    try {

                                        appoinmentDetailAdapter = new AppoinmentDetailAdapter(getActivity(), appoinmentDetailModel);
                                        recyclerViewOrderHistory.setAdapter(appoinmentDetailAdapter);
                                        dialog.dismiss();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();

                                    }
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), getString(R.string.Server_Error), Toast.LENGTH_LONG).show();
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


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


    private void cancelBookingApi() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String UrlServices = utilities.GetUrl() + "booking_cancel";

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
                                    dialog.dismiss();
                                    Fragment fragment = new AppoinmentFragment();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();


                                } else {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

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
                        //displaying the error in toast if occurrs

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
                params.put("booking_id", booking_id);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
