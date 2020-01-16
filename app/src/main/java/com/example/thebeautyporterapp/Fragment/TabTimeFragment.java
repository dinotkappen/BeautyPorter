package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thebeautyporterapp.Adapter.HoursAdapter;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessTime;
import com.example.thebeautyporterapp.Model.ServicesModel.Content;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TabTimeFragment extends Fragment {

    View rootView;
    List<Content> listSearchBusiness = new ArrayList<>();
    List<BusinessTime> businessTimeList = new ArrayList<>();
    String selecteSubServiceID;
    private RecyclerView recyclerViewHours;
    private HoursAdapter hoursAdapter;
    LinearLayout linearMain,linearNoData;
    public TabTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.tab_time, container, false);
        linearNoData = (LinearLayout) rootView.findViewById(R.id.linearNoData);
        linearMain = (LinearLayout) rootView.findViewById(R.id.linearMain);

        recyclerViewHours = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewHours.setLayoutManager(mLayoutManager);
        recyclerViewHours.setItemAnimator(new DefaultItemAnimator());
        selecteSubServiceID = Hawk.get("selecteSubServiceID", selecteSubServiceID);
        listSearchBusiness = Hawk.get("searchBusineess", listSearchBusiness);
        for (int i = 0; i < listSearchBusiness.size(); i++) {
            int bussinessID = listSearchBusiness.get(i).getId();
            if (Integer.parseInt(selecteSubServiceID) == bussinessID) {
                businessTimeList = listSearchBusiness.get(i).getBusinessTime();
                if (businessTimeList.size() > 0) {

                    for (int j = 0; j < businessTimeList.size(); j++) {
                        String day = businessTimeList.get(j).getDay();
                        String start_time = businessTimeList.get(j).getStartTime();
                        String end_time = businessTimeList.get(j).getEndTime();
                        String status = businessTimeList.get(j).getStatus();

                    }
                    Collections.reverse(businessTimeList);


                    hoursAdapter = new HoursAdapter(getActivity(), businessTimeList);
                    recyclerViewHours.setAdapter(hoursAdapter);
//                    linearNoData.setVisibility(View.GONE);
//                    linearMain.setVisibility(View.VISIBLE);

                }
                else
                {


                    BusinessTime businessTimeSun=new BusinessTime();
                    businessTimeSun.setDay("Sunday");
                    businessTimeSun.setStartTime("Close");
                    businessTimeSun.setEndTime("Close");
                    businessTimeList.add(businessTimeSun);

                    BusinessTime businessTimeMon=new BusinessTime();
                    businessTimeMon.setDay("Monday");
                    businessTimeMon.setStartTime("Close");
                    businessTimeMon.setEndTime("Close");
                    businessTimeList.add(businessTimeMon);

                    BusinessTime businessTimeTue=new BusinessTime();
                    businessTimeTue.setDay("Tuesday");
                    businessTimeTue.setStartTime("Close");
                    businessTimeTue.setEndTime("Close");
                    businessTimeList.add(businessTimeTue);

                    BusinessTime businessTimeWed=new BusinessTime();
                    businessTimeWed.setDay("Wednesday");
                    businessTimeWed.setStartTime("Close");
                    businessTimeWed.setEndTime("Close");
                    businessTimeList.add(businessTimeWed);

                    BusinessTime businessTimeThu=new BusinessTime();
                    businessTimeThu.setDay("Thursday");
                    businessTimeThu.setStartTime("Close");
                    businessTimeThu.setEndTime("Close");
                    businessTimeList.add(businessTimeThu);

                    BusinessTime businessTimeFri=new BusinessTime();
                    businessTimeFri.setDay("Friday");
                    businessTimeFri.setStartTime("Close");
                    businessTimeFri.setEndTime("Close");
                    businessTimeList.add(businessTimeFri);

                    BusinessTime businessTimeSat=new BusinessTime();
                    businessTimeSat.setDay("Saturday");
                    businessTimeSat.setStartTime("Close");
                    businessTimeSat.setEndTime("Close");
                    businessTimeList.add(businessTimeSat);

                    hoursAdapter = new HoursAdapter(getActivity(), businessTimeList);
                    recyclerViewHours.setAdapter(hoursAdapter);
                }
            }
        }
        return rootView;
    }

}