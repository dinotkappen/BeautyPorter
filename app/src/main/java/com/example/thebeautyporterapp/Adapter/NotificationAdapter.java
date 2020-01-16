package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thebeautyporterapp.Model.NotificationModel;
import com.example.thebeautyporterapp.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private List<NotificationModel> notificationModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationTitle, notificationDescription;
        ImageView imgWishList;

        public MyViewHolder(View view) {
            super(view);

            notificationTitle = (TextView) view.findViewById(R.id.notificationTitle);
            notificationDescription = (TextView) view.findViewById(R.id.notificationDescription);
            imgWishList=(ImageView)view.findViewById(R.id.imgWishList);

        }
    }

    public NotificationAdapter(Context mContext, List<NotificationModel> notificationModel) {
        this.mContext = mContext;
        this.notificationModel = notificationModel;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_adapter_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NotificationModel itemSubModel = notificationModel.get(position);
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        String notificationTitle=itemSubModel.getNotificationTitle();
        String description=itemSubModel.getNotificationDescription();
//        String imgUrl="http://click.whytecreations.in/public/uploads/business/big/t1qrwFti9sk5i0HLzBp14XeSpyK2FC5C5WtSBIuO.jpeg";
        if (notificationTitle != null && !notificationTitle.isEmpty() && !notificationTitle.equals("null"))
        {
            viewHolder.notificationTitle.setText(notificationTitle);
        }
        if (description != null && !description.isEmpty() && !description.equals("null"))
        {
            viewHolder.notificationDescription.setText(description);
        }
//        if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null")) {
//            Glide.with(mContext).load(imgUrl).into(holder.imgWishList);
//        }

    }

    @Override
    public int getItemCount() {
        return notificationModel.size();
    }

    public void removeItem(int position) {
        notificationModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, notificationModel.size());
    }
}

