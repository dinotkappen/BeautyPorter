package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.thebeautyporterapp.Adapter.AppoinmentAdapter;
import com.example.thebeautyporterapp.Model.AppoinmentModel;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;


public class AppoinmentFragment extends Fragment {

    View rootView;
    LinearLayout lineaerMain,lineaerNoData;
    private RecyclerView recyclerViewAppoinmentList;
    private AppoinmentAdapter appoinmentAdapter;

    ArrayList<AppoinmentModel>appoinmentModelUpComing = new ArrayList<AppoinmentModel>();
    ArrayList<AppoinmentModel> appoinmentModelPast = new ArrayList<AppoinmentModel>();
    String access_token, user_id;
    int logedIn;
    Utilities utilities;
    public static CardView cardUpComing, cardPast;
    public AppoinmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_appoinment, container, false);
        lineaerMain=(LinearLayout)rootView.findViewById(R.id.lineaerMain);
        lineaerNoData=(LinearLayout)rootView.findViewById(R.id.lineaerNoData);
        lineaerNoData.setVisibility(View.GONE);
        lineaerMain.setVisibility(View.VISIBLE);

        logedIn = Hawk.get("logedIn", 0);
        user_id = Hawk.get("user_id", "");
        access_token = Hawk.get("access_token", "");
        Log.v("user_id",""+user_id);
        Log.v("access_token",access_token);

        cardUpComing = (CardView) rootView.findViewById(R.id.cardUpComing);
        cardPast = (CardView) rootView.findViewById(R.id.cardPast);
        recyclerViewAppoinmentList = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewAppoinmentList.setLayoutManager(mLayoutManager);
        recyclerViewAppoinmentList.setItemAnimator(new DefaultItemAnimator());

        linearActionBar.setVisibility(View.VISIBLE);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.appointment));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);


        cardUpComing.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cardPast.setCardBackgroundColor(getResources().getColor(R.color.colorGray));
        cardUpComing.setCardElevation(10);
        cardPast.setCardElevation(1);
        cardUpComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardUpComing.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                cardPast.setCardBackgroundColor(getResources().getColor(R.color.colorGray));
                cardUpComing.setCardElevation(10);
                cardPast.setCardElevation(1);
                appoinmentAdapter = new AppoinmentAdapter(getActivity(), appoinmentModelUpComing);
                recyclerViewAppoinmentList.setAdapter(appoinmentAdapter);
            }
        });
        cardPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardUpComing.setCardBackgroundColor(getResources().getColor(R.color.colorGray));
                cardPast.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                cardUpComing.setCardElevation(1);
                cardPast.setCardElevation(10);
                appoinmentAdapter = new AppoinmentAdapter(getActivity(), appoinmentModelPast);
                recyclerViewAppoinmentList.setAdapter(appoinmentAdapter);
            }
        });
        loadBookingHistoryApi();
        return rootView;
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
                                        lineaerNoData.setVisibility(View.GONE);
                                        lineaerMain.setVisibility(View.VISIBLE);
                                        appoinmentModelUpComing.clear();
                                        appoinmentModelPast.clear();

                                        for (int i = 0; i < contentsArray.length(); i++) {
                                            JSONObject jsondata = contentsArray.getJSONObject(i);
                                            String id = jsondata.getString("id");
                                            String booking_date = jsondata.getString("booking_date");
                                            String booking_status = jsondata.getString("booking_status");
                                            String amount = jsondata.getString("amount");


                                            if(!jsondata.isNull( "business" )) {
                                                JSONObject businessObj = jsondata.getJSONObject("business");
                                                String business_name = businessObj.getString("business_name");
                                                String contact_no = businessObj.getString("contact_no");
                                                String photo = businessObj.getString("photo");

                                                Log.v("photoAppoinment", photo);


                                                if (booking_status.equals("Booked")) {
                                                    appoinmentModelUpComing.add(new AppoinmentModel(id, business_name, booking_date, booking_date, photo, booking_status, "UPCOMING", "25.28", "51.53", contact_no));

                                                } else {
                                                    appoinmentModelPast.add(new AppoinmentModel(id, business_name, booking_date, booking_date, photo, booking_status, "PAST", "25.28", "51.53", contact_no));
                                                }

//                                                if (booking_status.equals("Completed") || booking_status.equals("Cancel")) {
//                                                    appoinmentModelPast.add(new AppoinmentModel(id, business_name, booking_date, booking_date, photo, booking_status, "PAST", "25.28", "51.53", contact_no));
//                                                } else {
//                                                    appoinmentModelUpComing.add(new AppoinmentModel(id, business_name, booking_date, booking_date, photo, booking_status, "UPCOMING", "25.28", "51.53", contact_no));
//                                                }
                                            }


                                        }
                                    } else {
                                        lineaerNoData.setVisibility(View.VISIBLE);
                                        lineaerMain.setVisibility(View.GONE);
                                    }
                                    try {

                                        appoinmentAdapter = new AppoinmentAdapter(getActivity(), appoinmentModelUpComing);
                                        recyclerViewAppoinmentList.setAdapter(appoinmentAdapter);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();

                                    }
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), getString(R.string.Server_Error), Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
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

}
