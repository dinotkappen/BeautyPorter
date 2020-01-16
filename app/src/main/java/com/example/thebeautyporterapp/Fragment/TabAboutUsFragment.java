package com.example.thebeautyporterapp.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessService;
import com.example.thebeautyporterapp.Model.ServicesModel.Content;
import com.example.thebeautyporterapp.Model.ServicesModel.Service;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;


public class TabAboutUsFragment extends Fragment {

    List<Content> list_id = new ArrayList<>();
    List<BusinessService> businessServiceList = new ArrayList<>();
    List<Service> seerviceList = new ArrayList<>();
    String selectedBusinessID;
    ImageView imgBanner;
    TextView txtBio, txtAdrz, txtHomeService, txtSaloonService,txtPhone;
    EditText edtWebSite;
    ImageView imgFb, imgInsta, imgTwitter;
    String image, bio, building_number, street_number, street_name, zone_number, locations, contact_no, website, facebook, instagram, twitter;
    View rootView;
    String subListAdrz = "";
    Boolean homeServiceBool = false;
    Boolean spaServiceBool = false;
    ImageView   imgHomeService,imgSpaService;

    public TabAboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.tab_about, container, false);
        imgBanner = (ImageView) rootView.findViewById(R.id.imgBanner);
        imgFb = (ImageView) rootView.findViewById(R.id.imgFb);
        imgHomeService = (ImageView) rootView.findViewById(R.id.imgHomeService);
        imgSpaService = (ImageView) rootView.findViewById(R.id.imgSpaService);
        imgInsta = (ImageView) rootView.findViewById(R.id.imgInsta);
        imgTwitter = (ImageView) rootView.findViewById(R.id.imgTwitter);
        txtBio = (TextView) rootView.findViewById(R.id.txtBio);
        txtAdrz = (TextView) rootView.findViewById(R.id.txtAdrz);
        txtPhone = (TextView) rootView.findViewById(R.id.txtPhone);
        edtWebSite = (EditText) rootView.findViewById(R.id.edtWebSite);
        txtHomeService = (TextView) rootView.findViewById(R.id.txtHomeService);
        txtSaloonService = (TextView) rootView.findViewById(R.id.txtSaloonService);
        list_id = Hawk.get("searchBusineess", list_id);
        Log.v("list_idAbout", "" + list_id.size());
        selectedBusinessID = Hawk.get("selectedBusinessID", "");
        if (list_id.size() > 0) {
            for (int i = 0; i < list_id.size(); i++) {
                int bussinessID = list_id.get(i).getId();
                if (selectedBusinessID.equals("" + bussinessID)) {
                    image = list_id.get(i).getPhoto();
                    bio = list_id.get(i).getBio();
                    building_number = list_id.get(i).getBuildingNumber();
                    street_number = list_id.get(i).getStreetNumber();
                    street_name = list_id.get(i).getStreetName();
                    zone_number = list_id.get(i).getZoneNumber();
                    locations = list_id.get(i).getLocations();
                    contact_no = list_id.get(i).getContactNo();
                    website = list_id.get(i).getWebsite();
                    facebook = list_id.get(i).getFacebook();
                    instagram = list_id.get(i).getInstagram();
                    twitter = list_id.get(i).getTwitter();
                    businessServiceList = list_id.get(i).getBusinessServices();



                    for (int j = 0; j < businessServiceList.size(); j++) {
                        seerviceList = businessServiceList.get(j).getService();
                        for (int k = 0; k < seerviceList.size(); k++) {
                            String mode = seerviceList.get(k).getMode();
                            if (mode.equals("Salon, Spa & Clinics")) {
                                spaServiceBool = true;
                                Log.v("spaServiceBool", ""+spaServiceBool);
                            } else if (mode.equals("Home Service")) {
                                homeServiceBool = true;
                                Log.v("homeServiceBool", ""+homeServiceBool);

                            }

                            Log.v("mode", mode);
                        }
                    }

                    Glide.with(getActivity())
                            .load(image)
                            .apply(new RequestOptions().placeholder(R.mipmap.logo).error(R.mipmap.logo))
                            .into(imgBanner);
                    if (bio != null && !bio.isEmpty() && !bio.equals("null")) {
                        txtBio.setText(bio);
                    }
                    if (contact_no != null && !contact_no.isEmpty() && !contact_no.equals("null")) {
                        txtPhone.setText(contact_no);
                    }

                }
            }
        }
        if (spaServiceBool == true) {
            txtSaloonService.setText("Salon Services : Yes");
            imgSpaService.setBackgroundResource(R.mipmap.work);

        }
        else
        {
            txtSaloonService.setText("Salon Services : No");
            imgSpaService.setBackgroundResource(R.mipmap.work);
        }
        if (homeServiceBool == true) {
            txtHomeService.setText(" Home Services : Yes");
            imgHomeService.setBackgroundResource(R.mipmap.home_circle);

        } else {
            txtHomeService.setText(" Home Services : No");
            imgHomeService.setBackgroundResource(R.mipmap.home);
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
        if (subListAdrz != null && !subListAdrz.isEmpty() && !subListAdrz.equals("null")) {
            txtAdrz.setText(subListAdrz);
        }
        if (website != null && !website.isEmpty() && !website.equals("null")) {
            edtWebSite.setText(website);
        } else {
            edtWebSite.setText("No WebSite Availabe");
        }
        edtWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (website != null && !website.isEmpty() && !website.equals("null")) {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(website));
                    startActivity(viewIntent);

                } else {
                    Toast.makeText(getActivity(), "Sorry not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facebook != null && !facebook.isEmpty() && !facebook.equals("null")) {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(facebook));
                    startActivity(viewIntent);

                } else {
                    Toast.makeText(getActivity(), "Sorry not available", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instagram != null && !instagram.isEmpty() && !instagram.equals("null")) {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(instagram));
                    startActivity(viewIntent);
                } else {
                    Toast.makeText(getActivity(), "Sorry not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twitter != null && !twitter.isEmpty() && !twitter.equals("null")) {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(twitter));
                    startActivity(viewIntent);
                } else {
                    Toast.makeText(getActivity(), "Sorry not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

}