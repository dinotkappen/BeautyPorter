package com.example.thebeautyporterapp.Fragment;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.thebeautyporterapp.Adapter.ServicesAdapter.ServicesItemAdapter;
import com.example.thebeautyporterapp.Adapter.ServicesAdapter.ServicesTimeAdapter;
import com.example.thebeautyporterapp.Adapter.ServicesAdapter.ServicesaddPersionAdapter;
import com.example.thebeautyporterapp.Model.AppoinmentModel;
import com.example.thebeautyporterapp.Model.GuestOrderModel;
import com.example.thebeautyporterapp.Model.ServicesItemModel;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessService;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessServicesModel;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessStaff;
import com.example.thebeautyporterapp.Model.ServicesModel.Content;
import com.example.thebeautyporterapp.Model.ServicesModel.Service;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;


public class TabServicesFragment extends Fragment {

    int logedIn, business_id;
    View rootView;
    Utilities utilities;
    CardView btnCardBookService;
    RecyclerView recyType, recy_persion;
    static RecyclerView recyTime;
    String selecteSubServiceID, selectedLang, user_id, access_token;
    ArrayList<AppoinmentModel> appoinmentModel = new ArrayList<AppoinmentModel>();
    ArrayList<ServicesItemModel> servicType = new ArrayList<>();
    ServicesItemAdapter adpterType;
    static ServicesTimeAdapter adpterTime;
    List<Content> list_id = new ArrayList<>();
    public static List<Content> searchBusinessGuest = new ArrayList<>();
    public static List<BusinessService> businessServiceList = new ArrayList<>();
    public static List<Service> serviceModelList = new ArrayList<>();
    List<BusinessService> bussinessService = new ArrayList<>();
    List<BusinessService> bussinessServicePopModel = new ArrayList<>();
    List<Service> servicesList = new ArrayList<>();
    static List<Service> servicesList1 = new ArrayList<>();
    static List<Service> servicesListSelected = new ArrayList<>();
    String img_url;
    static FragmentActivity activit;
    ServicesaddPersionAdapter adapterName;
    ArrayList namList = new ArrayList();
    ImageView imgAdd;
    EditText editName;
    List<BusinessStaff> staffList = new ArrayList<>();
    List<Service> subServicePopUpModel = new ArrayList<>();
    ArrayList<String> alreadyExistList = new ArrayList<>();
    ArrayList<GuestOrderModel> guestList = new ArrayList<>();
    public static Dialog guestServiceDialog;
    public static CardView btnCardGuestOK;
    public static EditText edtService, edtSubService;
    public static Dialog serviceMainDialog;
    public static ListView listViewServiceMain;
    public static List<String> listserviceMainNames;
    public static List<String> listServiceMainIDS;

    public static Dialog subServiceDialog;
    public static ListView listViewSubService;
    public static List<String> subServiceNames;
    public static List<String> subServiceIDS;
    public static List<String> subServicePrice;
    LinearLayout len_spa, linear_Home, linearModes;
    String selectedMode;
    String availableModes;
    TextView txtModeHeading;
    Boolean homeServceFlag=false, spaServiceFlag=false,stylistsFlag=false;


    public static String selectedMainServiceID, selectedMainServiceName, selectedSubServiceID, selectedSubServiceName, selectedServiceCost, guestName;

    public TabServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.tab_services, container, false);
        activit = getActivity();
        logedIn = Hawk.get("logedIn", 0);
        user_id = Hawk.get("user_id", "");
        access_token = Hawk.get("access_token", "");
        availableModes = Hawk.get("availableModes", availableModes);

        selecteSubServiceID = Hawk.get("selecteSubServiceID");
        Log.v("selecteSubServiceIDTab", selecteSubServiceID);
        business_id = Integer.parseInt(selecteSubServiceID);
        Hawk.put("selectedTimeList", "");
        Hawk.put("specialRequest", "");
        Hawk.put("selectedDate", "");


//        itemList = Hawk.get("itemList", itemList);
//        alreadyExistList=Hawk.get("alreadyExistList",alreadyExistList);
//        itemList.clear();
//        alreadyExistList.clear();
//        Hawk.put("itemList", itemList);
//        Hawk.put("alreadyExistList", alreadyExistList);

        btnCardBookService = rootView.findViewById(R.id.btnCardBookService);
        len_spa = rootView.findViewById(R.id.len_spa);
        linear_Home = rootView.findViewById(R.id.linear_Home);
        linearModes = rootView.findViewById(R.id.linearModes);
        txtModeHeading = rootView.findViewById(R.id.txtModeHeading);

        if (availableModes.contains("Salon, Spa & Clinics")) {
            spaServiceFlag = true;
        }
        if (availableModes.contains("Home Service")) {
            homeServceFlag = true;
        }
        if(availableModes.contains("Stylists"))
        {
            stylistsFlag=true;
        }


        if(stylistsFlag==true)
        {
            txtModeHeading.setVisibility(View.GONE);
            linearModes.setVisibility(View.GONE);
        }
        else {
            if (spaServiceFlag==true && homeServceFlag == true) {

                txtModeHeading.setVisibility(View.VISIBLE);
                linearModes.setVisibility(View.VISIBLE);
            }
            else {
                txtModeHeading.setVisibility(View.GONE);
                linearModes.setVisibility(View.GONE);
            }
        }


        recyType = rootView.findViewById(R.id.recy_type);
        recyTime = rootView.findViewById(R.id.recy_time);
        //  recy_persion = rootView.findViewById(R.id.recy_persion);
        // imgAdd = rootView.findViewById(R.id.img_add);
        //editName = rootView.findViewById(R.id.edit_name);


        recyType.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adpterType = new ServicesItemAdapter(getActivity(), bussinessService);
        recyType.setAdapter(adpterType);

        recyTime.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adpterTime = new ServicesTimeAdapter(getActivity(), servicesList1);
        recyTime.setAdapter(adpterTime);

        guestServiceDialog = new Dialog(getActivity());
        guestServiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        guestServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        guestServiceDialog.setCancelable(false);
        guestServiceDialog.setContentView(R.layout.dialog_guest_layout);
        btnCardGuestOK = (CardView) guestServiceDialog.findViewById(R.id.btnCardOK);
        edtService = (EditText) guestServiceDialog.findViewById(R.id.edtService);
        edtSubService = (EditText) guestServiceDialog.findViewById(R.id.edtSubService);
        btnCardGuestOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtService.setError(null);
                if (listserviceMainNames.size() > 0) {
                    if (selectedMainServiceName != null && !selectedMainServiceName.isEmpty() && !selectedMainServiceName.equals("null")) {
                        if (selectedMainServiceID != null && !selectedMainServiceID.isEmpty() && !selectedMainServiceID.equals("null")) {
                            if (selectedSubServiceName != null && !selectedSubServiceName.isEmpty() && !selectedSubServiceName.equals("null")) {

                                if (selecteSubServiceID != null && !selecteSubServiceID.isEmpty() && !selecteSubServiceID.equals("null")) {
                                    guestServiceDialog.dismiss();
                                    GuestOrderModel guestOrderModel = new GuestOrderModel();

                                    guestOrderModel.setGuestID("1");
                                    guestOrderModel.setBookedDate("");
                                    guestOrderModel.setServiceId(selecteSubServiceID);
                                    guestOrderModel.setServiceName(selectedSubServiceName);
                                    guestOrderModel.setCost(selectedServiceCost);
                                    guestOrderModel.setGuestName(guestName);


                                    guestList.add(guestOrderModel);
                                    edtService.setError(null);
                                } else {
                                    edtService.setError("Please select a sub service");
                                }
                            } else {
                                edtService.setError("Please select a sub service");
                            }
                        } else {
                            edtService.setError("Please select a service");
                        }
                    } else {
                        edtService.setError("Please select a service");
                    }
                } else {
                    guestServiceDialog.dismiss();

                }


            }
        });


        subServiceDialog = new Dialog(getActivity());
        subServiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        subServiceDialog.setContentView(R.layout.subservice_dialog_layout);
        subServiceDialog.setCanceledOnTouchOutside(false);
        subServiceNames = new ArrayList<>();
        subServiceIDS = new ArrayList<>();
        subServicePrice = new ArrayList<>();
        listViewSubService = (ListView) subServiceDialog.findViewById(R.id.listViewSubService);


        serviceMainDialog = new Dialog(getActivity());
        serviceMainDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        serviceMainDialog.setContentView(R.layout.dilg_service_main);
        serviceMainDialog.setCanceledOnTouchOutside(false);
        listserviceMainNames = new ArrayList<>();
        listServiceMainIDS = new ArrayList<>();
        listViewServiceMain = (ListView) serviceMainDialog.findViewById(R.id.listViewServiceMain);
        // Create an ArrayAdapter from List
        ArrayAdapter<String> adapterMainService = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, listserviceMainNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(getResources().getColor(R.color.colorBlack));

                // Generate ListView Item using TextView
                return view;
            }
        };

        listViewServiceMain.setAdapter(adapterMainService);


//        // Capture ListView item click
        listViewServiceMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                selectedMainServiceName = (String) listViewServiceMain.getItemAtPosition(position);
//                serviceList.clear();
//                subServiceNames.clear();
//                subServiceIDS.clear();

                if (selectedMainServiceName != null && !selectedMainServiceName.isEmpty() && !selectedMainServiceName.equals("null")) {
                    edtService.setText(selectedMainServiceName);
                    selectedMainServiceID = listServiceMainIDS.get(position);


                    for (int j = 0; j < businessServiceList.size(); j++) {


                        String serviceId = "" + businessServiceList.get(j).getId();
                        if (selectedMainServiceID.equals(serviceId)) {

                            serviceModelList = businessServiceList.get(j).getService();

                            if (serviceModelList.size() > 0) {
                                for (int k = 0; k < serviceModelList.size(); k++) {
                                    // String subServiceName = serviceModelList.get(k).getAmount();
                                    subServiceNames.add(serviceModelList.get(k).getServiceName());
                                    subServiceIDS.add("" + serviceModelList.get(k).getId());
                                    //  subServicePrice.add("" + serviceModelList.get(k).getAmount());
                                    if (subServiceNames.size() > 0) {
                                        edtSubService.setText(subServiceNames.get(0));
//                                        selectedServiceCost=subServicePrice.get(0);
                                        //            edtSubService.setText("");
                                    }

                                }

                            }
                        }


                    }


                    //----------

                }

//                editStaff.setText(str_CustomerType);
//                selectedStaffId = listServiceMainIDS.get(position);
//                selectedStaffIDList.add(selectedStaffId);
//                Log.v("staff_id", selectedStaffId);
//                Hawk.put("selectedStaffIDList", selectedStaffIDList);
//                Hawk.get("selectedStaffId", selectedStaffId);


                serviceMainDialog.dismiss();


            }
        });


        //***********************************************************************************
//*********************************************************************************************


        // Create an ArrayAdapter from List
        ArrayAdapter<String> adaptersubService = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, subServiceNames) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(getResources().getColor(R.color.colorBlack));

                // Generate ListView Item using TextView
                return view;
            }
        };

        listViewSubService.setAdapter(adaptersubService);


//        // Capture ListView item click
        listViewSubService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                selectedSubServiceName = (String) listViewSubService.getItemAtPosition(position);


                if (selectedSubServiceName != null && !selectedSubServiceName.isEmpty() && !selectedSubServiceName.equals("null")) {
                    edtSubService.setText(selectedSubServiceName);
                    selectedSubServiceID = subServiceIDS.get(position);
                    selectedServiceCost = subServicePrice.get(position);


                }


                subServiceDialog.dismiss();


            }
        });
        edtService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listserviceMainNames.size() > 0) {
                    serviceMainDialog.show();

                    subServiceNames.clear();
                    subServiceIDS.clear();
                    subServicePrice.clear();
                    edtSubService.setText("");

                } else {
                    serviceMainDialog.dismiss();
                    subServiceDialog.dismiss();
                    guestServiceDialog.dismiss();
                    Toast.makeText(getContext(), "Sorry no data available", Toast.LENGTH_SHORT).show();
                }


            }
        });
        edtSubService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listserviceMainNames.size() > 0) {
                    if (subServiceNames.size() > 0) {
                        subServiceDialog.show();
                    } else {
                        serviceMainDialog.dismiss();
                        subServiceDialog.dismiss();
                        guestServiceDialog.dismiss();
                        Toast.makeText(getContext(), "Sorry no data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    serviceMainDialog.dismiss();
                    subServiceDialog.dismiss();
                    guestServiceDialog.dismiss();
                    Toast.makeText(getContext(), "Sorry no data available", Toast.LENGTH_SHORT).show();
                }

            }
        });
//
//        recy_persion.setLayoutManager(new GridLayoutManager(getContext(), 1));
//        adapterName = new ServicesaddPersionAdapter(getActivity(), namList);
//        recy_persion.setAdapter(adapterName);

//        imgAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                if (editName.getText().toString().equals("")) {
//                    editName.setError(getString(R.string.ad_anme));
//                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                } else {
//                    Log.v("guestListTSF", "" + guestList.size());
//                    Log.v("namList", "" + namList.size());
//                    if (guestList.size() == namList.size()) {
//                        namList.add(editName.getText().toString());
//                        adapterName = new ServicesaddPersionAdapter(getActivity(), namList);
//                        recy_persion.setAdapter(adapterName);
//                        editName.setText("");
//                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//                    } else {
//                        Toast.makeText(getContext(), "Please select a service for guest", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//        });

//        recyTime.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyTime, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//                servicesListSelected =
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        }));

        len_spa.setBackgroundResource(R.drawable.round_corner);
        linear_Home.setBackgroundResource(R.drawable.round_corner_white);
        Hawk.put("selectedMode", "Salon");
        loadSubServiceListApiEN();

        len_spa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                len_spa.setBackgroundResource(R.drawable.round_corner);
                linear_Home.setBackgroundResource(R.drawable.round_corner_white);
                Hawk.put("selectedMode", "Spa & Clinics");
                adpterTime.notifyDataSetChanged();

            }
        });
        linear_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_Home.setBackgroundResource(R.drawable.round_corner);
                len_spa.setBackgroundResource(R.drawable.round_corner_white);
                Hawk.put("selectedMode", "Home Service");
                adpterTime.notifyDataSetChanged();

            }
        });


        btnCardBookService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guestList.size() == namList.size()) {
                    String selectedServiceName = Hawk.get("selectedServiceName", "");
                    int selectedServiceID = Hawk.get("selectedServiceID", 0);
                    if (selectedServiceName != null && !selectedServiceName.isEmpty() && !selectedServiceName.equals("null")) {
                        if (selectedServiceID != 0) {
                            if (servicesListSelected.size() > 0) {
                                Fragment fragment = DateTimeFragment.newInstance(servicesListSelected);
                                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                Hawk.put("guestList", guestList);
//                            Log.v("guestListTSF",""+guestList.size());
//                            Log.v("namList",""+namList.size());

                            } else {
                                Toast.makeText(getActivity(), "Service not availabe..", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Service not availabe..", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Service not availabe..", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please select a service for guest", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return rootView;
    }

    private void loadSubServiceListApiEN() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String UrlServices = utilities.GetUrl() + "search_business";
        Log.e("url", UrlServices);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                    @Override
                    public void onResponse(String response) {

                        Log.e("services", response);
                        BusinessServicesModel services = new BusinessServicesModel();// response.body();
                        Gson gson = new Gson();
                        JsonParser parser = new JsonParser();
                        JsonObject o = parser.parse(response).getAsJsonObject();
                        Log.e("banner_pkb", o.toString());
                        services = gson.fromJson(o, BusinessServicesModel.class);
                        Log.e("status_pkb", services.toString());
                        if (services.getStatus().equals("success")) {
                            list_id = services.getContent();
                            Hawk.put("searchBusineess", list_id);
                            Log.v("list_id", "" + list_id.size());

                            for (int i = 0; i < list_id.size(); i++) {

                                staffList.addAll(list_id.get(i).getBusinessStaff());
                                bussinessServicePopModel.addAll(list_id.get(i).getBusinessServices());
                                for (int z = 0; z < bussinessServicePopModel.size(); z++) {
                                    subServicePopUpModel.addAll(bussinessServicePopModel.get(z).getService());
                                }

                                if (list_id.get(i).getId() == business_id) {
                                    bussinessService = list_id.get(i).getBusinessServices();
                                    if (bussinessService.size() != 0) {
                                        timeList(bussinessService.get(0).getService());
                                    }
                                }
                            }
                            Hawk.put("staffModel", staffList);
                            Hawk.put("subServicePopUpModel", subServicePopUpModel);
                            Log.v("staffModelTab", "" + staffList.size());
                            adpterType = new ServicesItemAdapter(getActivity(), bussinessService);
                            recyType.setAdapter(adpterType);
                        }


                        dialog.dismiss();

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

    public static void timeList(List<Service> timeList) {
        servicesListSelected = timeList;
        recyTime.setLayoutManager(new GridLayoutManager(activit, 1));
        adpterTime = new ServicesTimeAdapter(activit, timeList);
        recyTime.setAdapter(adpterTime);


    }

    public static void showServiceForGuest(String name) {
        guestName = name;
        String selectedBusinessID = Hawk.get("selectedBusinessID", "");
        Log.v("selectedBusinessID", "" + selectedBusinessID);
        searchBusinessGuest = Hawk.get("searchBusineess", searchBusinessGuest);

        for (int i = 0; i < searchBusinessGuest.size(); i++) {
            String businessID = "" + searchBusinessGuest.get(i).getId();
            String serviceID;

            if (businessID.equals(selectedBusinessID)) {
                businessServiceList = searchBusinessGuest.get(i).getBusinessServices();
                guestServiceDialog.show();


                if (businessServiceList.size() > 0) {
                    listserviceMainNames.clear();


                    for (int j = 0; j < businessServiceList.size(); j++) {
                        listserviceMainNames.add(businessServiceList.get(j).getName());
                        listServiceMainIDS.add("" + businessServiceList.get(j).getId());

                        if (listserviceMainNames.size() > 0) {
                            edtService.setText(listserviceMainNames.get(0));
                        }
                        serviceModelList = businessServiceList.get(j).getService();
                        if (serviceModelList.size() > 0) {
                            for (int k = 0; k < serviceModelList.size(); k++) {
                                String subServiceName = serviceModelList.get(k).getServiceName();
                                subServiceNames.add(serviceModelList.get(k).getServiceName());
                                subServiceIDS.add("" + serviceModelList.get(k).getId());
                                subServicePrice.add(serviceModelList.get(k).getPrice());
                                if (subServiceNames.size() > 0) {
                                    edtSubService.setText(subServiceNames.get(0));
                                }

                            }

                        }

                    }
                }


            }
        }

    }

}