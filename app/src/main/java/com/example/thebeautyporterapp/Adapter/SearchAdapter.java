package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Fragment.ServiceDetailFragment;
import com.example.thebeautyporterapp.Model.SubServiceModel;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context mContext;
    private List<SubServiceModel> subServiceModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView searchTitle, searchAdrz,searchRating;
        ImageView imgSearch;
        LinearLayout linearClick;



        public MyViewHolder(View view) {
            super(view);

            searchTitle = (TextView) view.findViewById(R.id.searchTitle);
            searchAdrz = (TextView) view.findViewById(R.id.searchAdrz);
            searchRating=(TextView)view.findViewById(R.id.searchRating);
            imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
            linearClick=(LinearLayout)view.findViewById(R.id.linearClick);


//            LayerDrawable stars = (LayerDrawable) serviceListRatingBar.getProgressDrawable();
//            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            //    stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);


        }
    }

    public SearchAdapter(Context mContext, List<SubServiceModel> subServiceModel) {
        this.mContext = mContext;
        this.subServiceModel = subServiceModel;
    }

    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sarch_adapter_layout, parent, false);


        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchAdapter.MyViewHolder holder, int position) {
        final SubServiceModel itemSubModel = subServiceModel.get(position);
        final SearchAdapter.MyViewHolder viewHolder = (SearchAdapter.MyViewHolder) holder;

        final String title = itemSubModel.getSubServiceTitle();
        String adrz = itemSubModel.getSubServiceAdrz();
        String imgUrl = itemSubModel.getSubServiceImgUrl();
        String rating = itemSubModel.getSubServiceRating();
        final String subServiceDescription = itemSubModel.getSubServiceDescription();



        String serviceIDS = itemSubModel.getServicesIDS();
        if (serviceIDS != null && !serviceIDS.isEmpty() && !serviceIDS.equals("null")) {



            if (title != null && !title.isEmpty() && !title.equals("null")) {
                viewHolder.searchTitle.setText(title);
            }
            if (adrz != null && !adrz.isEmpty() && !adrz.equals("null")) {
                viewHolder.searchAdrz.setText(adrz);
            }
            if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null")) {
                Glide.with(mContext).load(imgUrl).into(holder.imgSearch);

            }
            if (rating != null && !rating.isEmpty() && !rating.equals("null")) {

                String[] separated = rating.split(",");
                String singleRating= separated[0]; // this will contain "Fruit"
                String reviews= separated[1]; // this will contain " they taste good"
                if (singleRating != null && !singleRating.isEmpty() && !singleRating.equals("null")) {
                    viewHolder.searchRating.setText(singleRating+" "+mContext.getResources().getString(R.string.ratings));
                }
                else
                {
                    viewHolder.searchRating.setText("0"+" "+mContext.getResources().getString(R.string.ratings));
                }

            }
            else
            {

                viewHolder.searchRating.setText("0"+" "+mContext.getResources().getString(R.string.ratings));


            }



        }




        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ServiceDetailFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);

                bundle.putString("subServiceDescription",subServiceDescription);
                bundle.putString("latitude",itemSubModel.getLatitude());
                bundle.putString("longitude",itemSubModel.getLongitude());
                Log.v("subServiceID",itemSubModel.getSubServiceID());
                Hawk.put("selecteSubServiceID", itemSubModel.getSubServiceID());
                Hawk.put("subServiceName", itemSubModel.getSubServiceTitle());

                fragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });

//        holder.imgSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


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