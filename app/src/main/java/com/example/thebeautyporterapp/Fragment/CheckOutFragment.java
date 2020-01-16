package com.example.thebeautyporterapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thebeautyporterapp.Activity.ThankyouActivity;
import com.example.thebeautyporterapp.Adapter.CheckOutAdapter;
import com.example.thebeautyporterapp.Model.BookingDetailsModel;
import com.example.thebeautyporterapp.Model.CheckOutModel;
import com.example.thebeautyporterapp.Model.GuestOrderModel;
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardMultilineWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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


public class CheckOutFragment extends Fragment {

    View rootView;
    RecyclerView recyList;
    public static CheckOutAdapter checkOutAdapter;
    ArrayList arrayListCheckOut = new ArrayList();
    CardView cardCashOnDelivery, cardLayoutCardPayment,cardViewPayment;
    private List demoList;
    String selectedBusinessName, selectedBusinessID, selectedBusinessAdrz, access_token;
    ArrayList<String> selectedTimeList = new ArrayList();
    ArrayList<String> selectedStaffIDList = new ArrayList();
    ArrayList<String> selectedDateList = new ArrayList();
    ArrayList<String> subServiceNameList = new ArrayList();
    ArrayList<String> subServiceIDList = new ArrayList();
    ArrayList<String> subServicePriceList = new ArrayList();
    String type, coupon_code, business_id, discAmount, promoCode;
    String strItemTotalPrice, user_id, mainServiceName, mainServiceID, specialRequest,selectedMode;
    public static ArrayList<BookingDetailsModel> itemList = new ArrayList<>();
    public static List<GuestOrderModel> guestList = new ArrayList<>();
    public static List<GuestOrderModel> guestListPrice = new ArrayList<>();
    public static GuestOrderModel guestOrderModel = new GuestOrderModel();
    public static BookingDetailsModel bModel = new BookingDetailsModel();
    ArrayList<CheckOutModel> checkOutModelModel = new ArrayList<CheckOutModel>();
    public static Double dblItemTotalPrice = 0.0;
    public static TextView txtGrandTotal, txtDiscount;
    ArrayList<String> alreadyExistList = new ArrayList<>();
    LinearLayout linearPrivacyPolicy;
    ImageView imgAddPromocde;
    EditText edtPromocode;
    Utilities utilities;
    int guestListSize = 0;
    String payment_method;
    public static ArrayList<GuestOrderModel> guestOrderModelArrayList = new ArrayList<>();
LinearLayout linearCheckOut,linearCard;
    CardMultilineWidget card_multiline_widget;

    String cardNumber;
    int expMonth;
    String expYear;
    String cvv;
    public CheckOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_check_out, container, false);
        cardCashOnDelivery = (CardView) rootView.findViewById(R.id.cardCashOnDelivery);
        cardLayoutCardPayment = (CardView) rootView.findViewById(R.id.cardLayoutCardPayment);
        card_multiline_widget = (CardMultilineWidget)rootView.findViewById(R.id.card_multiline_widget);
        cardViewPayment = (CardView) rootView.findViewById(R.id.cardViewPayment);
        linearCheckOut=(LinearLayout)rootView.findViewById(R.id.linearCheckOut);
        linearCard=(LinearLayout)rootView.findViewById(R.id.linearCard);
        linearCheckOut.setVisibility(View.VISIBLE);
        linearCard.setVisibility(View.GONE);
        imgAddPromocde = (ImageView) rootView.findViewById(R.id.imgAddPromocde);
        edtPromocode = (EditText) rootView.findViewById(R.id.edtPromocode);
        cardCashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearCheckOut.setVisibility(View.VISIBLE);
                linearCard.setVisibility(View.GONE);
                payment_method="COD";
                bookServiceApi();
            }
        });
        cardLayoutCardPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearCheckOut.setVisibility(View.GONE);
                linearCard.setVisibility(View.VISIBLE);

            }
        });
        cardViewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Card cardToSave = card_multiline_widget.getCard();

                if (cardToSave == null) {

                    return;
                } else {

                     cardNumber = cardToSave.getNumber();
                     expMonth = cardToSave.getExpMonth();
                     expYear =""+ cardToSave.getExpYear();
                    expYear = expYear.substring(Math.max(expYear.length() - 2, 0));
                    Log.v("expYear",expYear);
                     cvv = cardToSave.getCVC();
                    int h = cardToSave.getExpMonth();
                    payment_method="Card";
                    bookServiceApi();

                }
            }
        });
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.checkout));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);

        recyList = rootView.findViewById(R.id.recy_list);
        txtGrandTotal = (TextView) rootView.findViewById(R.id.txtGrandTotal);
        txtDiscount = (TextView) rootView.findViewById(R.id.txtDiscount);
        recyList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        linearPrivacyPolicy = (LinearLayout) rootView.findViewById(R.id.linearPrivacyPolicy);


        demoList = new ArrayList<>();
        promoCode = Hawk.get("promoCode", "");
        type = Hawk.get("type", "");
        coupon_code = Hawk.get("coupon_code", "");


        selectedBusinessName = Hawk.get("selectedBusinessName", "");
        selectedBusinessID = Hawk.get("selectedBusinessID", "");
        selectedBusinessAdrz = Hawk.get("selectedBusinessAdrz", "");
        user_id = Hawk.get("user_id", "");
        mainServiceName = Hawk.get("mainServiceName", "");
        mainServiceID = Hawk.get("mainServiceID", "");
        access_token = Hawk.get("access_token", "");

        specialRequest = Hawk.get("specialRequest", "");
        itemList.clear();
        guestList.clear();
        alreadyExistList.clear();
        dblItemTotalPrice = 0.0;
        itemList = Hawk.get("itemList", itemList);
        alreadyExistList = Hawk.get("alreadyExistList", alreadyExistList);
        Log.v("itemList", "" + itemList.size());

        selectedTimeList = Hawk.get("selectedTimeList", selectedTimeList);
        selectedStaffIDList = Hawk.get("selectedStaffIDList", selectedStaffIDList);
        selectedDateList = Hawk.get("selectedDateList", selectedDateList);
        subServiceNameList = Hawk.get("subServiceNameList", subServiceNameList);
        subServiceIDList = Hawk.get("subServiceIDList", subServiceIDList);
        subServicePriceList = Hawk.get("subServicePriceList", subServicePriceList);
        //bookingDetailsModel=Hawk.get("bookingDetailsModel",bookingDetailsModel);


        if (selectedBusinessName != null && !selectedBusinessName.isEmpty() && !selectedBusinessName.equals("null")) {

        }
        if (selectedBusinessAdrz != null && !selectedBusinessAdrz.isEmpty() && !selectedBusinessAdrz.equals("null")) {

        }
        checkOutModelModel.clear();
        for (int i = 0; i < itemList.size(); i++) {
            bModel = itemList.get(i);
            checkOutModelModel.add(new CheckOutModel(bModel.getBookingID(), bModel.getBookedDate(), bModel.getBookedTime(), "" + guestListSize,
                    bModel.getBookedServiceName(), bModel.getBookedPrice(), bModel.getGuestOrderModel()));
            int v=bModel.getGuestOrderModel().size();
            Log.v("guestSizeCF",""+v);

        }
        checkOutAdapter = new CheckOutAdapter(getActivity(), checkOutModelModel, getActivity());
        recyList.setAdapter(checkOutAdapter);
        Double guestCost = 0.0;
        for (int i = 0; i < itemList.size(); i++) {
            bModel = itemList.get(i);
            String normalCost = bModel.getBookedPrice();
//            guestListPrice = bModel.getGuestOrderModel();
//            for (int j = 0; j < guestListPrice.size(); j++) {
//                String price = guestListPrice.get(j).getCost();
//                guestCost = guestCost + Double.parseDouble(price);
//            }
            if (normalCost != null && !normalCost.isEmpty() && !normalCost.equals("null")) {
                Double bn = guestCost + Double.parseDouble(normalCost);
                dblItemTotalPrice = dblItemTotalPrice + bn;
            }
        }
        strItemTotalPrice = "" + dblItemTotalPrice;
        if (strItemTotalPrice != null && !strItemTotalPrice.isEmpty() && !strItemTotalPrice.equals("null")) {

            txtGrandTotal.setText(strItemTotalPrice + " " + getString(R.string.Qar));
        }
        if (discAmount != null && !discAmount.isEmpty() && !discAmount.equals("null")) {

            Double disCountedTotal = Double.parseDouble(strItemTotalPrice) - Double.parseDouble(discAmount);
            txtGrandTotal.setText(disCountedTotal + " " + getString(R.string.Qar));
        } else {
            txtDiscount.setText("0.00 " + getString(R.string.Qar));
        }
        linearPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new PrivacyPolicyFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        edtPromocode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                edtPromoCode.requestFocus();
//                ClipData abc = myClipboard.getPrimaryClip();
//                ClipData.Item item = abc.getItemAt(1);
                if (coupon_code != null && !coupon_code.isEmpty() && !coupon_code.equals("null")) {
                    edtPromocode.setText(coupon_code);
                }
                return false;
            }
        });
        imgAddPromocde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoCode = edtPromocode.getText().toString();
                if (promoCode != null && !promoCode.isEmpty() && !promoCode.equals("null")) {
                    applyOffersApi();
                }
            }
        });
        return rootView;
    }

    public static void deletePriceCalc() {
        dblItemTotalPrice = 0.0;
        for (int i = 0; i < itemList.size(); i++) {
            bModel = itemList.get(i);
            String bookedPrice=bModel.getBookedPrice();
            if (bookedPrice != null && !bookedPrice.isEmpty() && !bookedPrice.equals("null")) {
                dblItemTotalPrice = dblItemTotalPrice + Double.parseDouble(bookedPrice);
            }

        }
        String strItemTotalPrice = "" + dblItemTotalPrice;
        if (strItemTotalPrice != null && !strItemTotalPrice.isEmpty() && !strItemTotalPrice.equals("null")) {

            txtGrandTotal.setText(strItemTotalPrice + " QAR");
        }

        txtDiscount.setText("0.00");

    }

    private void bookServiceApi() {

      //  strItemTotalPrice = "100";

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = Utilities.GetUrl() + "booking";
        JSONObject objMain = new JSONObject();
        try {
            if(payment_method.equals("COD")) {
                objMain.put("payment_method", payment_method);
                objMain.put("amount", strItemTotalPrice);
                objMain.put("user_id", user_id);
                objMain.put("business_id", selectedBusinessID);
                objMain.put("coupon_code",edtPromocode.getText().toString());
            }
            else
            {
                objMain.put("payment_method", payment_method);
                objMain.put("amount", strItemTotalPrice);
                objMain.put("user_id", user_id);
                objMain.put("business_id", selectedBusinessID);
                objMain.put("coupon_code",edtPromocode.getText().toString());
                objMain.put("year", expYear);
                objMain.put("month", ""+expMonth);
                objMain.put("card", cardNumber);
                objMain.put("cvv", cvv);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray arrayNormal = new JSONArray();
        for (int i = 0; i < itemList.size(); i++) {

            bModel = itemList.get(i);
            guestList = bModel.getGuestOrderModel();
            JSONObject obj = new JSONObject();
            try {
                obj.put("mainServiceId", mainServiceID);

                obj.put("bookingDate", bModel.getBookedDate());
                obj.put("mainServiceName", mainServiceName);
                obj.put("mode", bModel.getMode());
                obj.put("serviceAmount", bModel.getBookedPrice());
                obj.put("numberOfPerson", "1");
                obj.put("bookingTime", bModel.getBookedTime());
                String hj = bModel.getBookedServiceName();
                obj.put("subServiceName", bModel.getBookedServiceName());
                obj.put("staffId", bModel.getBookedStaffId());
                obj.put("subServiceId", bModel.getBookedServiceID());
                obj.put("comment", specialRequest);

                JSONArray arrayGuest = new JSONArray();
                for (int j = 0; j < guestList.size(); j++) {
                    guestOrderModel = guestList.get(j);

                    JSONObject objGuest = new JSONObject();
                    try {

                        objGuest.put("serviceId", guestOrderModel.getServiceId());
                        objGuest.put("serviceName", guestOrderModel.getServiceName());
                        objGuest.put("cost", guestOrderModel.getCost());
                        objGuest.put("guestName", guestOrderModel.getGuestName());

                        arrayGuest.put(objGuest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                obj.put("guestUsers", arrayGuest);
                arrayNormal.put(obj);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        try {
            objMain.put("service", arrayNormal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("array", objMain.toString());


//            JSONObject jsonBody = new JSONObject();
//            jsonBody.put("Title", "Android Volley Demo");
//            jsonBody.put("Author", "BNK");
        final String requestBody = objMain.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);


                try {
                    dialog.dismiss();
                    if (response != null && !response.isEmpty() && !response.equals("null")) {
                        itemList.clear();
                        alreadyExistList.clear();
                        guestList.clear();
                        Hawk.put("itemList", itemList);
                        Hawk.put("alreadyExistList", alreadyExistList);
                        Hawk.put("guestList", guestList);
//                        bookingDetailsModel.clear();
//                        Hawk.put("bookingDetailsModel",bookingDetailsModel);

                        JSONObject obj = new JSONObject(response);

                        String status = obj.getString("status");
                        String message = obj.getString("message");
                        if (status.equals("success")) {
                            Intent intent = new Intent(getActivity(), ThankyouActivity.class);
                            startActivity(intent);
                            Toast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
                        }

                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Order placed Successfully...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ThankyouActivity.class);
                    startActivity(intent);
                    itemList.clear();
                    alreadyExistList.clear();
                    Hawk.put("itemList", itemList);
                    Hawk.put("alreadyExistList", alreadyExistList);


                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                itemList.clear();
                alreadyExistList.clear();
                Hawk.put("itemList", itemList);
                Hawk.put("alreadyExistList", alreadyExistList);
                Toast.makeText(getContext(), "Order placed Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ThankyouActivity.class);
                startActivity(intent);
                itemList.clear();
                Hawk.put("itemList", itemList);
                Log.e("VOLLEY", error.toString());
            }
        }) {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("authorization", "Bearer " + access_token);
                headers.put("accept", "application/json");
                return headers;
            }


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    responseString = String.valueOf(response.data);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void applyOffersApi() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        String UrlServices = utilities.GetUrl() + "coupon_code_check";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();

                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                JSONObject obj = new JSONObject(response);

                                String status = obj.getString("status");
                                String message = obj.getString("message");
                                if (status.equals("success")) {

                                    JSONObject contentsJsonObj = obj.getJSONObject("content");

                                    if (contentsJsonObj.length() > 0) {

                                        String id = contentsJsonObj.getString("id");
                                        String hj = contentsJsonObj.getString("amount");
                                        discAmount = contentsJsonObj.getString("amount");
                                        strItemTotalPrice=txtGrandTotal.getText().toString();
                                      if(strItemTotalPrice.contains("QAR"))
                                      {
                                          strItemTotalPrice=strItemTotalPrice.replaceAll("QAR", "");
                                          Log.v("strItemTotalPrice",strItemTotalPrice);
                                      }

                                        if (Double.parseDouble(discAmount) < Double.parseDouble(strItemTotalPrice)) {
                                            if (discAmount != null && !discAmount.isEmpty() && !discAmount.equals("null")) {
                                                txtDiscount.setText(discAmount);
                                                Double disCountedTotal = Double.parseDouble(strItemTotalPrice) - Double.parseDouble(discAmount);
                                                txtGrandTotal.setText(disCountedTotal + " " + getString(R.string.Qar));
                                                strItemTotalPrice=""+disCountedTotal;

                                            } else {
                                                txtDiscount.setText("0.00 " + getString(R.string.Qar));
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Not Available", Toast.LENGTH_SHORT).show();
                                        }


                                    }


                                    try {


                                    } catch (Exception ex) {
                                        dialog.dismiss();
                                        ex.printStackTrace();

                                    }
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Coupon code is not applicable ", Toast.LENGTH_LONG).show();
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
                params.put("type", "QAR");
                params.put("coupon_code", promoCode);
                params.put("business_id", selectedBusinessID);
                params.put("user_id", user_id);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


}
