package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Model.ServiceDetailModel;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class ServiceDetailAdpater extends RecyclerView.Adapter<ServiceDetailAdpater.MyViewHolder> {

    private Context mContext;
    private List<ServiceDetailModel> subServiceModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceListTitle, serviceListAdrz;
        ImageView imgCirular;
      //  final RatingBar serviceListRatingBar;


        public MyViewHolder(View view) {
            super(view);

            serviceListTitle = (TextView) view.findViewById(R.id.serviceListTitle);
            serviceListAdrz = (TextView) view.findViewById(R.id.serviceListAdrz);
            imgCirular=(ImageView)view.findViewById(R.id.imgCirular);
//            serviceListRatingBar = (RatingBar) view.findViewById(R.id.serviceListRatingBar);

//            LayerDrawable stars = (LayerDrawable) serviceListRatingBar.getProgressDrawable();
//            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            //    stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);


        }
    }

    public ServiceDetailAdpater(Context mContext, List<ServiceDetailModel> subServiceModel) {
        this.mContext = mContext;
        this.subServiceModel = subServiceModel;
    }

    @Override
    public ServiceDetailAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subservice_adapter_layout, parent, false);


        return new ServiceDetailAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiceDetailAdpater.MyViewHolder holder, int position) {
        final ServiceDetailModel itemSubModel = subServiceModel.get(position);
        final ServiceDetailAdpater.MyViewHolder viewHolder = (ServiceDetailAdpater.MyViewHolder) holder;

        final String title = itemSubModel.getSubServiceTitle();
        String adrz = itemSubModel.getSubServiceAdrz();
      //  String imgUrl = itemSubModel.getSubServiceImgUrl();
        String imgUrl="http://click.whytecreations.in/public/uploads/business/big/t1qrwFti9sk5i0HLzBp14XeSpyK2FC5C5WtSBIuO.jpeg";
        String rating = itemSubModel.getSubServiceRating();
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
                    if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null")) {
                        Glide.with(mContext).load(imgUrl).into(holder.imgCirular);

                    }

                }

            }

        }


        if (rating != null && !rating.isEmpty() && !rating.equals("null")) {

            char first = rating.charAt(0);
            String exactRating=""+first;
//            if (exactRating != null && !exactRating.isEmpty() && !exactRating.equals("null")) {
//                holder.serviceListRatingBar.setRating(Float.parseFloat(exactRating));
//            }


        }
        Hawk.put("selectedBusinessID",itemSubModel.getSubServiceID());



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