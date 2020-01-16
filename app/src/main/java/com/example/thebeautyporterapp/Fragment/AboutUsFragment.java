package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thebeautyporterapp.R;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;


public class AboutUsFragment extends Fragment {

    View rootView;
    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_about_us, container, false);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.about_us));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);

        return rootView;
    }

}