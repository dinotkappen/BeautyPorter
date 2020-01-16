package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Model.ReviewModel;
import com.example.thebeautyporterapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {

    private Context mContext;
    private List<ReviewModel> reviewModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView reviewTitle, txtReviewCmnt, txtReviewDate;
        ImageView circulrImgReview;
        RatingBar reviewListRatingBar;

        public MyViewHolder(View view) {
            super(view);

            reviewTitle = (TextView) view.findViewById(R.id.reviewTitle);
            txtReviewCmnt = (TextView) view.findViewById(R.id.txtReviewCmnt);
            txtReviewDate = (TextView) view.findViewById(R.id.txtReviewDate);

            circulrImgReview = (ImageView) view.findViewById(R.id.circulrImgReview);
            reviewListRatingBar = (RatingBar) view.findViewById(R.id.reviewListRatingBar);


        }
    }

    public RatingAdapter(Context mContext, List<ReviewModel> reviewModel) {
        this.mContext = mContext;
        this.reviewModel = reviewModel;
    }

    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_adapter_layout, parent, false);


        return new RatingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RatingAdapter.MyViewHolder holder, int position) {
        ReviewModel itemSubModel = reviewModel.get(position);
        final RatingAdapter.MyViewHolder viewHolder = (RatingAdapter.MyViewHolder) holder;


        String reviewname = itemSubModel.getReviewName();
        if (reviewname != null && !reviewname.isEmpty() && !reviewname.equals("null")) {
            viewHolder.reviewTitle.setText(reviewname);
        }
        String reviewCmnt = itemSubModel.getReviewCmnt();
        if (reviewCmnt != null && !reviewCmnt.isEmpty() && !reviewCmnt.equals("null")) {
            viewHolder.txtReviewCmnt.setText(reviewCmnt);
        }
        String reviewDate = itemSubModel.getReviewDate();
        if (reviewDate != null && !reviewDate.isEmpty() && !reviewDate.equals("null")) {

            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy" );
            Date date;
            try {
                date = originalFormat.parse(reviewDate);

                viewHolder.txtReviewDate.setText("" + targetFormat.format(date));

            } catch (ParseException ex) {
                String hj=ex.getMessage().toString();
                // Handle Exception.
            }



        }
        String rate = itemSubModel.getReviewCount();
        if (rate != null && !rate.isEmpty() && !rate.equals("null")) {

            char first = rate.charAt(0);
            String exactRating = "" + first;
            if (exactRating != null && !exactRating.isEmpty() && !exactRating.equals("null")) {
                holder.reviewListRatingBar.setRating(Float.parseFloat(exactRating));
            }


        }

        String imgUrl = itemSubModel.getReviewImgUrl();
        if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null")) {
            Glide.with(mContext).load(imgUrl).into(holder.circulrImgReview);
        }


    }

    @Override
    public int getItemCount() {
        return reviewModel.size();
    }

    public void removeItem(int position) {
        reviewModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, reviewModel.size());
    }
}