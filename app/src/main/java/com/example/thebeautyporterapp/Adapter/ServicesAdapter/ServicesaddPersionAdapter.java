package com.example.thebeautyporterapp.Adapter.ServicesAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thebeautyporterapp.R;

import java.util.ArrayList;

import static com.example.thebeautyporterapp.Fragment.TabServicesFragment.showServiceForGuest;

public class ServicesaddPersionAdapter extends RecyclerView.Adapter<ServicesaddPersionAdapter.MyViewHolder> {

    int row_index;
    private Context mContext;
    private ArrayList nameList = new ArrayList();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title, txt_close;


        public MyViewHolder(View view) {
            super(view);

            Title =  view.findViewById(R.id.txt_name);
            txt_close =  view.findViewById(R.id.txt_close);



        }
    }

    public ServicesaddPersionAdapter(Context mContext, ArrayList appoinmentModel) {
        this.mContext = mContext;
        this.nameList = appoinmentModel;
    }

    @Override
    public ServicesaddPersionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nam_list_item, parent, false);

        return new ServicesaddPersionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServicesaddPersionAdapter.MyViewHolder holder, int position) {
//        BusinessService itemSubModel = appoinmentModel.get(position);
        final ServicesaddPersionAdapter.MyViewHolder viewHolder = (ServicesaddPersionAdapter.MyViewHolder) holder;

        holder.Title.setText(nameList.get(position).toString());
        String name= holder.Title.getText().toString();
        holder.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showServiceForGuest(name);

            }
        });

    }

    @Override
    public int getItemCount() {

        if (nameList != null)
            return nameList.size();
        else
            return 0;
    }

    public void removeItem(int position) {
        nameList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, nameList.size());
    }
}