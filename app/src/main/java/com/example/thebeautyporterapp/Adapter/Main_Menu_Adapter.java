package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Fragment.SubMenuFragment;
import com.example.thebeautyporterapp.Model.Service_List_Model;
import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import java.util.List;

public class Main_Menu_Adapter extends RecyclerView.Adapter<Main_Menu_Adapter.MyViewHolder> {

    private Context mContext;
    private List<Service_List_Model> service_List_Model;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceName;
        public ImageView serviceImg;
        public LinearLayout linearLayoutCard;
        LinearLayout linear_single_items;
        CardView cardViewServicemain;



        public MyViewHolder(View view) {
            super(view);
            serviceName = (TextView) view.findViewById(R.id.menu_Name);
            serviceImg = (ImageView) view.findViewById(R.id.menu_Icon);
            linear_single_items= view.findViewById(R.id.linear_single_items);




        }
    }

    public Main_Menu_Adapter(Context mContext, List<Service_List_Model> service_List_Model) {
        this.mContext = mContext;
        this.service_List_Model = service_List_Model;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_menu_main, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Service_List_Model itemMainModel = service_List_Model.get(position);



        String imgUrl=itemMainModel.getService_icon();
        String name=itemMainModel.getService_name();
        if (name != null && !name.isEmpty() && !name.equals("null")){
            holder.serviceName.setText(name);
        }
        if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null"))
        {
            Glide.with(mContext).load(imgUrl).into(holder.serviceImg);
        }
        else
        {
            Glide.with(mContext).load("http://market.whyte.company/storage/products/no-image.jpg").into(holder.serviceImg);
        }
        holder.linear_single_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hawk.init(mContext)
                        .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                        .setStorage(HawkBuilder.newSqliteStorage(mContext))
                        .setLogLevel(LogLevel.FULL)
                        .build();

                Fragment fragment = new SubMenuFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                Hawk.put("mainServiceName",itemMainModel.getService_name());
                Hawk.put("mainServiceID",itemMainModel.getServie_id());
                Log.v("serID",itemMainModel.getServie_id());
//                Bundle bundle = new Bundle();
//                bundle.putString("businessTitle", itemMainModel.getServiceName());
//                bundle.putString("selectedMainServiceID", itemMainModel.getServiceID());
//                fragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
//        holder.serviceImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Hawk.init(mContext)
//                        .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
//                        .setStorage(HawkBuilder.newSqliteStorage(mContext))
//                        .setLogLevel(LogLevel.FULL)
//                        .build();
//
//                Fragment fragment = new SubMenuFragment();
//                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                Hawk.put("mainServiceName",itemMainModel.getServiceName());
//                Hawk.put("mainServiceID",itemMainModel.getServiceID());
//                Log.v("serID",itemMainModel.getServiceID());
////                Bundle bundle = new Bundle();
////                bundle.putString("businessTitle", itemMainModel.getServiceName());
////                bundle.putString("selectedMainServiceID", itemMainModel.getServiceID());
////                fragment.setArguments(bundle);
//                fragmentTransaction.commit();
//
//
//
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return service_List_Model.size();
    }
}
