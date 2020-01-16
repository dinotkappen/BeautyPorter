package com.example.thebeautyporterapp.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.example.thebeautyporterapp.Adapter.SubMenuAdapter;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessServicesModel;
import com.example.thebeautyporterapp.Model.ServicesModel.Content;
import com.example.thebeautyporterapp.Model.ServicesModel.Service;
import com.example.thebeautyporterapp.Model.SubServiceModel;
import com.example.thebeautyporterapp.Other.RecyclerItemClickListener;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgAdd;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubMenuFragment extends Fragment {

    View rootView;
    int logedIn = 0;
    Utilities utilities;
    String subListAdrz = "";
    String user_id, access_token;
    private SubMenuAdapter subMenuAdapter;
    String mainServiceName, mainServiceID;
    LinearLayout lineaerMain, lineaerNoData;
    private RecyclerView recyclerViewSubMenu;
    String isfavourite, subServiceDescription;
    ArrayList<String> bannerImagesList = new ArrayList<>();
    ArrayList<SubServiceModel> subServiceModel = new ArrayList<SubServiceModel>();

    List<Service> filterServiceModelList = new ArrayList<>();
    List<Content> list_id = new ArrayList<>();
    public SubMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sub_menu, container, false);

        logedIn = Hawk.get("logedIn", 0);
        user_id = Hawk.get("user_id", "");
        access_token = Hawk.get("access_token", "");
        Log.v("access_token", access_token);
        mainServiceID = Hawk.get("mainServiceID", "");
        mainServiceName = Hawk.get("mainServiceName", "");


        lineaerMain = (LinearLayout) rootView.findViewById(R.id.lineaerMain);
        lineaerNoData = (LinearLayout) rootView.findViewById(R.id.lineaerNoData);
        lineaerNoData.setVisibility(View.GONE);
        lineaerMain.setVisibility(View.VISIBLE);



        linearActionBar.setVisibility(View.VISIBLE);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        if (mainServiceName != null && !mainServiceName.isEmpty() && !mainServiceName.equals("null")) {
            txt_actionbar_Title.setText(mainServiceName);
        }

        imgFilter.setVisibility(View.VISIBLE);
        imgSearch.setVisibility(View.GONE);
        imgAdd.setVisibility(View.GONE);
        imgAdd.setVisibility(View.GONE);

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FilterFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        recyclerViewSubMenu = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewSubMenu.setLayoutManager(mLayoutManager);
        recyclerViewSubMenu.setItemAnimator(new DefaultItemAnimator());

        recyclerViewSubMenu.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewSubMenu, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Fragment fragment = new ServiceDetailFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        if (mainServiceID != null && !mainServiceID.isEmpty() && !mainServiceID.equals("null")) {

            loadSubServiceListApi();
           // loadSubServiceListApiEN();
        }

        return rootView;
    }

    private void loadSubServiceListApi() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String UrlServices = utilities.GetUrl() + "search_business";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        Log.e("response_pkb", response.toString());
                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                JSONObject obj = new JSONObject(response);

                                String status = obj.getString("status");
                                if (status.equals("success")) {

                                    JSONArray contentsArray = obj.getJSONArray("content");

                                    if (contentsArray.length() > 0) {
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

                                            if (!jsondata.isNull("BusinessServices")) {
                                                JSONArray jsonArrayBusinessServices = jsondata.getJSONArray("BusinessServices");
//
                                                if (jsonArrayBusinessServices.length() > 0) {
                                                    for(int j=0;j<jsonArrayBusinessServices.length();j++)
                                                    {
                                                        JSONObject jsonobjBusinessServices = jsonArrayBusinessServices.getJSONObject(j);

                                                        if (!jsonobjBusinessServices.isNull("service")) {
                                                            JSONArray serviceJSONARRAY = jsonobjBusinessServices.getJSONArray("service");
                                                            for(int y=0;y<serviceJSONARRAY.length();y++)
                                                            {
                                                                JSONObject jsonobjservice = serviceJSONARRAY.getJSONObject(y);
                                                                String business_id=jsonobjservice.getString("business_id");
                                                                String amount = jsonobjservice.getString("price");
                                                                String mode = jsonobjservice.getString("mode");
                                                                Service filterServiceModel = new Service();
                                                                filterServiceModel.setPrice(amount);
                                                                filterServiceModel.setMode(mode);
                                                                filterServiceModel.setBusinessId(business_id);
                                                                filterServiceModelList.add(filterServiceModel);
                                                            }

                                                        }
                                                    }

                                                    Hawk.put("filterServiceModelList",filterServiceModelList);

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

                                            JSONArray business_imagesJsonArray = jsondata.getJSONArray("business_images");
                                            if (business_imagesJsonArray.length() > 0) {
                                                for (int p = 0; p < business_imagesJsonArray.length(); p++) {
                                                    JSONObject businessImageJsonObjec = business_imagesJsonArray.getJSONObject(p);
                                                    String bannerImages = businessImageJsonObjec.getString("image");

                                                    bannerImagesList.add(bannerImages);
                                                    Hawk.put("bannerImagesList", bannerImagesList);
                                                }
                                            }
                                            if (serviceIDS.contains(mainServiceID)) {
                                                subServiceModel.add(new SubServiceModel(serviceID, serviceName, serviceImg, rating, subListAdrz, serviceIDS, mainServiceID, subServiceDescription, latitude, longitude, isfavourite, locations));

                                            }
                                            subListAdrz = "";
                                        }

                                    }
                                    try {

                                        subMenuAdapter = new SubMenuAdapter(getActivity(), subServiceModel);
                                        recyclerViewSubMenu.setAdapter(subMenuAdapter);
                                        Hawk.put("subServiceModel", subServiceModel);
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
                if (logedIn == 1) {
                    params.put("user_id", user_id);
                }


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void loadSubServiceListApiEN() {

        String UrlServices = utilities.GetUrl() + "search_business";
        Log.e("url",UrlServices);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                    @Override
                    public void onResponse(String response) {

                        Log.e("services",response);
                        BusinessServicesModel services = new BusinessServicesModel();// response.body();
                        Gson gson = new Gson();
                        JsonParser parser = new JsonParser();
                        JsonObject o = parser.parse(response).getAsJsonObject();
                        Log.e("banner_pkb",o.toString());
                        services = gson.fromJson(o, BusinessServicesModel.class);
                        Log.e("status_pkb",services.toString());
                        if (services.getStatus().equals("success"))
                        {
                            list_id = services.getContent();
                            Hawk.put("searchBusineess",list_id);

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
                }){


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
                if (logedIn == 1) {
                    params.put("user_id", user_id);
                }


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
