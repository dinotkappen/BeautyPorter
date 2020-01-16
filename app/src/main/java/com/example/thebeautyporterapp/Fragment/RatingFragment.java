package com.example.thebeautyporterapp.Fragment;


import android.graphics.PorterDuff;
import android.os.Bundle;
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
import android.widget.ProgressBar;
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
import com.example.thebeautyporterapp.Adapter.RatingAdapter;
import com.example.thebeautyporterapp.Model.ReviewCountModel;
import com.example.thebeautyporterapp.Model.ReviewModel;
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
import static com.example.thebeautyporterapp.Activity.MainActivity.imgAdd;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment {

View rootView;
    Utilities utilities;
    String access_token, user_id, business_id;
    int logedIn;

    LinearLayout lineaerMain, lineaerNoData;
    RecyclerView recyclerViewReview;
    private RatingAdapter ratingAdapter;
    ArrayList<ReviewModel> reviewModel = new ArrayList<ReviewModel>();
    ArrayList<ReviewCountModel> reviewCountModel = new ArrayList<ReviewCountModel>();
    TextView txtTotalReviews, txtAvgRating;
    TextView txtProgressCountOne, txtProgressCountTwo, txtProgressCountThree, txtProgressCountFour, txtProgressCountFive;
    private ProgressBar progressBarOne, progressBarTwo, progressBarThree, progressBarFour, progressBarFive;

    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_rating, container, false);

        linearActionBar.setVisibility(View.VISIBLE);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText("Client Reviews");
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);
        imgAdd.setVisibility(View.VISIBLE);

        access_token = Hawk.get("access_token", "");
        user_id = Hawk.get("user_id", "");
        logedIn = Hawk.get("logedIn", 0);
        business_id = Hawk.get("selectedBusinessID", "");

        txtProgressCountOne = (TextView) rootView.findViewById(R.id.txtProgressCountOne);
        txtProgressCountTwo = (TextView) rootView.findViewById(R.id.txtProgressCountTwo);
        txtProgressCountThree = (TextView) rootView.findViewById(R.id.txtProgressCountThree);
        txtProgressCountFour = (TextView) rootView.findViewById(R.id.txtProgressCountFour);
        txtProgressCountFive = (TextView) rootView.findViewById(R.id.txtProgressCountFive);


        progressBarOne = (ProgressBar) rootView.findViewById(R.id.progressBarOne);
        progressBarTwo = (ProgressBar) rootView.findViewById(R.id.progressBarTwo);
        progressBarThree = (ProgressBar) rootView.findViewById(R.id.progressBarThree);
        progressBarFour = (ProgressBar) rootView.findViewById(R.id.progressBarFour);
        progressBarFive = (ProgressBar) rootView.findViewById(R.id.progressBarFive);

        progressBarOne.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorGold), PorterDuff.Mode.SRC_IN);
        progressBarTwo.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorGold), PorterDuff.Mode.SRC_IN);
        progressBarThree.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorGold), PorterDuff.Mode.SRC_IN);
        progressBarFour.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorGold), PorterDuff.Mode.SRC_IN);
        progressBarFive.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorGold), PorterDuff.Mode.SRC_IN);


        txtTotalReviews = (TextView) rootView.findViewById(R.id.txtTotalReviews);
        txtAvgRating = (TextView) rootView.findViewById(R.id.txtAvgRating);
        lineaerMain = (LinearLayout) rootView.findViewById(R.id.lineaerMain);
        lineaerNoData = (LinearLayout) rootView.findViewById(R.id.lineaerNoData);
        lineaerNoData.setVisibility(View.GONE);
        lineaerMain.setVisibility(View.VISIBLE);
        recyclerViewReview = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewReview.setLayoutManager(mLayoutManager);
        recyclerViewReview.setItemAnimator(new DefaultItemAnimator());

        if (user_id != null && !user_id.isEmpty() && !user_id.equals("null")) {
            if (access_token != null && !access_token.isEmpty() && !access_token.equals("null")) {
                loadReviewApi();
            }
        }
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddRateFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

//        imgAddReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Fragment fragment = new AddReviewFragment();
//                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//
//            }
//        });

        return rootView;
    }

    private void loadReviewApi() {


        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String UrlServices = utilities.GetUrl() + "review_list";

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

                                    JSONObject contentsJsonObj = obj.getJSONObject("content");

                                    if (contentsJsonObj.length() > 0) {
                                        lineaerNoData.setVisibility(View.GONE);
                                        lineaerMain.setVisibility(View.VISIBLE);
                                        reviewModel.clear();
                                        JSONArray review_listArray = contentsJsonObj.getJSONArray("review_list");
                                        for (int i = 0; i < review_listArray.length(); i++) {
                                            JSONObject review_listObj = review_listArray.getJSONObject(i);
                                            String id = review_listObj.getString("id");
                                            String review = review_listObj.getString("review");
                                            String rating = review_listObj.getString("rating");
                                            String created_at = review_listObj.getString("created_at");
                                            JSONObject jsonObjectUser = review_listObj.getJSONObject("user");
                                            String customerName = jsonObjectUser.getString("name");
                                            String photo = jsonObjectUser.getString("photo");


                                            reviewModel.add(new ReviewModel(id, customerName, created_at, review, rating, photo));

                                        }

                                        JSONArray review_countJsonArray = contentsJsonObj.getJSONArray("review_count");
                                        for (int i = 0; i < review_countJsonArray.length(); i++) {
                                            JSONObject review_count_listObj = review_countJsonArray.getJSONObject(i);
                                            String total_rating = review_count_listObj.getString("total_rating");
                                            String rating = review_count_listObj.getString("rating");


                                            reviewCountModel.add(new ReviewCountModel(total_rating, rating));

                                        }


                                    } else {
                                        lineaerNoData.setVisibility(View.VISIBLE);
                                        lineaerMain.setVisibility(View.GONE);
                                    }
                                    try {
                                        ratingAdapter = new RatingAdapter(getActivity(), reviewModel);
                                        recyclerViewReview.setAdapter(ratingAdapter);
                                        setRating();
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
                params.put("business_id", business_id);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void setRating() {
        int totalRating = 0;
        int avrge = 0;
        for (int i = 0; i < reviewCountModel.size(); i++) {

            String total = reviewCountModel.get(i).getTotalRating();
            totalRating = totalRating + Integer.parseInt(total);

            String avg = reviewCountModel.get(i).getRating();
            if(avg.equals("1"))
            {
                progressBarOne.setProgress(Integer.parseInt(avg));
                txtProgressCountOne.setText(total);
            }
            if(avg.equals("2"))
            {
                progressBarTwo.setProgress(Integer.parseInt(avg));
                txtProgressCountTwo.setText(total);
            }
            if(avg.equals("3"))
            {
                progressBarThree.setProgress(Integer.parseInt(avg));
                txtProgressCountThree.setText(total);
            }
            if(avg.equals("4"))
            {
                progressBarFour.setProgress(Integer.parseInt(avg));
                txtProgressCountFour.setText(total);
            }
            if(avg.equals("5"))
            {
                progressBarFive.setProgress(Integer.parseInt(avg));
                txtProgressCountFive.setText(total);
            }

            avrge = avrge + Integer.parseInt(avg);


        }

//        progressBarTwo.setProgress(Integer.parseInt(reviewCountModel.get(1).getTotalRating()));
//        progressBarThree.setProgress(Integer.parseInt(reviewCountModel.get(2).getTotalRating()));
//        progressBarFour.setProgress(Integer.parseInt(reviewCountModel.get(3).getTotalRating()));
//        progressBarFive.setProgress(Integer.parseInt(reviewCountModel.get(4).getTotalRating()));

        txtTotalReviews.setText("" + totalRating + " Reviews" );
        txtAvgRating.setText("" + (avrge / reviewCountModel.size())+".0");
    }
}
