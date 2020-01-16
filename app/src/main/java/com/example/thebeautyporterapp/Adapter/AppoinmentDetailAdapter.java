package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thebeautyporterapp.Model.AppoinmentDetailModel;
import com.example.thebeautyporterapp.R;

import java.util.List;

public class AppoinmentDetailAdapter extends RecyclerView.Adapter<AppoinmentDetailAdapter.MyViewHolder> {

    private Context mContext;
    private List<AppoinmentDetailModel> appoinmentDetailModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDate, txtTime, txtService,txtAmount;


        public MyViewHolder(View view) {
            super(view);

            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            txtService = (TextView) view.findViewById(R.id.txtService);
            txtAmount = (TextView) view.findViewById(R.id.txtAmount);

        }
    }

    public AppoinmentDetailAdapter(Context mContext, List<AppoinmentDetailModel> appoinmentDetailModel) {
        this.mContext = mContext;
        this.appoinmentDetailModel = appoinmentDetailModel;
    }

    @Override
    public AppoinmentDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appoinment_detail_adapter_layout, parent, false);


        return new AppoinmentDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppoinmentDetailAdapter.MyViewHolder holder, int position) {
        AppoinmentDetailModel itemSubModel = appoinmentDetailModel.get(position);
        final AppoinmentDetailAdapter.MyViewHolder viewHolder = (AppoinmentDetailAdapter.MyViewHolder) holder;


        String date = itemSubModel.getDateDetail();
        String time = itemSubModel.getTimeDetail();
        String service = itemSubModel.getServiceDetail();
        String amount = itemSubModel.getPriceDetail();
        if (date != null && !date.isEmpty() && !date.equals("null")) {
            viewHolder.txtDate.setText(date);
        }
        if (time != null && !time.isEmpty() && !time.equals("null")) {
            viewHolder.txtTime.setText(time);
        }
        if (service != null && !service.isEmpty() && !service.equals("null")) {
            viewHolder.txtService.setText(service);
        }
        if (amount != null && !amount.isEmpty() && !amount.equals("null")) {
            viewHolder.txtAmount.setText(amount+" "+mContext.getString(R.string.Qar));
        }





    }

    @Override
    public int getItemCount() {
        return appoinmentDetailModel.size();
    }

    public void removeItem(int position) {
        appoinmentDetailModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, appoinmentDetailModel.size());
    }
}