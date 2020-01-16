package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thebeautyporterapp.Model.CheckOutModel;
import com.example.thebeautyporterapp.Model.GuestOrderModel;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import static com.example.thebeautyporterapp.Fragment.CheckOutFragment.checkOutAdapter;
import static com.example.thebeautyporterapp.Fragment.CheckOutFragment.deletePriceCalc;
import static com.example.thebeautyporterapp.Fragment.CheckOutFragment.itemList;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.MyViewHolder> {

    private Context mContext;
    private Context context;
    private List<CheckOutModel> checkOutModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPrice, txtServiceName, txtGuest, txtClose, txtTime, txtDate;
LinearLayout mLinear;
        private List<GuestOrderModel> guestOrderModel = null;

        public MyViewHolder(View view) {
            super(view);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            txtServiceName = (TextView) view.findViewById(R.id.txtServiceName);
            txtGuest = (TextView) view.findViewById(R.id.txtGuest);
            txtClose = (TextView) view.findViewById(R.id.txtClose);
            txtTime = (TextView) view.findViewById(R.id.txtTime);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            mLinear = (LinearLayout) view.findViewById(R.id.child_linear);
            guestOrderModel=new ArrayList<>();
        }
    }

    public CheckOutAdapter(Context mContext, List<CheckOutModel> checkOutModel,Context context) {
        this.mContext = mContext;
        this.checkOutModel = checkOutModel;
        this.context=context;
    }

    @Override
    public CheckOutAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chech_out_item, parent, false);


        return new CheckOutAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CheckOutAdapter.MyViewHolder holder, int position) {
        CheckOutModel itemSubModel = checkOutModel.get(position);
        final CheckOutAdapter.MyViewHolder viewHolder = (CheckOutAdapter.MyViewHolder) holder;
        String price = itemSubModel.getPrice();
        String serviceName = itemSubModel.getServiceName();
        String guest = itemSubModel.getGuest();
        String time = itemSubModel.getTime();
        String date = itemSubModel.getDate();
        viewHolder.guestOrderModel=itemSubModel.getGuestOrderModel();
        Log.v("guestSize",""+viewHolder.guestOrderModel.size());


        if (serviceName != null && !serviceName.isEmpty() && !serviceName.equals("null")) {
            viewHolder.txtServiceName.setText(serviceName);
        }
        if (price != null && !price.isEmpty() && !price.equals("null")) {
            viewHolder.txtPrice.setText(price);
        }
//        if (guest != null && !guest.isEmpty() && !guest.equals("null")) {
//            viewHolder.txtGuest.setText(""+viewHolder.guestOrderModel.size());
//            Log.v("totalGuest",""+viewHolder.guestOrderModel.size());
//        }


        if (date != null && !date.isEmpty() && !date.equals("null")) {
            viewHolder.txtDate.setText(date);
        }
        if (time != null && !time.isEmpty() && !time.equals("null")) {
            viewHolder.txtTime.setText(time);
        }
        viewHolder.txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if( viewHolder.guestOrderModel.size()>0) {
            for (int i = 0; i < viewHolder.guestOrderModel.size(); i++) {
                View childView = inflater.inflate(R.layout.checkout_guest_layout, null);
                TextView txtGuestServiceName = (TextView) childView.findViewById(R.id.txtGuestServiceName);
                TextView txtGuestPrice = (TextView) childView.findViewById(R.id.txtGuestPrice);
                txtGuestServiceName.setText(viewHolder.guestOrderModel.get(i).getServiceName()+" For "+viewHolder.guestOrderModel.get(i).getGuestName());
                txtGuestPrice.setText(viewHolder.guestOrderModel.get(i).getCost());
                viewHolder.mLinear.addView(childView);
            }
        }

    }

    @Override
    public int getItemCount() {
        return checkOutModel.size();
    }

    public void removeItem(int position) {
        checkOutModel.remove(position);
        notifyItemRemoved(position);
        itemList.remove(position);
        Hawk.put("itemList",itemList);
        checkOutAdapter.notifyDataSetChanged();
        notifyItemRangeChanged(position, checkOutModel.size());
        deletePriceCalc();
    }
}
