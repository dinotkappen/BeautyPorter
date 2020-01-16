package com.example.thebeautyporterapp.Fragment;


import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thebeautyporterapp.Activity.VendorFromVendorFragmentActivity;
import com.example.thebeautyporterapp.R;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;


public class VendorFragment extends Fragment {

    View rootView;
    TextView txtfree;

    public VendorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_vendor, container, false);
        txtfree=(TextView)rootView.findViewById(R.id.txtfree);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.join_as_vendor));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);


        txtfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), VendorFromVendorFragmentActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
