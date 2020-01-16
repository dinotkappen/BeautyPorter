package com.example.thebeautyporterapp.Adapter.ServicesAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessService;
import com.example.thebeautyporterapp.R;

import java.util.List;

import static com.example.thebeautyporterapp.Fragment.TabServicesFragment.timeList;

public class ServicesItemAdapter extends RecyclerView.Adapter<ServicesItemAdapter.MyViewHolder> {

    int row_index;
    private Context mContext;
    private  List<BusinessService> appoinmentModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, wishListAdrz,txtOrderHistoryTime;
        ImageView imgIcon;
        LinearLayout len_click;


        public MyViewHolder(View view) {
            super(view);

            Title =  view.findViewById(R.id.txt_name);
            wishListAdrz = (TextView) view.findViewById(R.id.wishListAdrz);
            imgIcon = (ImageView) view.findViewById(R.id.img_icon);
            len_click = view.findViewById(R.id.len_colore_cahnge);


        }
    }

    public ServicesItemAdapter(Context mContext, List<BusinessService> appoinmentModel) {
        this.mContext = mContext;
        this.appoinmentModel = appoinmentModel;
    }

    @Override
    public ServicesItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_item, parent, false);


        return new ServicesItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServicesItemAdapter.MyViewHolder holder, int position) {
        BusinessService itemSubModel = appoinmentModel.get(position);
        final ServicesItemAdapter.MyViewHolder viewHolder = (ServicesItemAdapter.MyViewHolder) holder;

        holder.Title.setText(itemSubModel.getName());
        Glide.with(mContext).load(itemSubModel.getIcon()).into(holder.imgIcon);
        holder.len_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();
                timeList(itemSubModel.getService());
            }
        });
        if(row_index==position){
            holder.len_click.setBackgroundResource(R.drawable.round_corner);
//            holder.title.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.len_click.setBackgroundResource(R.drawable.round_corner_white);
//            holder.title.setTextColor(Color.parseColor("#000000"));
        }

//     //   String adrz = itemSubModel.getAppoinmentDate();
//        String  adrz="12-04-2019";
//        // String imgUrl = itemSubModel.getWishListImgUrl();
//
//        String imgUrl="http://click.whytecreations.in/public/uploads/business/big/t1qrwFti9sk5i0HLzBp14XeSpyK2FC5C5WtSBIuO.jpeg";
////        String rating = itemSubModel.getAppoinmentID();
//        if (title != null && !title.isEmpty() && !title.equals("null")) {
//            viewHolder.wishListTitle.setText(title);
//        }
//        if (adrz != null && !adrz.isEmpty() && !adrz.equals("null")) {
//            viewHolder.wishListAdrz.setText(adrz);
//        }
//        if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null")) {
//            Glide.with(mContext).load(imgUrl).into(holder.imgWishList);
//        }
//
////        if (rating != null && !rating.isEmpty() && !rating.equals("null")) {
////            holder.simpleRatingBar.setRating(Float.parseFloat(rating));
////        }
//        if (rating != null && !rating.isEmpty() && !rating.equals("null")) {
//
//            char first = rating.charAt(0);
//            String exactRating=""+first;
//
//
//
//        }

    }

    @Override
    public int getItemCount() {

        if (appoinmentModel != null)
            return appoinmentModel.size();
        else
            return 0;
    }

    public void removeItem(int position) {
        appoinmentModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, appoinmentModel.size());
    }
}