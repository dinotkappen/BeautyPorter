package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.example.thebeautyporterapp.Adapter.Tab.ViewPagerAdapter;
import com.example.thebeautyporterapp.Adapter.Tab.TabAdapter;
import com.example.thebeautyporterapp.Model.ServiceDetailBannerModel;
import com.example.thebeautyporterapp.Model.SubServiceModel;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cc.cloudist.acplibrary.ACProgressFlower;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgAdd;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;

public class ServiceDetailFragment extends Fragment {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager, tab_viewpager, bannerPager;
    ViewPagerAdapter adapter_tab;
    View rootView;
    ArrayList bannerLink = new ArrayList();
    String access_token, user_id;
    int logedIn;
    String selecteSubServiceID, subServiceName;
    Utilities utilities;
    LinearLayout linearRating;
    ImageView serviceDetailBack, serviceDetailWishListSelected, serviceDetailWishListNotSelected;
    TextView txtServiceDetailTitle, txtRating;
    ArrayList<ServiceDetailBannerModel> serviceDetailBannerModel = new ArrayList<ServiceDetailBannerModel>();
    String isfavourite;
    private static int NUM_PAGES_BANNER = 0;
    private static int currentPage_Items = 0;
    private static int currentPage_Banner = 0;
    ArrayList<SubServiceModel> subServiceModel = new ArrayList<SubServiceModel>();
    String selectedBusinessRating;
    String availableModes;
    ImageView imgSpa,imgHome;

    public ServiceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_service_detail, container, false);
        txtServiceDetailTitle = (TextView) rootView.findViewById(R.id.txtServiceDetailTitle);
        txtRating = (TextView) rootView.findViewById(R.id.txtRating);
        imgSpa=(ImageView)rootView.findViewById(R.id.imgSpa);
        imgHome=(ImageView)rootView.findViewById(R.id.imgHome);
        imgSpa.setVisibility(View.GONE);
        imgHome.setVisibility(View.GONE);
        viewPager = rootView.findViewById(R.id.view_pager_screens);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        bannerPager = rootView.findViewById(R.id.pager_banner);
        linearRating = (LinearLayout) rootView.findViewById(R.id.linearRating);
        selecteSubServiceID = Hawk.get("selecteSubServiceID", "");
        Log.v("selecteSubServiceID", selecteSubServiceID);
        if (getArguments() != null) {
            availableModes = getArguments().getString("availableModes");
            Log.v("availableModes", availableModes);
        }
        else
        {

            availableModes = Hawk.get("availableModes", availableModes);
            Log.v("availableModes", availableModes);
        }

        Hawk.put("availableModes", availableModes);
        if (availableModes.contains("Home Service")) {
           imgHome.setBackgroundResource(R.mipmap.home_circle);
          imgHome.setVisibility(View.VISIBLE);


        }
        if (availableModes.contains("Salon, Spa & Clinics")) {
           imgSpa.setVisibility(View.VISIBLE);
          imgSpa.setBackgroundResource(R.mipmap.work);

        }
        if (availableModes.contains("Stylists")) {
           imgSpa.setVisibility(View.GONE);
           imgHome.setVisibility(View.GONE);

        }
        user_id = Hawk.get("user_id", "");
        access_token = Hawk.get("access_token", "");
        subServiceName = Hawk.get("subServiceName", "");
        selectedBusinessRating = Hawk.get("selectedBusinessRating", selectedBusinessRating);
        if (selectedBusinessRating != null && !selectedBusinessRating.isEmpty() && !selectedBusinessRating.equals("null")) {


            String[] separated = selectedBusinessRating.split(",");
            if (separated[0] != null && !separated[0].isEmpty() && !separated[0].equals("null")) {
                txtRating.setText(separated[0] + " Ratings");
            } else {
                txtRating.setText("0 Ratings");
            }


        } else {
            txtRating.setText("0");
        }

        isfavourite = Hawk.get("isfavourite", isfavourite);
        Log.v("isfavourite", "" + isfavourite);
        subServiceModel = Hawk.get("subServiceModel", subServiceModel);

        imgAdd.setVisibility(View.GONE);
        linearActionBar.setVisibility(View.GONE);
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        if (subServiceName != null && !subServiceName.isEmpty() && !subServiceName.equals("null")) {
            txtServiceDetailTitle.setText(subServiceName);
        }

        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);
        serviceDetailBack = (ImageView) rootView.findViewById(R.id.serviceDetailBack);
        serviceDetailWishListSelected = (ImageView) rootView.findViewById(R.id.serviceDetailWishListSelected);
        serviceDetailWishListNotSelected = (ImageView) rootView.findViewById(R.id.serviceDetailWishListNotSelected);
        if (isfavourite.equals("1")) {
            serviceDetailWishListNotSelected.setVisibility(View.GONE);
            serviceDetailWishListSelected.setVisibility(View.VISIBLE);
        } else {
            serviceDetailWishListNotSelected.setVisibility(View.VISIBLE);
            serviceDetailWishListSelected.setVisibility(View.GONE);
        }

        serviceDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SubMenuFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        serviceDetailWishListSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addOrDlete = 0;
                AddDeleteWishListApi(addOrDlete);
            }
        });

        serviceDetailWishListNotSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addOrDlete = 1;
                AddDeleteWishListApi(addOrDlete);
            }
        });

        adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new TabAboutUsFragment(), getResources().getString(R.string.about_us));
        adapter.addFragment(new TabServicesFragment(), getResources().getString(R.string.services));
        adapter.addFragment(new TabOfferFragment(), getResources().getString(R.string.offer));
        adapter.addFragment(new TabTimeFragment(), getResources().getString(R.string.hours));
        adapter.addFragment(new TabMapFragment(), getResources().getString(R.string.map));

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);


        int[] tabIcons = {
                R.mipmap.about_black,
                R.mipmap.service_black,
                R.mipmap.offer_black,
                R.mipmap.time_black,
                R.mipmap.location_black
        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);

        Hawk.init(getActivity())
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(getActivity()))
                .setLogLevel(LogLevel.FULL)
                .build();

        linearRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RatingFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        loadSubServiceListApiEN();
        return rootView;
    }

    public void banner_load() {
        adapter_tab = new ViewPagerAdapter(getActivity(), bannerLink);
        bannerPager.setAdapter(adapter_tab);

        NUM_PAGES_BANNER = bannerLink.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage_Banner == NUM_PAGES_BANNER) {
                    currentPage_Banner = 0;
                }
                bannerPager.setCurrentItem(currentPage_Banner++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 6000, 6000);

        // Pager listener over indicator


    }

    private void loadSubServiceListApiEN() {

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
                                        serviceDetailBannerModel.clear();


                                        for (int i = 0; i < contentsArray.length(); i++) {

                                            JSONObject jsondata = contentsArray.getJSONObject(i);
                                            String serviceID = jsondata.getString("id");
                                            String photo = jsondata.getString("photo");
                                            String bio = jsondata.getString("bio");
                                            String contact_no = jsondata.getString("contact_no");
                                            String building_number = jsondata.getString("building_number");
                                            String street_number = jsondata.getString("street_number");
                                            String street_name = jsondata.getString("street_name");
                                            String zone_number = jsondata.getString("zone_number");
                                            String locations = jsondata.getString("locations");
                                            String website = jsondata.getString("website");


                                            JSONArray business_imagesArray = jsondata.getJSONArray("business_images");
                                            for (int j = 0; j < business_imagesArray.length(); j++) {
                                                JSONObject business_imagesJsonObj = business_imagesArray.getJSONObject(j);
                                                String business_id = business_imagesJsonObj.getString("business_id");
                                                String bannerImage = business_imagesJsonObj.getString("image");
                                                if (selecteSubServiceID.equals(business_id)) {
                                                    bannerLink.add(bannerImage);


                                                }
                                            }

                                        }
                                    }
                                    try {
                                        banner_load();
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

    public void AddDeleteWishListApi(final int addOrDlete) {

        subServiceModel = Hawk.get("subServiceModel", subServiceModel);
        String hj = selecteSubServiceID;
        String UrlServices = utilities.GetUrl() + "favourite";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                JSONObject obj = new JSONObject(response);

                                String status = obj.getString("status");
                                if (status.equals("success")) {

                                    for (int i = 0; i < subServiceModel.size(); i++) {
                                        String hj = subServiceModel.get(i).getSubServiceID();
                                        if (subServiceModel.get(i).getSubServiceID().equals(selecteSubServiceID)) {
                                            if (addOrDlete == 1) {
                                                subServiceModel.get(i).setIsfavourite("1");
                                                Hawk.put("isfavourite", "1");
                                                serviceDetailWishListNotSelected.setVisibility(View.GONE);
                                                serviceDetailWishListSelected.setVisibility(View.VISIBLE);
                                            } else {
                                                subServiceModel.get(i).setIsfavourite("0");
                                                isfavourite = Hawk.get("isfavourite", "");
                                                Hawk.put("isfavourite", "0");
                                                serviceDetailWishListNotSelected.setVisibility(View.VISIBLE);
                                                serviceDetailWishListSelected.setVisibility(View.GONE);
                                            }

                                        }
                                    }

                                    try {
                                        Hawk.put("subServiceModel", subServiceModel);

                                    } catch (Exception ex) {
                                        ex.printStackTrace();

                                    }
                                } else {

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
                params.put("favourite_status", "" + addOrDlete);
                params.put("business_id", selecteSubServiceID);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
