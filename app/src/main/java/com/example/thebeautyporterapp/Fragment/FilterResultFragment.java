package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thebeautyporterapp.Adapter.SubMenuAdapter;
import com.example.thebeautyporterapp.Model.SubServiceModel;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterResultFragment extends Fragment {
    View rootView;
    private RecyclerView recyclerViewSubMenu;
    private SubMenuAdapter subMenuAdapter;
    ArrayList<SubServiceModel> filteredSubServiceModel = new ArrayList<SubServiceModel>();
    public FilterResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_filter_result, container, false);
        recyclerViewSubMenu = (RecyclerView) rootView.findViewById(R.id.recyclerViewCommon);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewSubMenu.setLayoutManager(mLayoutManager);
        recyclerViewSubMenu.setItemAnimator(new DefaultItemAnimator());
        filteredSubServiceModel = Hawk.get("filteredSubServiceModel", filteredSubServiceModel);
        if(filteredSubServiceModel.size()>0)
        {
            subMenuAdapter = new SubMenuAdapter(getActivity(), filteredSubServiceModel);
            recyclerViewSubMenu.setAdapter(subMenuAdapter);
        }


        return rootView;
    }

}
