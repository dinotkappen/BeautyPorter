package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.thebeautyporterapp.Model.ServicesModel.BusinessTime;
import com.example.thebeautyporterapp.R;

import java.util.List;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.MyViewHolder> {

    private Context mContext;
    private List<BusinessTime> businessTimeModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDay, txtTime;

LinearLayout linearMain,linearNoData;
        public MyViewHolder(View view) {
            super(view);

            txtDay = (TextView) view.findViewById(R.id.txtDay);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
           // linearMain=(LinearLayout)view.findViewById(R.id.linearMain);
//            linearMain.setVisibility(View.VISIBLE);
//            linearNoData=(LinearLayout)view.findViewById(R.id.linearNoData);
         //   linearNoData.setVisibility(View.GONE);


        }
    }

    public HoursAdapter(Context mContext, List<BusinessTime> businessTimeModel) {
        this.mContext = mContext;
        this.businessTimeModel = businessTimeModel;
    }

    @Override
    public HoursAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hours_adapter_layout, parent, false);


        return new HoursAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HoursAdapter.MyViewHolder holder, int position) {

        BusinessTime itemSubModel = businessTimeModel.get(position);

        final HoursAdapter.MyViewHolder viewHolder = (HoursAdapter.MyViewHolder) holder;
        String txtDay=itemSubModel.getDay();
        String startTime=itemSubModel.getStartTime();
        String endTime=itemSubModel.getEndTime();
        int size=businessTimeModel.size();
        Log.v("sizeMatch",""+size);



//        String imgUrl="http://click.whytecreations.in/public/uploads/business/big/t1qrwFti9sk5i0HLzBp14XeSpyK2FC5C5WtSBIuO.jpeg";
            if (txtDay != null && !txtDay.isEmpty() && !txtDay.equals("null")) {
                viewHolder.txtDay.setText(txtDay);
            } else {

            }
            if (startTime != null && !startTime.isEmpty() && !startTime.equals("null")) {
                if (endTime != null && !endTime.isEmpty() && !endTime.equals("null")) {
                    if(startTime.equals("Close"))
                    {
                        viewHolder.txtTime.setText("Close");
                    }
                    else
                    {
                        viewHolder.txtTime.setText(startTime + "-" + endTime);
                    }

                }

            }



    }

    @Override
    public int getItemCount() {
        return businessTimeModel.size();
    }

    public void removeItem(int position) {
        businessTimeModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, businessTimeModel.size());
    }
}
