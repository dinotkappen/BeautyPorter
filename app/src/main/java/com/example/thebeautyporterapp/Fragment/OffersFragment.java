package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;


import android.support.v4.app.Fragment;
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
import com.example.thebeautyporterapp.Adapter.OfferAdapter;
import com.example.thebeautyporterapp.Model.OfferModel;
import com.example.thebeautyporterapp.R;
import com.example.thebeautyporterapp.Other.Utilities;
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

public class OffersFragment extends Fragment {
    View rootView;

    private RecyclerView recyclerViewOffer;
    private OfferAdapter offerAdapter;
    ArrayList<OfferModel> offerModel = new ArrayList<OfferModel>();
    Utilities utilities;
    String access_token, user_id;
    int logedIn;
    LinearLayout lineaerMain,lineaerNoData;

    public OffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            if (utilities.isOnline(getActivity())) {

                Log.e("token",Hawk.get("access_token"));
                access_token = Hawk.get("access_token");
                user_id = Hawk.get("user_id");
                // Inflate the layout for this fragment
                rootView = inflater.inflate(R.layout.fragment_offers, container, false);
                lineaerMain=(LinearLayout)rootView.findViewById(R.id.lineaerMain);
                lineaerNoData=(LinearLayout)rootView.findViewById(R.id.lineaerNoData);
                lineaerNoData.setVisibility(View.GONE);
                lineaerMain.setVisibility(View.VISIBLE);

                linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                imgMenu.setVisibility(View.GONE);
                backArrow.setVisibility(View.VISIBLE);
                txt_actionbar_Title.setVisibility(View.VISIBLE);
                txt_actionbar_Title.setText(getString(R.string.offer));
                imgFilter.setVisibility(View.GONE);
                imgSearch.setVisibility(View.GONE);


//                Hawk.init(getActivity())
//                        .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
//                        .setStorage(HawkBuilder.newSqliteStorage(getActivity()))
//                        .setLogLevel(LogLevel.FULL)
//                        .build();


                recyclerViewOffer = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                recyclerViewOffer.setLayoutManager(mLayoutManager);
                recyclerViewOffer.setItemAnimator(new DefaultItemAnimator());





                loadOffersApi();
            }
            else {


            }
        } catch (Exception ex) {
            String msg = ex.getMessage().toString();
            String c = msg;
        }
        return rootView;
    }


    private void loadOffersApi() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String UrlServices = utilities.GetUrl() + "list";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();
                        offerModel.clear();
                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                JSONObject obj = new JSONObject(response);

                                Log.e("response_pkb",response.toString());
                                String status = obj.getString("status");
                                if (status.equals("success")) {

                                    JSONArray contentsArray = obj.getJSONArray("content");

                                    if (contentsArray.length() > 0) {

                                        lineaerNoData.setVisibility(View.GONE);
                                        lineaerMain.setVisibility(View.VISIBLE);
                                        for (int i = 0; i < contentsArray.length(); i++) {
                                            JSONObject jsondata = contentsArray.getJSONObject(i);
                                            String id = jsondata.getString("id");
                                            String coupon_name = jsondata.getString("coupon_name");
                                            String coupon_code = jsondata.getString("coupon_code");
                                            String amount = jsondata.getString("amount");
                                            String description = jsondata.getString("description");
                                            String type = jsondata.getString("type");



                                            offerModel.add(new OfferModel(id, coupon_name, description, amount,coupon_code,type));


                                        }
                                    }
                                    else
                                    {
                                        lineaerNoData.setVisibility(View.VISIBLE);
                                        lineaerMain.setVisibility(View.GONE);
                                    }

                                    try {
                                        offerAdapter = new OfferAdapter(getActivity(), offerModel);
                                        recyclerViewOffer.setAdapter(offerAdapter);

                                    } catch (Exception ex) {
                                        ex.printStackTrace();

                                    }
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), getString(R.string.Server_Error), Toast.LENGTH_LONG).show();
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



                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


}
