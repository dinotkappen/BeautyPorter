package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thebeautyporterapp.Model.TimeModel;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import java.util.ArrayList;
import java.util.List;

import static com.example.thebeautyporterapp.Fragment.DateTimeFragment.listSelectedTime;


public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder> {

    private Context mContext;
    private List<TimeModel> timeModel;
    public static   ArrayList<String> selectedTimeList=new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTime;
        LinearLayout linearBackCircle;

        public MyViewHolder(View view) {
            super(view);

            txtTime = (TextView) view.findViewById(R.id.txtTime);
            linearBackCircle = (LinearLayout) view.findViewById(R.id.linearBackCircle);


        }
    }

    public TimeAdapter(Context mContext, List<TimeModel> timeModel) {
        this.mContext = mContext;
        this.timeModel = timeModel;
    }

    @Override
    public TimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_time_adapter, parent, false);


        return new TimeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TimeAdapter.MyViewHolder holder, final int position) {
        final TimeModel itemSubModel = timeModel.get(position);
        final TimeAdapter.MyViewHolder viewHolder = (TimeAdapter.MyViewHolder) holder;
        viewHolder.txtTime.setText(itemSubModel.getTime());
        String timeStatus=itemSubModel.getTimeID();
        Log.v("timeStatus",timeStatus);
//        viewHolder.txtTime.setBackgroundResource(R.color.colorWhite);
        if (itemSubModel.getStatus()==true) {
            viewHolder.txtTime.setBackgroundResource(R.drawable.circle);
            viewHolder.txtTime.setTextColor(Color.WHITE);
            viewHolder.linearBackCircle.setBackgroundResource(R.drawable.circle);
            viewHolder.txtTime.setClickable(false);


        } else {
            if(timeStatus.equals("free"))
            {
                viewHolder.txtTime.setBackgroundResource(R.drawable.round_cicle);
                viewHolder.txtTime.setTextColor(Color.BLACK);
                viewHolder.linearBackCircle.setBackgroundResource(R.drawable.round_black_border_trans_back);
                viewHolder.txtTime.setClickable(true);
            }
            else
            {
                viewHolder.txtTime.setBackgroundResource(R.drawable.circle);
                viewHolder.txtTime.setTextColor(Color.WHITE);
                viewHolder.linearBackCircle.setBackgroundResource(R.drawable.circle);
                viewHolder.txtTime.setClickable(false);

            }


        }

        viewHolder.txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Hawk.init(mContext)
                        .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                        .setStorage(HawkBuilder.newSqliteStorage(mContext))
                        .setLogLevel(LogLevel.FULL)
                        .build();
                for (int i = 0; i < timeModel.size(); i++) {
                    timeModel.get(i).setStatus(false);

                }

                viewHolder.txtTime.setBackgroundResource(R.drawable.round_cicle);
                viewHolder.txtTime.setTextColor(Color.BLACK);
                 viewHolder.linearBackCircle.setBackgroundResource(R.drawable.round_black_border_trans_back);
                timeModel.get(position).setStatus(true);
                if (timeModel.get(position).getStatus() == true) {

                    viewHolder.txtTime.setBackgroundResource(R.drawable.circle);
                    viewHolder.txtTime.setTextColor(Color.WHITE);
                    viewHolder.linearBackCircle.setBackgroundResource(R.drawable.circle);




                } else {
                    viewHolder.txtTime.setBackgroundResource(R.drawable.round_cicle);
                    viewHolder.txtTime.setTextColor(Color.BLACK);
                     viewHolder.linearBackCircle.setBackgroundResource(R.drawable.round_black_border_trans_back);


                }
               notifyDataSetChanged();
//                viewHolder.txtTime.setBackgroundResource(R.drawable.rectangle_with_rounded_primarycolor);
//                viewHolder.txtTime.setTextColor(Color.WHITE);
                Log.v("time",""+itemSubModel.getTime());
                Hawk.put("selectedTimeSlot",itemSubModel.getTime());
                Hawk.put("listSelectedTime",listSelectedTime);
                selectedTimeList.add(itemSubModel.getTime());


            }
        });
    }

    @Override
    public int getItemCount() {
        return timeModel.size();
    }

    public void removeItem(int position) {
        timeModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, timeModel.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}