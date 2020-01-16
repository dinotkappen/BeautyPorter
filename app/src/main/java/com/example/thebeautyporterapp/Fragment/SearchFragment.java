package com.example.thebeautyporterapp.Fragment;


import android.content.Context;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.example.thebeautyporterapp.Adapter.SearchAdapter;
import com.example.thebeautyporterapp.Model.SearchModel;
import com.example.thebeautyporterapp.Model.SubServiceModel;
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

public class SearchFragment extends Fragment {

    View rootView;
    LinearLayout lineaerMain,lineaerNoData;
    private RecyclerView recyclerViewSearch;
    private SearchAdapter searchAdapter;
    ArrayList<SearchModel> searchModel = new ArrayList<SearchModel>();
    EditText edtSearch;
    ArrayList<SubServiceModel> subServiceModel = new ArrayList<SubServiceModel>();
    String access_token, user_id;
    int logedIn;
    Utilities utilities;
    String subListAdrz,subServiceDescription;
    String selectedMainServiceID, isfavourite;
    String keyword;
    TextView imgClose;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_search, container, false);
        edtSearch=(EditText)rootView.findViewById(R.id.edtSearch);

        lineaerMain=(LinearLayout)rootView.findViewById(R.id.lineaerMain);
        lineaerNoData=(LinearLayout)rootView.findViewById(R.id.lineaerNoData);
        lineaerNoData.setVisibility(View.GONE);
        lineaerMain.setVisibility(View.VISIBLE);

        linearActionBar.setVisibility(View.VISIBLE);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.Search));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);

        imgClose=(TextView)rootView.findViewById(R.id.imgClose);
        imgClose.setVisibility(View.GONE);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                subServiceModel.clear();
                searchAdapter = new SearchAdapter(getActivity(), subServiceModel);
                recyclerViewSearch.setAdapter(searchAdapter);
            }
        });

        recyclerViewSearch = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
        user_id = Hawk.get("user_id", "");
        access_token = Hawk.get("access_token", "");
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.requestFocus();
                edtSearch.setFocusable(true);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    keyword = edtSearch.getText().toString().trim();
                    loadServiceListApi(keyword);
                    Log.v("reached","Reached");
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    return true;
                }
                else if(actionId==3)
                {
                    keyword = edtSearch.getText().toString().trim();
                    loadServiceListApi(keyword);
                    Log.v("reached","Reached");
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    return true;
                }
                else {
                    keyword = edtSearch.getText().toString().trim();
                    loadServiceListApi(keyword);
                    Log.v("reached", "Reached");
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    return true;
                }


            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String temp = keyword = edtSearch.getText().toString();
                if(temp.length()>0)
                {
                    imgClose.setVisibility(View.VISIBLE);
                }
                else
                {
                    imgClose.setVisibility(View.INVISIBLE);
                }


            }
        });
        return rootView;
    }

    private void loadServiceListApi(final String keyword) {


        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String UrlServices = utilities.GetUrl() + "search_business";

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
                                        lineaerMain.setVisibility(View.VISIBLE);
                                        lineaerNoData.setVisibility(View.GONE);
                                        subServiceModel.clear();


                                        for (int i = 0; i < contentsArray.length(); i++) {
                                            JSONObject jsondata = contentsArray.getJSONObject(i);
                                            subListAdrz = "";
                                            String serviceName = jsondata.getString("business_name");
                                            String serviceID = jsondata.getString("id");
                                            String serviceImg = jsondata.getString("photo");
                                            String serviceIDS = jsondata.getString("service");
                                            String rating = jsondata.getString("rating");
                                            Hawk.put("rating", rating);
                                            if (logedIn == 1) {
                                                isfavourite = jsondata.getString("isfavourite");
                                                //Hawk.put("isfavourite",isfavourite);
                                            } else {
                                                isfavourite = "";
                                            }
                                            String building_number = jsondata.getString("building_number");
                                            String street_number = jsondata.getString("street_number");
                                            String street_name = jsondata.getString("street_name");
                                            String locations = jsondata.getString("locations");
                                            String longitude = jsondata.getString("longitude");
                                            String latitude = jsondata.getString("latitude");

                                            if (!jsondata.isNull("business_categories")) {
                                                JSONObject businessCategoriesJsonObj = jsondata.getJSONObject("business_categories");
                                                if (businessCategoriesJsonObj.length() > 0) {
                                                    subServiceDescription = businessCategoriesJsonObj.getString("description");

                                                }
                                            }





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
                                            if (locations != null && !locations.isEmpty() && !locations.equals("null")) {
                                                if (subListAdrz.length() > 0) {
                                                    subListAdrz = subListAdrz + "," + locations;
                                                } else {
                                                    subListAdrz = subListAdrz + locations;
                                                }

                                            }


                                            subServiceModel.add(new SubServiceModel(serviceID, serviceName, serviceImg, rating, subListAdrz, serviceIDS, selectedMainServiceID, subServiceDescription, latitude, longitude, isfavourite, locations));

                                        }
                                    } else {
                                        lineaerMain.setVisibility(View.GONE);
                                        lineaerNoData.setVisibility(View.VISIBLE);
                                    }
                                    try {
                                        searchAdapter = new SearchAdapter(getActivity(), subServiceModel);
                                        recyclerViewSearch.setAdapter(searchAdapter);
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
                params.put("keyword", keyword);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
