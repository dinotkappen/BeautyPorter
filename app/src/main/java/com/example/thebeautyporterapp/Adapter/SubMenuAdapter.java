package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Fragment.ServiceDetailFragment;
import com.example.thebeautyporterapp.Model.ServicesModel.Service;
import com.example.thebeautyporterapp.Model.SubServiceModel;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import java.util.ArrayList;
import java.util.List;

public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.MyViewHolder> {

    private Context mContext;
    private List<SubServiceModel> subServiceModel;
    List<Service> filterServiceModelList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceListTitle, serviceListAdrz, serviceListPrice, txtRating;
        ImageView imgServiceList, imgSpa, imgHome;
        LinearLayout linearClick;
        //  final RatingBar serviceListRatingBar;


        public MyViewHolder(View view) {
            super(view);

            serviceListTitle = (TextView) view.findViewById(R.id.subServiceTitle);
            serviceListAdrz = (TextView) view.findViewById(R.id.subServiceAdrz);
            imgServiceList = (ImageView) view.findViewById(R.id.img_services_list);
            serviceListPrice = (TextView) view.findViewById(R.id.serviceListPrice);
            txtRating = (TextView) view.findViewById(R.id.txtRating);
            linearClick = (LinearLayout) view.findViewById(R.id.linearClick);
            imgSpa = (ImageView) view.findViewById(R.id.imgSpa);
            imgHome = (ImageView) view.findViewById(R.id.imgHome);
            imgSpa.setVisibility(View.GONE);
            imgHome.setVisibility(View.GONE);
            // serviceListRatingBar = (RatingBar) view.findViewById(R.id.serviceListRatingBar);

//            LayerDrawable stars = (LayerDrawable) serviceListRatingBar.getProgressDrawable();
//            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            //    stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);


        }
    }

    public SubMenuAdapter(Context mContext, List<SubServiceModel> subServiceModel) {
        this.mContext = mContext;
        this.subServiceModel = subServiceModel;
    }

    @Override
    public SubMenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.submenu_adapter_layout_n, parent, false);


        return new SubMenuAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubMenuAdapter.MyViewHolder holder, int position) {

        filterServiceModelList = Hawk.get("filterServiceModelList", filterServiceModelList);

        final SubServiceModel itemSubModel = subServiceModel.get(position);
        final SubMenuAdapter.MyViewHolder viewHolder = (SubMenuAdapter.MyViewHolder) holder;

        final String title = itemSubModel.getSubServiceTitle();
        String adrz = itemSubModel.getSubServiceAdrz();
        String imgUrl = itemSubModel.getSubServiceImgUrl();
        String rating = itemSubModel.getSubServiceRating();
        String selectedBussinessID = itemSubModel.getSubServiceID();
        viewHolder.imgSpa.setVisibility(View.GONE);
        viewHolder.imgHome.setVisibility(View.GONE);


        for (int i = 0; i < filterServiceModelList.size(); i++) {
            String bussinessID = filterServiceModelList.get(i).getBusinessId();
            String mode = filterServiceModelList.get(i).getMode();
            if (selectedBussinessID.equals(bussinessID)) {
                if (mode.equals("Home Service")) {
                    holder.imgHome.setBackgroundResource(R.mipmap.home_circle);
                    viewHolder.imgHome.setVisibility(View.VISIBLE);


                } else if (mode.equals("Salon, Spa & Clinics")) {
                    viewHolder.imgSpa.setVisibility(View.VISIBLE);
                    holder.imgSpa.setBackgroundResource(R.mipmap.work);

                } else if (mode.equals("Stylists")) {
                    viewHolder.imgSpa.setVisibility(View.GONE);
                    viewHolder.imgHome.setVisibility(View.GONE);

                }
            }
        }

        final String subServiceDescription = itemSubModel.getSubServiceDescription();

        String selectedMainServiceID = itemSubModel.getSelectedMainServiceID();
        if (selectedMainServiceID != null && !selectedMainServiceID.isEmpty() && !selectedMainServiceID.equals("null")) {

            String serviceIDS = itemSubModel.getServicesIDS();
            if (serviceIDS != null && !serviceIDS.isEmpty() && !serviceIDS.equals("null")) {

                if (serviceIDS.contains(selectedMainServiceID)) {

                    if (title != null && !title.isEmpty() && !title.equals("null")) {
                        viewHolder.serviceListTitle.setText(title);
                    }
                    if (adrz != null && !adrz.isEmpty() && !adrz.equals("null")) {
                        viewHolder.serviceListAdrz.setText(adrz);
                    }
                    if (rating != null && !rating.isEmpty() && !rating.equals("null")) {

                        String[] separated = rating.split(",");
                        String singleRating = separated[0]; // this will contain "Fruit"
                        String reviews = separated[1]; // this will contain " they taste good"
                        if (singleRating != null && !singleRating.isEmpty() && !singleRating.equals("null")) {
                            viewHolder.txtRating.setText(singleRating + " " + mContext.getResources().getString(R.string.ratings));
                        } else {
                            viewHolder.txtRating.setText("0" + " " + mContext.getResources().getString(R.string.ratings));
                        }

                    } else {

                        viewHolder.txtRating.setText("0" + " " + mContext.getResources().getString(R.string.ratings));


                    }
                    if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null")) {
                        Glide.with(mContext).load(imgUrl).into(holder.imgServiceList);

                    }

                }

            }

        }



        Hawk.put("selectedBusinessID", itemSubModel.getSubServiceID());
        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.init(mContext)
                        .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                        .setStorage(HawkBuilder.newSqliteStorage(mContext))
                        .setLogLevel(LogLevel.FULL)
                        .build();

                String selecteSubServiceID = itemSubModel.getSubServiceID();
                String subServiceName = itemSubModel.getSubServiceTitle();
                String availableModes = "";
                for (int i = 0; i < filterServiceModelList.size(); i++) {

                    String id=filterServiceModelList.get(i).getBusinessId();
                    if(selectedBussinessID.equals(id))
                    {
                        String mode=filterServiceModelList.get(i).getMode();
                        if(mode.contains("Home Service"))
                        {
                            if(!availableModes.contains("Home Service"))
                            {
                                availableModes = availableModes + " , " + "Home Service";
                            }
                        }
                        if (mode.contains("Salon, Spa & Clinics")) {
                            if(!availableModes.contains("Salon, Spa & Clinics"))
                            {
                                availableModes = availableModes + " , " + "Salon, Spa & Clinics";
                            }
                        }
                        if (mode.contains("Stylists")) {
                            if(!availableModes.contains("Stylists"))
                            {
                                availableModes = availableModes + " , " + "Stylists";
                            }
                        }
                    }
                }

                if (selecteSubServiceID != null && !selecteSubServiceID.isEmpty() && !selecteSubServiceID.equals("null")) {

                    if (subServiceName != null && !subServiceName.isEmpty() && !subServiceName.equals("null")) {
                        Hawk.put("title", title);
                        Hawk.put("selecteSubServiceID", itemSubModel.getSubServiceID());
                        Hawk.put("subServiceName", itemSubModel.getSubServiceTitle());
                        Hawk.put("subServiceDescription", subServiceDescription);
                        Hawk.put("latitude", itemSubModel.getLatitude());
                        Hawk.put("longitude", itemSubModel.getLongitude());
                        Hawk.put("locations", itemSubModel.getLocation());

                        String vb = itemSubModel.getSubServiceID();
                        Hawk.put("selectedBusinessID", itemSubModel.getSubServiceID());
                        Hawk.put("selectedBusinessName", itemSubModel.getSubServiceTitle());
                        Hawk.put("selectedBusinessAdrz", itemSubModel.getSubServiceAdrz());
                        Hawk.put("selectedBusinessRating", itemSubModel.getSubServiceRating());
                        Hawk.put("isfavourite", itemSubModel.getIsfavourite());


                        Fragment fragment = new ServiceDetailFragment();
                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        Bundle args = new Bundle();
                        args.putString("availableModes", availableModes);
                        fragment.setArguments(args);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(mContext, "Please select another service", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, "Please select another service", Toast.LENGTH_SHORT).show();
                }


            }
        });
        holder.imgServiceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return subServiceModel.size();
    }

    public void removeItem(int position) {
        subServiceModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, subServiceModel.size());
    }
}