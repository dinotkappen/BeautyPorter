package com.example.thebeautyporterapp.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.example.thebeautyporterapp.Adapter.TimeAdapter;
import com.example.thebeautyporterapp.Model.BookingDetailsModel;
import com.example.thebeautyporterapp.Model.GuestOrderModel;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessStaff;
import com.example.thebeautyporterapp.Model.ServicesModel.Service;
import com.example.thebeautyporterapp.Model.TimeModel;
import com.example.thebeautyporterapp.R;
import com.example.thebeautyporterapp.Other.Utilities;
import com.orhanobut.hawk.Hawk;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;
import static com.example.thebeautyporterapp.Adapter.TimeAdapter.selectedTimeList;


public class DateTimeFragment extends Fragment {
    View rootView;
    private RecyclerView recyclerViewTime;
    public static TimeAdapter timeAdapter;
    ArrayList<TimeModel> timeModel = new ArrayList<TimeModel>();

    CardView btnCardBookNow, btnCardAddMore;
    EditText editStaff;
    List<BusinessStaff> staffList = new ArrayList<>();

    List<Service> subServicePopUpModel = new ArrayList<>();

    ArrayList<String> alreadyExistList = new ArrayList<>();
    ArrayList<BookingDetailsModel> itemList = new ArrayList<>();
    ArrayList<GuestOrderModel> guestList = new ArrayList<>();
    BookingDetailsModel bModel = new BookingDetailsModel();

    Dialog staff_Dialog;
    ListView listView_Staff;
    List<String> listStaffNames;
    List<String> listStaffIDS;
    public static List<String> listSelectedTime;

    List<Service> selectedServiceDetails = new ArrayList<>();
    ArrayList<String> selectedStaffIDList = new ArrayList<>();
    ArrayList<String> selectedDateList = new ArrayList<>();
    ArrayList<String> subServiceNameList = new ArrayList<>();
    ArrayList<String> subServiceIDList = new ArrayList<>();
    ArrayList<String> subServicePriceList = new ArrayList<>();
    String selectedServiceName, selectedServiceID, selectedServicePrice, selectedMode;
    int serviceId;
    LinearLayout linearRecyclerview;


    String formatedTodaysDate;
    Utilities utilities;
    String access_token, user_id;
    int logedIn;
    Context mContext;
    EditText edtSpecialRequest;
    public static List<Service> listStaff = new ArrayList<>();
    MaterialCalendarView calendarView;


    String dateCrctFormat, selectedStaff, service_id, business_id, selectedStaffId;

//    public DateTimeFragment() {
//        // Required empty public constructor
//    }

    public static DateTimeFragment newInstance(List<Service> list_staff) {
        DateTimeFragment fragment = new DateTimeFragment();
        listStaff = list_staff;
        return fragment;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_date_time, container, false);

        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        linearActionBar.setVisibility(View.VISIBLE);
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.BookAppointment));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);

        service_id = "" + listStaff.get(0).getServiceId();
        selectedStaffId = listStaff.get(0).getServiceStaff();
        business_id = listStaff.get(0).getBusinessId();

        editStaff = (EditText) rootView.findViewById(R.id.editStaff);
        edtSpecialRequest = (EditText) rootView.findViewById(R.id.edtSpecialRequest);

        calendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
        final Calendar calendar = Calendar.getInstance();
        calendarView.setDateSelected(calendar.getTime(), true);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        final String todaysDate = "" + calendarView.getCurrentDate().getCalendar().getTime();

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            selectedServiceName = bundle.getString("selectedServiceName", "");
//            selectedServiceID = bundle.getString("selectedServiceID", "");
//            selectedServicePrice = bundle.getString("selectedServicePrice", "");
//
//
//        }
        selectedServiceName = Hawk.get("selectedServiceName", selectedServiceName);
        selectedServiceID = "" + Hawk.get("selectedServiceID", 0);
        selectedServicePrice = Hawk.get("selectedServicePrice", selectedServicePrice);
        selectedMode = Hawk.get("selectedMode", "");

        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = null;
        try {
            date = (Date) formatter.parse(todaysDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        formatedTodaysDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);


        linearRecyclerview = (LinearLayout) rootView.findViewById(R.id.linearRecyclerview);
        linearRecyclerview.setVisibility(View.GONE);

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        access_token = Hawk.get("access_token", "");
        user_id = Hawk.get("user_id", "");
        logedIn = Hawk.get("logedIn", 0);

        listStaffNames = new ArrayList<>();
        listStaffIDS = new ArrayList<>();
        listSelectedTime = new ArrayList<>();
        selectedServiceDetails.add(Hawk.get("selectedServiceDetails"));
        staffList = Hawk.get("staffModel", staffList);
        Log.v("staffModelDate", "" + staffList.size());
        subServicePopUpModel = Hawk.get("subServicePopUpModel", subServicePopUpModel);
        Log.v("staffList", "" + staffList.size());

        Log.v("subServicePopUpModel", "" + subServicePopUpModel.size());
        for (int i = 0; i < staffList.size(); i++) {
            String jk = staffList.get(i).getBusinessId();
            Log.v("jk", jk);
        }

        if (staffList.size() > 0) {
            listStaffNames = new ArrayList<>();
            listStaffIDS = new ArrayList<>();
            for (int i = 0; i < staffList.size(); i++) {
                String staffBussinessId = staffList.get(i).getBusinessId();
                for (int j = 0; j < subServicePopUpModel.size(); j++) {

                    String businessID = subServicePopUpModel.get(j).getBusinessId();
                    if (staffBussinessId.equals(businessID)) {
                        if (!listStaffIDS.contains(staffList.get(i).getId())) {
                            if (!listStaffNames.contains(staffList.get(i).getStaffName())) {

                                for (int k = 0; k < selectedServiceDetails.size(); k++) {
                                    String selectedStaffName = selectedServiceDetails.get(k).getServiceStaff();
                                    if (selectedStaffName.equals(subServicePopUpModel.get(j).getServiceStaff())) {
                                        service_id = "" + subServicePopUpModel.get(j).getId();
                                        business_id = subServicePopUpModel.get(j).getBusinessId();
                                        if (!listStaffNames.contains(staffList.get(i).getStaffName())) {
                                            listStaffNames.add(staffList.get(i).getStaffName());
                                            listStaffIDS.add("" + staffList.get(i).getId());
                                        }
                                    }
                                }


                            }


                        }

                    }
                }


            }
        }
        staff_Dialog = new Dialog(getActivity());
        staff_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        staff_Dialog.setContentView(R.layout.dilg_staff);
        staff_Dialog.setCanceledOnTouchOutside(false);
        listView_Staff = (ListView) staff_Dialog.findViewById(R.id.listView_Staff);
        // Create an ArrayAdapter from List
        ArrayAdapter<String> adapterStaff = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, listStaffNames) {
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

        listView_Staff.setAdapter(adapterStaff);


//        // Capture ListView item click
        listView_Staff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String str_CustomerType = (String) listView_Staff.getItemAtPosition(position);
                editStaff.setText(str_CustomerType);
                selectedStaffId = listStaffIDS.get(position);
                selectedStaffIDList.add(selectedStaffId);
                Log.v("staff_id", selectedStaffId);
                Hawk.put("selectedStaffIDList", selectedStaffIDList);
                Hawk.get("selectedStaffId", selectedStaffId);


                staff_Dialog.dismiss();


            }
        });

        editStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStaff.setError(null);
                staff_Dialog.show();
            }
        });

        staffList = Hawk.get("staffList", staffList);
        subServicePopUpModel = Hawk.get("subServicePopUpModel", subServicePopUpModel);
        Log.v("staffList", "" + staffList.size());

        Log.v("subServicePopUpModel", "" + subServicePopUpModel.size());
//for(int i=0;i<subServicePopUpModel.size();i++)
//{
//    boolean stat=subServicePopUpModel.get(i).getStatus();
//    boolean kjl=stat;
//}


        btnCardAddMore = (CardView) rootView.findViewById(R.id.btnCardAddMore);
        btnCardBookNow = (CardView) rootView.findViewById(R.id.btnCardBookNow);


        btnCardBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String selectedTimeSlot = Hawk.get("selectedTimeSlot", "");

                if (selectedTimeSlot != null && !selectedTimeSlot.isEmpty() && !selectedTimeSlot.equals("null")) {

                    Hawk.put("selectedTimeList", selectedTimeList);
                    Hawk.put("specialRequest", edtSpecialRequest.getText().toString());
                    guestList = Hawk.get("guestList", guestList);
                    for (int k = 0; k < guestList.size(); k++) {
                        Log.v("guestListValues", guestList.get(k).getServiceName());
                    }


                    BookingDetailsModel bookingDetailsModel = new BookingDetailsModel();

                    bookingDetailsModel.setBookedDate(dateCrctFormat);
                    bookingDetailsModel.setBookedTime(selectedTimeSlot);
                    bookingDetailsModel.setBookedServiceID(selectedServiceID);
                    bookingDetailsModel.setBookedServiceName(selectedServiceName);
                    bookingDetailsModel.setBookedStaffId(selectedStaffId);
                    bookingDetailsModel.setBookedPrice(selectedServicePrice);
                    bookingDetailsModel.setBookingID("1");
                    bookingDetailsModel.setMode(selectedMode);
                    bookingDetailsModel.setGuestOrderModel(guestList);


                    itemList = Hawk.get("itemList", itemList);
                    alreadyExistList = Hawk.get("alreadyExistList", alreadyExistList);
                    Boolean flag = false;
                    if (itemList.size() > 0) {
                        for (int i = 0; i < itemList.size(); i++) {
                            bModel = itemList.get(i);

                            String ServiceID = bModel.getBookedServiceID();
                            String date = bModel.getBookedDate();
                            String time = bModel.getBookedTime();


                            for (int j = 0; j < alreadyExistList.size(); j++) {
                                String alString = alreadyExistList.get(j);
                                if (!alString.equals(selectedServiceID + dateCrctFormat + selectedTimeSlot)) {
                                    flag = true;

                                } else {
                                    flag = false;
                                }
                            }
                            if (flag == true) {
                                itemList.add(bookingDetailsModel);
                                alreadyExistList.add(selectedServiceID + dateCrctFormat + selectedTimeSlot);
                                flag = false;
                            }
                        }

                    } else {
                        alreadyExistList.add(selectedServiceID + dateCrctFormat + selectedTimeSlot);
                        itemList.add(bookingDetailsModel);
                    }


                    Hawk.put("bookingDetailsModel", bookingDetailsModel);

                    Log.v("itemListDatF", "" + itemList.size());

                    Hawk.put("itemList", itemList);
                    Hawk.put("selectedTimeSlot", "");



                    itemList = Hawk.get("itemList", itemList);


                    Hawk.put("alreadyExistList", alreadyExistList);
//                    guestList.clear();
//                    Hawk.put("guestList", guestList);


                    Fragment fragment = new CheckOutFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                } else {

                    Toast.makeText(getContext(), "SelectTimeSlot", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCardAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedStaff = editStaff.getText().toString();

                if (selectedStaff != null && !selectedStaff.isEmpty() && !selectedStaff.equals("null")) {
                    final String selectedTimeSlot = Hawk.get("selectedTimeSlot", "");

                    if (selectedTimeSlot != null && !selectedTimeSlot.isEmpty() && !selectedTimeSlot.equals("null")) {

                        itemList = Hawk.get("itemList", itemList);
                        alreadyExistList = Hawk.get("alreadyExistList", alreadyExistList);
                        guestList = Hawk.get("guestList", guestList);
                        Log.v("guestListSizeDTF", "" + guestList.size());

                        BookingDetailsModel bookingDetailsModel = new BookingDetailsModel();
                        bookingDetailsModel.setBookedDate(dateCrctFormat);
                        bookingDetailsModel.setBookedTime(selectedTimeSlot);
                        bookingDetailsModel.setBookedServiceID(selectedServiceID);
                        bookingDetailsModel.setBookedServiceName(selectedServiceName);
                        bookingDetailsModel.setBookedStaffId(selectedStaffId);
                        bookingDetailsModel.setBookedPrice(selectedServicePrice);
                        bookingDetailsModel.setBookingID("1");
                        bookingDetailsModel.setMode(selectedMode);
                        bookingDetailsModel.setGuestOrderModel(guestList);
//*********************************************************
                        Boolean flag = false;
                        if (itemList.size() > 0) {
                            for (int i = 0; i < itemList.size(); i++) {
                                bModel = itemList.get(i);

                                String ServiceID = bModel.getBookedServiceID();
                                String date = bModel.getBookedDate();
                                String time = bModel.getBookedTime();


                                for (int j = 0; j < alreadyExistList.size(); j++) {
                                    String alString = alreadyExistList.get(j);
                                    if (!alString.equals(selectedServiceID + dateCrctFormat + selectedTimeSlot)) {
                                        flag = true;

                                    }
                                }

                            }
                            if (flag == true) {
                                itemList.add(bookingDetailsModel);
                                alreadyExistList.add(selectedServiceID + dateCrctFormat + selectedTimeSlot);
                            }

                        } else {
                            alreadyExistList.add(selectedServiceID + dateCrctFormat + selectedTimeSlot);
                            itemList.add(bookingDetailsModel);
                        }
///********************************************

                        //Hawk.put("bookingDetailsModel", bookingDetailsModel);
//                        guestList.clear();
//                        Hawk.put("guestList", guestList);
                        Hawk.put("itemList", itemList);
                        Hawk.put("alreadyExistList", alreadyExistList);
                        Fragment fragment = new TabServicesFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {

                        Toast.makeText(getContext(), "Select time Slot", Toast.LENGTH_LONG).show();
                    }

                } else {
                    editStaff.setError("Select time Slot");
                    Toast.makeText(getContext(), "Select time Slot", Toast.LENGTH_LONG).show();

                }

            }
        });

        recyclerViewTime = (RecyclerView) rootView.findViewById(R.id.recyclerViewTime);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerViewTime.setLayoutManager(mLayoutManager);
        recyclerViewTime.setItemAnimator(new DefaultItemAnimator());

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedStaff = editStaff.getText().toString();
                if (selectedStaff != null && !selectedStaff.isEmpty() && !selectedStaff.equals("null")) {
                    CalendarDay mDate = date;
                    String dtae = "" + mDate;
                    dtae = dtae.replace("CalendarDay{", "");
                    dtae = dtae.replace("}", "");

                    String[] separated = dtae.split("-");
                    String yearStr = separated[0];
                    String monthStr = separated[1];
                    String dateStr = separated[2];
                    int monthInt = Integer.parseInt(monthStr) + 1;
                    monthStr = "" + monthInt;
                    Log.v("dat:", "" + dateStr.length());
                    if (dateStr.length() < 2) {
                        dateStr = "0" + dateStr;
                    }
                    dateCrctFormat = yearStr + "-" + monthStr + "-" + dateStr;

                    Log.v("dateCrctFormat", "" + dateCrctFormat);

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
                    String getCurrentDateTime = sdf.format(c.getTime());

                    Log.d("getCurrentDateTime", getCurrentDateTime);

                    if (getCurrentDateTime.compareTo(dateCrctFormat) <= 0) {

                        Hawk.put("selectedDate", dateCrctFormat);


                        if (service_id != null && !service_id.isEmpty() && !service_id.equals("null")) {
                            if (dateCrctFormat != null && !dateCrctFormat.isEmpty() && !dateCrctFormat.equals("null")) {
                                if (selectedStaffId != null && !selectedStaffId.isEmpty() && !selectedStaffId.equals("null")) {
                                    if (business_id != null && !business_id.isEmpty() && !business_id.equals("null")) {
                                        loadServiceStaffSlotsApi();
                                    }
                                }
                            }
                        }
                    } else {
                        if (timeModel.size() > 0) {
                            linearRecyclerview.setVisibility(View.GONE);
                            timeModel.clear();
                            timeAdapter = new TimeAdapter(getActivity(), timeModel);
                            recyclerViewTime.setAdapter(timeAdapter);
                        }
                        Toast.makeText(getContext(), "Select a valid date", Toast.LENGTH_SHORT).show();

                    }


//                    if (Date.parse(formatedTodaysDate) > Date.parse(dateCrctFormat)) {
//                        Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(getContext(),"2",Toast.LENGTH_SHORT).show();
//                    }


                } else {
                    editStaff.setError("staff_hint");

                }

            }
        });

        return rootView;
    }

    private void loadServiceStaffSlotsApi() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String UrlServices = utilities.GetUrl() + "service-staffs-slots";
        Log.e("url", UrlServices);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("time_slote", response);
                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                JSONObject obj = new JSONObject(response);
                                timeModel.clear();
                                String status = obj.getString("status");
                                if (status.equals("success")) {
                                    linearRecyclerview.setVisibility(View.VISIBLE);
                                    JSONArray contentsArray = obj.getJSONArray("content");

                                    if (contentsArray.length() > 0) {


                                        for (int i = 0; i < contentsArray.length(); i++) {
                                            JSONObject jsondata = contentsArray.getJSONObject(i);
                                            String slot = jsondata.getString("slot");
                                            String statusTimeSlot = jsondata.getString("status");


                                            timeModel.add(new TimeModel(statusTimeSlot, slot, false));

                                        }
                                    }

                                    try {
                                        timeAdapter = new TimeAdapter(getActivity(), timeModel);
                                        recyclerViewTime.setAdapter(timeAdapter);
                                        dialog.dismiss();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        dialog.dismiss();

                                    }
                                } else {

                                    Toast.makeText(getContext(), "Server_Error", Toast.LENGTH_LONG).show();
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
                params.put("service_id", service_id);
                params.put("service_date", dateCrctFormat);
                params.put("staff_id", selectedStaffId);
                params.put("business_id", business_id);

                Hawk.put("service_id", service_id);
                Hawk.put("service_date", dateCrctFormat);
                Hawk.put("staff_id", selectedStaffId);
                Hawk.put("business_id", business_id);
                Log.e("params", params.toString());

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


}
