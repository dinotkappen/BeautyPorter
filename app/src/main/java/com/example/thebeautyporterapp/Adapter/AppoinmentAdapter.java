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

import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Fragment.AppoinmentDetailFragment;
import com.example.thebeautyporterapp.Model.AppoinmentModel;
import com.example.thebeautyporterapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppoinmentAdapter extends RecyclerView.Adapter<AppoinmentAdapter.MyViewHolder> {

    private Context mContext;
    private List<AppoinmentModel> appoinmentModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView wishListTitle, wishListAdrz,txtTime;
        ImageView imgAppoinment;
        LinearLayout linearClick;

        public MyViewHolder(View view) {
            super(view);

            wishListTitle = (TextView) view.findViewById(R.id.wishListTitle);
            wishListAdrz = (TextView) view.findViewById(R.id.wishListAdrz);
            imgAppoinment = (ImageView) view.findViewById(R.id.imgAppoinment);
            linearClick = (LinearLayout) view.findViewById(R.id.linearClick);
            txtTime = (TextView) view.findViewById(R.id.txtTime);



        }
    }

    public AppoinmentAdapter(Context mContext, List<AppoinmentModel> appoinmentModel) {
        this.mContext = mContext;
        this.appoinmentModel = appoinmentModel;
    }

    @Override
    public AppoinmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appoinment_adapter_layout, parent, false);


        return new AppoinmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppoinmentAdapter.MyViewHolder holder, int position) {
        AppoinmentModel itemSubModel = appoinmentModel.get(position);
        final AppoinmentAdapter.MyViewHolder viewHolder = (AppoinmentAdapter.MyViewHolder) holder;
        String title = itemSubModel.getAppoinmentTitle();
        String date = itemSubModel.getAppoinmentDate();
         String imgUrl = itemSubModel.getAppoinmentImg();


        String rating = itemSubModel.getAppoinmentID();
        if (title != null && !title.isEmpty() && !title.equals("null")) {
            viewHolder.wishListTitle.setText(title);
        }

        if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null")) {
            Glide.with(mContext).load(imgUrl).into(holder.imgAppoinment);
        }


            if (date != null && !date.isEmpty() && !date.equals("null")) {
                SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd h:mm");
                Date newDate= null;
                try {
                    newDate = spf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                spf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                date = spf.format(newDate);
                String[] splited = date.split("\\s+");
                if (splited[0] != null && !splited[0].isEmpty() && !splited[0].equals("null")) {
                    viewHolder.wishListAdrz.setText(splited[0]);
                }
                if (splited[1] != null && !splited[1].isEmpty() && !splited[1].equals("null")) {
                    viewHolder.txtTime.setText(splited[1]+" "+splited[2]);
                }


            }



        if (rating != null && !rating.isEmpty() && !rating.equals("null")) {

            char first = rating.charAt(0);
            String exactRating=""+first;

        }
        holder.linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AppoinmentDetailFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);

                Bundle bundle = new Bundle();
                bundle.putString("selectedBookingHistoryID", itemSubModel.getAppoinmentID());
                bundle.putString("selectedName", itemSubModel.getAppoinmentTitle());
                bundle.putString("selectedImg", itemSubModel.getAppoinmentImg());
                bundle.putString("statusBooking", itemSubModel.getBooking_status());
                bundle.putString("contact_no", itemSubModel.getPhone());
                bundle.putString("bookdedDate", itemSubModel.getAppoinmentDate());
                fragment.setArguments(bundle);

                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appoinmentModel.size();
    }

    public void removeItem(int position) {
        appoinmentModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, appoinmentModel.size());
    }
}