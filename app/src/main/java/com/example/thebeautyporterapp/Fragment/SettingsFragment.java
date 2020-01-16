package com.example.thebeautyporterapp.Fragment;


import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thebeautyporterapp.R;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;


public class SettingsFragment extends Fragment {

    View rootView;
    LinearLayout linearChangepwd,linearPrivacyPolicy,linearshare,linearbeautyPorterPolicy,linearRefundPolicy,linearTC;
    String url;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_settings, container, false);
        linearChangepwd=(LinearLayout)rootView.findViewById(R.id.linearChangepwd);
        linearPrivacyPolicy=(LinearLayout)rootView.findViewById(R.id.linearPrivacyPolicy);
        linearshare=(LinearLayout)rootView.findViewById(R.id.linearshare);
        linearbeautyPorterPolicy=(LinearLayout)rootView.findViewById(R.id.linearbeautyPorterPolicy);
        linearRefundPolicy=(LinearLayout)rootView.findViewById(R.id.linearRefundPolicy);
        linearTC=(LinearLayout)rootView.findViewById(R.id.linearTC);


        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText(getString(R.string.setting));
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);

        linearPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = new PrivacyPolicyFragment();
//                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

                url="https://www.thebeautyporter.net/privacypolicy";
                Fragment fragment = new PolicyFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);

                Bundle args = new Bundle();
                args.putString("url", url);
                args.putString("title", "Privacy Policy");
                fragment.setArguments(args);
                fragmentTransaction.commit();
            }
        });

        linearbeautyPorterPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url="https://www.thebeautyporter.net/disclaimer";
                Fragment fragment = new PolicyFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);

                Bundle args = new Bundle();
                args.putString("url", url);
                args.putString("title", "BeautyPorter Policy");
                fragment.setArguments(args);
                fragmentTransaction.commit();
            }
        });

        linearRefundPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url="https://www.thebeautyporter.net/refundpolicy";
                Fragment fragment = new PolicyFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);

                Bundle args = new Bundle();
                args.putString("url", url);
                args.putString("title", "Refund Policy");
                fragment.setArguments(args);
                fragmentTransaction.commit();
            }
        });

        linearTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url="https://www.thebeautyporter.net/termsandconditions";
                Fragment fragment = new PolicyFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);

                Bundle args = new Bundle();
                args.putString("url", url);
                args.putString("title", "Terms & Conditions");
                fragment.setArguments(args);
                fragmentTransaction.commit();
            }
        });

        linearChangepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        linearshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "BeautyPorter");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        return rootView;
    }

}
