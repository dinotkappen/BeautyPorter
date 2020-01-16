package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.thebeautyporterapp.Model.ServicesModel.Service;
import com.example.thebeautyporterapp.Model.SubServiceModel;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    CardView cardsalonSpa, cardHome, cardStylist, cardApply;
    List<Service> filterServiceModelList = new ArrayList<>();
    String selectedMode;
    ArrayList<SubServiceModel> subServiceModel = new ArrayList<SubServiceModel>();
    ArrayList<String> alreadyexistingServiceIDList = new ArrayList<>();


    ArrayList<SubServiceModel> filteredSubServiceModel = new ArrayList<SubServiceModel>();
    TextView txtMinPriceRange, txtMaxPriceRange, txtFinalPriceRange;
    View rootView;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        cardsalonSpa = rootView.findViewById(R.id.card_salon);
        cardHome = rootView.findViewById(R.id.home_services);
        cardStylist = rootView.findViewById(R.id.home_stylist);
        cardApply = rootView.findViewById(R.id.cardApply);
        selectedMode = "Salon, Spa & Clinics";

        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rootView.findViewById(R.id.rangeSeekbar1);

// get min and max text view
        txtFinalPriceRange = (TextView) rootView.findViewById(R.id.txtFinalPriceRange);
        txtMaxPriceRange = (TextView) rootView.findViewById(R.id.txtMaxPriceRange);
        txtMinPriceRange = (TextView) rootView.findViewById(R.id.txtMinPriceRange);


// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                txtMinPriceRange.setText(String.valueOf(minValue));
                txtMaxPriceRange.setText(String.valueOf(maxValue));
                txtFinalPriceRange.setText(String.valueOf(minValue) + "-" + String.valueOf(maxValue));
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

        filterServiceModelList = Hawk.get("filterServiceModelList", filterServiceModelList);
        subServiceModel = Hawk.get("subServiceModel", subServiceModel);
        cardApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyexistingServiceIDList.clear();
                filteredSubServiceModel.clear();
                for (int i = 0; i < subServiceModel.size(); i++) {

                    String bussinessID = subServiceModel.get(i).getSubServiceID();
                    if (bussinessID != null && !bussinessID.isEmpty() && !bussinessID.equals("null")) {


                        for (int j = 0; j < filterServiceModelList.size(); j++) {
                            String filterBussinessID = filterServiceModelList.get(j).getBusinessId();
                            if (bussinessID.equals(filterBussinessID)) {
                                String mode = filterServiceModelList.get(j).getMode();
                                String amount = filterServiceModelList.get(j).getPrice();
                                if (mode.equals(selectedMode)) {
                                    if (!alreadyexistingServiceIDList.contains(subServiceModel.get(i).getSubServiceID())) {
                                        alreadyexistingServiceIDList.add(subServiceModel.get(i).getSubServiceID());
                                        filteredSubServiceModel.add(new SubServiceModel(subServiceModel.get(i).getSubServiceID(), subServiceModel.get(i).getSubServiceTitle(), subServiceModel.get(i).getSubServiceImgUrl(), subServiceModel.get(i).getSubServiceRating(), subServiceModel.get(i).getSubServiceAdrz(), subServiceModel.get(i).getServicesIDS(), subServiceModel.get(i).getSelectedMainServiceID(), subServiceModel.get(i).getSubServiceDescription(), subServiceModel.get(i).getLatitude(), subServiceModel.get(i).getLongitude(), subServiceModel.get(i).getIsfavourite(), subServiceModel.get(i).getLocation()));
                                    }
                                }
                            }
                        }


                    }

                    Hawk.put("filteredSubServiceModel", filteredSubServiceModel);
                }

                Fragment fragment = new FilterResultFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        cardsalonSpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardsalonSpa.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                cardHome.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                cardStylist.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                selectedMode = "Salon, Spa & Clinics";

            }
        });
        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardsalonSpa.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                cardHome.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                cardStylist.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                selectedMode = "Home Service";

            }
        });
        cardStylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardsalonSpa.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                cardHome.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                cardStylist.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                selectedMode = "Stylists";

            }
        });


        return rootView;
    }

}
