package com.example.thebeautyporterapp.Fragment;


import android.content.Context;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.thebeautyporterapp.Other.Utilities;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressFlower;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;


public class FeedBackFragment extends Fragment {

    View rootView;

    String access_token, user_id, business_id, review;
    int logedIn;

    LinearLayout lineaerMain, lineaerNoData;
    RecyclerView recyclerViewReview;

    Button btnSenReview;
    ImageView img1, img2, img3, img4, img5;
    String rating;
    EditText edtCmnt;

    LinearLayout linearReview;
    InputMethodManager imm;
    Utilities utilities;
    public FeedBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_feed_back, container, false);

        access_token = Hawk.get("access_token");
        user_id = Hawk.get("user_id");
        linearReview = rootView.findViewById(R.id.linearReview);
        btnSenReview = (Button) rootView.findViewById(R.id.btnSend);
        //btnSenReview.setText(getString(R.string.SendNow));
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.feedback));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);
        img1 = (ImageView) rootView.findViewById(R.id.img1);
        img2 = (ImageView) rootView.findViewById(R.id.img2);
        img3 = (ImageView) rootView.findViewById(R.id.img3);
        img4 = (ImageView) rootView.findViewById(R.id.img4);
        img5 = (ImageView) rootView.findViewById(R.id.img5);

        edtCmnt = (EditText) rootView.findViewById(R.id.edtCmnt);
        edtCmnt.requestFocus();


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "1";
                img1.setImageDrawable(null);
                img1.setBackgroundResource(R.drawable.smily_one_selected);
                img2.setBackgroundResource(R.drawable.smily_two_not_selected);
                img3.setBackgroundResource(R.drawable.smily_three_not_selected);
                img4.setBackgroundResource(R.drawable.smily_four_not_selected);
                img5.setBackgroundResource(R.drawable.smily_five_not_selected);

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "2";
                img2.setImageDrawable(null);
                img1.setBackgroundResource(R.drawable.smily_one_not_selected);
                img2.setBackgroundResource(R.drawable.smily_two_selected);
                img3.setBackgroundResource(R.drawable.smily_three_not_selected);
                img4.setBackgroundResource(R.drawable.smily_four_not_selected);
                img5.setBackgroundResource(R.drawable.smily_five_not_selected);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "3";
                img3.setImageDrawable(null);
                img1.setBackgroundResource(R.drawable.smily_one_not_selected);
                img2.setBackgroundResource(R.drawable.smily_two_not_selected);
                img3.setBackgroundResource(R.drawable.smily_three_selected);
                img4.setBackgroundResource(R.drawable.smily_four_not_selected);
                img5.setBackgroundResource(R.drawable.smily_five_not_selected);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "4";
                img4.setImageDrawable(null);
                img1.setBackgroundResource(R.drawable.smily_one_not_selected);
                img2.setBackgroundResource(R.drawable.smily_two_not_selected);
                img3.setBackgroundResource(R.drawable.smily_three_not_selected);
                img4.setBackgroundResource(R.drawable.smily_four_selected);
                img5.setBackgroundResource(R.drawable.smily_five_not_selected);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = "5";
                img5.setImageDrawable(null);
                img1.setBackgroundResource(R.drawable.smily_one_not_selected);
                img2.setBackgroundResource(R.drawable.smily_two_not_selected);
                img3.setBackgroundResource(R.drawable.smily_three_not_selected);
                img4.setBackgroundResource(R.drawable.smily_four_not_selected);
                img5.setBackgroundResource(R.drawable.smily_five_selected);
            }
        });
        btnSenReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review = edtCmnt.getText().toString();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtCmnt.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
//                            edtCmnt.setFocusable(false);
                if (review != null && !review.isEmpty() && !review.equals("null")) {
                    if (rating != null && !rating.isEmpty() && !rating.equals("null")) {
                       // Toast.makeText(getContext(), "Rated Successfully", Toast.LENGTH_SHORT).show();
                        AddReviewApi();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.ReviewCmntValidation), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), getString(R.string.ReviewCmntValidation), Toast.LENGTH_SHORT).show();
                }


            }
        });

        linearReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtCmnt, InputMethodManager.SHOW_FORCED);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                edtCmnt.requestFocus();
                edtCmnt.setFocusable(true);
            }
        });
        return rootView;
    }

    private void AddReviewApi() {

        final ACProgressFlower dialog = new ACProgressFlower.Builder(getActivity())
                .build();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String UrlServices = utilities.GetUrl() + "feedback";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServices,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try {
                            if (response != null && !response.isEmpty() && !response.equals("null")) {
                                JSONObject obj = new JSONObject(response);

                                String status = obj.getString("status");
                                String message = obj.getString("message");
                                if (status.equals("success")) {


//                                    dialog = new PrettyDialog(getActivity())
//                                            .setIcon(
//                                                    R.drawable.pdlg_icon_success,    // Icon resource
//                                                    R.color.colorPrimary,      // Icon tint
//                                                    new PrettyDialogCallback() {  // Icon OnClick listener
//                                                        @Override
//                                                        public void onClick() {
//                                                            // Do what you gotta do
//                                                        }
//                                                    })
//                                            .setTitle(getString(R.string.Success))
//                                            .setMessage(getString(R.string.ReviewSuccess))
//                                            .addButton(getString(R.string.Done), R.color.pdlg_color_white, R.color.colorPrimary, new PrettyDialogCallback() {
//                                                @Override
//                                                public void onClick() {
//                                                    dialog.dismiss();
//                                                    Fragment fragment = new ReviewFragment();
//                                                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
//                                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//
//                                                    fragmentTransaction.commit();
//                                                }
//                                            });
//                                    dialog.show();

                                    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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
//                params.put("business", business_id);
                params.put("message", review);
                params.put("level", "" + rating);


                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
