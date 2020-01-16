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
import com.example.thebeautyporterapp.Adapter.WishListAdapter;
import com.example.thebeautyporterapp.Model.WishListModel;
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


public class WishListFragment extends Fragment {
    View rootView;
    LinearLayout linearMainLayout,lineaerNoData;
    private RecyclerView recyclerViewwisgList;
    private WishListAdapter wishListAdapter;
    ArrayList<WishListModel> wishListModel = new ArrayList<WishListModel>();

    String access_token, user_id;
    int logedIn;
    Utilities utilities;
    String subListAdrz="";
    
    public WishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_wish_list, container, false);
        linearMainLayout=(LinearLayout)rootView.findViewById(R.id.linearMainLayout);
        lineaerNoData=(LinearLayout)rootView.findViewById(R.id.lineaerNoData);
        lineaerNoData.setVisibility(View.GONE);
        linearMainLayout.setVisibility(View.VISIBLE);

        logedIn = Hawk.get("logedIn", 0);
        user_id = Hawk.get("user_id", "");
        access_token = Hawk.get("access_token", "");
        Log.v("user_id",""+user_id);
        Log.v("access_token",access_token);

        linearActionBar.setVisibility(View.VISIBLE);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.favourites));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);

        

        recyclerViewwisgList = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewwisgList.setLayoutManager(mLayoutManager);
        recyclerViewwisgList.setItemAnimator(new DefaultItemAnimator());
        if(logedIn==1)
        {
            loadWishListApi();
        }




        return rootView;
    }

    private void loadWishListApi() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String UrlServices = utilities.GetUrl() + "favourite_list";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();

                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                JSONObject obj = new JSONObject(response);

                                String status = obj.getString("status");
                                if (status.equals("success")) {

                                    JSONArray contentsArray = obj.getJSONArray("content");

                                    if (contentsArray.length() > 0) {
                                        lineaerNoData.setVisibility(View.GONE);
                                        linearMainLayout.setVisibility(View.VISIBLE);

                                        for (int i = 0; i < contentsArray.length(); i++) {

                                            JSONObject jsondata = contentsArray.getJSONObject(i);
                                            String id = jsondata.getString("id");
                                            subListAdrz="";
                                            JSONObject businessJSONObj = jsondata.getJSONObject("business");
                                            if (businessJSONObj.length() > 0) {
                                                String business_name = businessJSONObj.getString("business_name");
                                                String photo = businessJSONObj.getString("photo");
                                                String rating = businessJSONObj.getString("rating");
                                                String building_number = businessJSONObj.getString("building_number");
                                                String street_number = businessJSONObj.getString("street_number");
                                                String street_name = businessJSONObj.getString("street_name");
                                                String locations=businessJSONObj.getString("locations");


                                                if (building_number != null && !building_number.isEmpty() && !building_number.equals("null")) {
                                                    if(subListAdrz.length()>0)
                                                    {
                                                        subListAdrz=subListAdrz+","+building_number;
                                                    }
                                                    else
                                                    {
                                                        subListAdrz=subListAdrz+building_number;
                                                    }

                                                }
                                                if (street_number != null && !street_number.isEmpty() && !street_number.equals("null")) {
                                                    if(subListAdrz.length()>0)
                                                    {
                                                        subListAdrz=subListAdrz+","+street_number;
                                                    }
                                                    else
                                                    {
                                                        subListAdrz=subListAdrz+street_number;
                                                    }

                                                }
                                                if (street_name != null && !street_name.isEmpty() && !street_name.equals("null")) {
                                                    if(subListAdrz.length()>0)
                                                    {
                                                        subListAdrz=subListAdrz+","+street_name;
                                                    }
                                                    else
                                                    {
                                                        subListAdrz=subListAdrz+street_name;
                                                    }

                                                }
                                                if (locations != null && !locations.isEmpty() && !locations.equals("null")) {
                                                    if(subListAdrz.length()>0)
                                                    {
                                                        subListAdrz=subListAdrz+","+locations;
                                                    }
                                                    else
                                                    {
                                                        subListAdrz=subListAdrz+locations;
                                                    }

                                                }
                                                wishListModel.add(new WishListModel(id, photo, business_name, rating, subListAdrz));
                                            }




                                        }
                                    }
                                    else
                                    {
                                        lineaerNoData.setVisibility(View.VISIBLE);
                                        linearMainLayout.setVisibility(View.GONE);
                                    }
                                    try {
                                        wishListAdapter = new WishListAdapter(getActivity(), wishListModel);
                                        recyclerViewwisgList.setAdapter(wishListAdapter);

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
