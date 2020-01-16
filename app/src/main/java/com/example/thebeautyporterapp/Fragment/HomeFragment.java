package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.crashlytics.android.Crashlytics;
import com.example.thebeautyporterapp.Adapter.Banner_Img_Adapter;
import com.example.thebeautyporterapp.Adapter.ViewPagerAdapter;
import com.example.thebeautyporterapp.Model.Banner_Img_Model;
import com.example.thebeautyporterapp.Model.BookingDetailsModel;
import com.example.thebeautyporterapp.Model.Service_List_Model;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.commons.collections4.ListUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


public class HomeFragment extends Fragment {

    View rootView;
    private ViewPager viewPager;
    private Banner_Img_Adapter obj_item_main_adapter_;
    ArrayList<Banner_Img_Model> data_Banner_Images = new ArrayList<Banner_Img_Model>();
    private static ViewPager mPager_Banner;
    private static int NUM_PAGES_BANNER = 0;
    private static int currentPage_Items = 0;
    private static int currentPage_Banner = 0;
    FragmentManager childFragMang;
    ArrayList<List<Service_List_Model>> arrayOfArray = new ArrayList<>();
    int totalPageCount;
    ArrayList<Service_List_Model> service_List_Arraylist = new ArrayList<Service_List_Model>();
    Utilities utilities;
    int logedIn = 0;
    String user_id,access_token;
    ArrayList<BookingDetailsModel> itemList = new ArrayList<>();
    ArrayList<String> alreadyExistList = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView= inflater.inflate(R.layout.fragment_home, container, false);
        logedIn=Hawk.get("logedIn",0);
        user_id=Hawk.get("user_id","");
        access_token=Hawk.get("access_token","");
        Hawk.put("mainServiceName","");
        Hawk.put("mainServiceID","");
        itemList=Hawk.get("itemList", itemList);
        alreadyExistList=Hawk.get("alreadyExistList",alreadyExistList) ;
        itemList.clear();
        alreadyExistList.clear();
        Hawk.put("itemList",itemList);
        Hawk.put("alreadyExistList",alreadyExistList);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);// no of fragments
        childFragMang = getChildFragmentManager();

        linearActionBar.setVisibility(View.VISIBLE);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.VISIBLE);
        backArrow.setVisibility(View.GONE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.BeautyPorter));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.VISIBLE);
        imgAdd.setVisibility(View.GONE);
//        Button crashButton = new Button(getActivity());
//        crashButton.setText("Crash!");
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Crashlytics.getInstance().crash(); // Force a crash
//            }
//        });

//        getActivity().addContentView(crashButton, new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });


        loadBannerListApi();
        loadServiceList();


        return rootView;
    }
    private void setupViewPager(ViewPager viewPager) {
        try {
            ViewPagerAdapter adapter = new ViewPagerAdapter(childFragMang);
             totalPageCount = service_List_Arraylist.size() / 6;
            int count = service_List_Arraylist.size();
            int reminder = count % 6;
            int quot = count / 6;

            if (reminder > 0) {
                totalPageCount = quot + 1;
            }


            for (List<Service_List_Model> partition : ListUtils.partition(service_List_Arraylist, 6)) {
                arrayOfArray.add(partition);
            }
            Log.e("array_value1",""+arrayOfArray.size());
//            Hawk.put("array", "en");
            for (int i = 0; i < totalPageCount; i++) {
                adapter.addFragment(new Home_Item_Tab_Fragment(arrayOfArray.get(i)), "");
                viewPager.setAdapter(adapter);
            }

            CirclePageIndicator indicator = (CirclePageIndicator)
                    rootView.findViewById(R.id.indicator_items);
            indicator.setViewPager(viewPager);
            final float density = getResources().getDisplayMetrics().density;

            //Set circle indicator radius
            indicator.setRadius(4 * density);
            //Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage_Items == totalPageCount) {
                        currentPage_Items = 0;
                    }
                    viewPager.setCurrentItem(currentPage_Items++, true);
                }
            };
        } catch (Exception ex) {
            String msg = ex.getMessage().toString();
            Log.v("msg_setupViewPager", msg);
        }

    }
    private void setBannerImage() {

        try {
            obj_item_main_adapter_ = new Banner_Img_Adapter(getActivity(), data_Banner_Images);
            mPager_Banner = (ViewPager) rootView.findViewById(R.id.pager_banner);
            if (getActivity() != null) {
                mPager_Banner.setAdapter(obj_item_main_adapter_);
            }


            CirclePageIndicator indicator = (CirclePageIndicator)
                    rootView.findViewById(R.id.indicator_banner);

            indicator.setViewPager(mPager_Banner);


            final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
            indicator.setRadius(4 * density);

            NUM_PAGES_BANNER = data_Banner_Images.size();

            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage_Banner == NUM_PAGES_BANNER) {
                        currentPage_Banner = 0;
                    }
                    mPager_Banner.setCurrentItem(currentPage_Banner++, true);
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
            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage_Banner = position;

                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });
        } catch (Exception ex) {
            String s = ex.getMessage().toString();
            String h = s;
        }

    }
    public void onResume() {
        super.onResume();

        setupViewPager(viewPager);
    }



    private void loadServiceList() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String UrlServices = utilities.GetUrl() + "services";

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
                                        service_List_Arraylist.clear();

                                        for (int i = 0; i < contentsArray.length(); i++) {
                                            JSONObject jsondata = contentsArray.getJSONObject(i);
                                            String serviceName = jsondata.getString("name");
                                            String serviceID = jsondata.getString("id");
                                            String serviceImg = jsondata.getString("icon");

                                            service_List_Arraylist.add(new Service_List_Model(serviceID, serviceName, serviceImg));

                                        }
                                        setupViewPager(viewPager);
                                    }
                                    try {

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

    private void loadBannerListApi() {

        String UrlBanners = utilities.GetUrl() + "dashboard_image";
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlBanners,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);

                                String status = obj.getString("status");
                                if (status.equals("success")) {
                                    //so here we are getting that json array
                                    JSONArray bannersArray = obj.getJSONArray("content");

                                    if (bannersArray.length() > 0) {
                                        data_Banner_Images.clear();
                                        //now looping through all the elements of the json array
                                        for (int i = 0; i < bannersArray.length(); i++) {
                                            //getting the json object of the particular index inside the array
                                            JSONObject jsondata = bannersArray.getJSONObject(i);
                                            String imageUrl = jsondata.getString("image");
                                            Log.v("imageUrl",imageUrl);

                                            data_Banner_Images.add(new Banner_Img_Model(""+i,imageUrl));


                                        }
                                    }
                                    setBannerImage();
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


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

}
