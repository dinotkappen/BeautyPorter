package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.thebeautyporterapp.Model.OfferModel;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    private Context mContext;
    private List<OfferModel> offerModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView offerTitle, offerDescription, offerPrice;
        EditText edtCircleCode;
        CardView btnCopy;


        public MyViewHolder(View view) {
            super(view);

            offerTitle = (TextView) view.findViewById(R.id.offerTitle);
            offerDescription = (TextView) view.findViewById(R.id.offerDescription);
            offerPrice = (TextView) view.findViewById(R.id.offerPrice);
            edtCircleCode= (EditText) view.findViewById(R.id.edtCircleCode);
            btnCopy=(CardView)view.findViewById(R.id.btnCopy);


        }
    }

    public OfferAdapter(Context mContext, List<OfferModel> offerModel) {
        this.mContext = mContext;
        this.offerModel = offerModel;
    }

    @Override
    public OfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_adapter_layout, parent, false);


        return new OfferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OfferAdapter.MyViewHolder holder, int position) {
        final OfferModel itemSubModel = offerModel.get(position);
        final OfferAdapter.MyViewHolder viewHolder = (OfferAdapter.MyViewHolder) holder;
        String title=itemSubModel.getOfferTitle();
        String description=itemSubModel.getOfferDesc();
        String price=itemSubModel.getOfferPrice();
        if (title != null && !title.isEmpty() && !title.equals("null"))
        {
            viewHolder.offerTitle.setText(title);
            viewHolder.edtCircleCode.setText(title);

        }
        if (description != null && !description.isEmpty() && !description.equals("null"))
        {
            viewHolder.offerDescription.setText(description);
        }
        if (price != null && !price.isEmpty() && !price.equals("null"))
        {
            viewHolder.offerPrice.setText(price+" QAR");
        }
        viewHolder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mContext.getString(R.string.CodeToast),Toast.LENGTH_SHORT).show();
                Hawk.put("promoCode",itemSubModel.getOfferTitle());
                Hawk.put("type",itemSubModel.getCouponType());
                Hawk.put("coupon_code",itemSubModel.getCouponCode());
            }
        });





    }

    @Override
    public int getItemCount() {
        return offerModel.size();
    }

    public void removeItem(int position) {
        offerModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, offerModel.size());
    }
}
