package com.example.thebeautyporterapp.Adapter.ServicesAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.thebeautyporterapp.Model.ServicesModel.Service;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class ServicesTimeAdapter extends RecyclerView.Adapter<ServicesTimeAdapter.MyViewHolder> {

    int row_index;
    private Context mContext;
    private List<Service> appoinmentModel;
    String selectedMode, selectedPrice;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTime, txtPrice;
        RadioButton radio_time;
        LinearLayout fram_click;


        public MyViewHolder(View view) {
            super(view);

            txtTime = view.findViewById(R.id.txt_time);
            txtPrice = view.findViewById(R.id.txt_price);
            radio_time = view.findViewById(R.id.radio_type);
            fram_click = view.findViewById(R.id.fram_click);


        }
    }

    public ServicesTimeAdapter(Context mContext, List<Service> appoinmentModel) {
        this.mContext = mContext;
        this.appoinmentModel = appoinmentModel;
    }

    @Override
    public ServicesTimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_time_itm, parent, false);


        return new ServicesTimeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServicesTimeAdapter.MyViewHolder holder, int position) {
        Service itemSubModel = appoinmentModel.get(position);
        selectedMode = Hawk.get("selectedMode", "");
        final ServicesTimeAdapter.MyViewHolder viewHolder = (ServicesTimeAdapter.MyViewHolder) holder;
        holder.radio_time.setText(itemSubModel.getServiceName());
        holder.txtTime.setText(itemSubModel.getDuration() + " " + itemSubModel.getTimeType());

        if (selectedMode.equals("Salon")) {
            selectedPrice = itemSubModel.getPrice();
            if (selectedPrice != null && !selectedPrice.isEmpty() && !selectedPrice.equals("null")) {
                holder.txtPrice.setText(selectedPrice + " QAR");

            } else {
                holder.txtPrice.setText("0" + " QAR");

            }

        } else if (selectedMode.equals("Spa & Clinics")) {
            String selectedPrice = itemSubModel.getPrice1();
            if (selectedPrice != null && !selectedPrice.isEmpty() && !selectedPrice.equals("null")) {
                holder.txtPrice.setText(selectedPrice + " QAR");

            } else {
                holder.txtPrice.setText("0" + " QAR");

            }
        } else if (selectedMode.equals("Home Service")) {
            String selectedPrice = itemSubModel.getPrice2();
            if (selectedPrice != null && !selectedPrice.isEmpty() && !selectedPrice.equals("null")) {
                holder.txtPrice.setText(selectedPrice + " QAR");

            } else {
                holder.txtPrice.setText("0" + " QAR");

            }
        }

//        holder.radio_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });
        holder.fram_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;

                notifyDataSetChanged();
            }
        });


        if (row_index == position) {
            holder.radio_time.setChecked(true);
            Hawk.put("selectedServiceDetails", itemSubModel);
            String Name = itemSubModel.getServiceName();
            String ID = "" + itemSubModel.getId();
            String sgf = itemSubModel.getPrice();
            Hawk.put("selectedServiceName", itemSubModel.getServiceName());
            String hj = selectedMode;
            if (selectedMode.equals("Salon")) {
                String price=itemSubModel.getPrice();
                Hawk.put("selectedServicePrice", itemSubModel.getPrice());
            }
            else  if (selectedMode.equals("Spa & Clinics")) {
                String price=itemSubModel.getPrice1();
                Hawk.put("selectedServicePrice", itemSubModel.getPrice1());
            }
            else  if (selectedMode.equals("Home Service")) {
                String price=itemSubModel.getPrice2();
                Hawk.put("selectedServicePrice", itemSubModel.getPrice2());
            }
            Hawk.put("selectedServiceID", itemSubModel.getId());

//            holder.title.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.radio_time.setChecked(false);
//            holder.title.setTextColor(Color.parseColor("#000000"));
        }

//

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