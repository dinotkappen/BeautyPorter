package com.example.thebeautyporterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Model.WishListModel;
import com.example.thebeautyporterapp.R;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    private Context mContext;
    private List<WishListModel> wishListModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView wishListTitle, wishListAdrz;
        ImageView imgWishList;

        public MyViewHolder(View view) {
            super(view);

            wishListTitle = (TextView) view.findViewById(R.id.wishListTitle);
            wishListAdrz = (TextView) view.findViewById(R.id.wishListAdrz);
            imgWishList = (ImageView) view.findViewById(R.id.imgWishList);


        }
    }

    public WishListAdapter(Context mContext, List<WishListModel> wishListModel) {
        this.mContext = mContext;
        this.wishListModel = wishListModel;
    }

    @Override
    public WishListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_adapter_layout, parent, false);


        return new WishListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WishListAdapter.MyViewHolder holder, int position) {
        WishListModel itemSubModel = wishListModel.get(position);
        final WishListAdapter.MyViewHolder viewHolder = (WishListAdapter.MyViewHolder) holder;
        String title = itemSubModel.getWishListTitle();
        String adrz = itemSubModel.getWishListAdrz();
        String imgUrl = itemSubModel.getWishListImgUrl();
        Log.v("imgUrl",imgUrl);


        String rating = itemSubModel.getWishListRating();
        if (title != null && !title.isEmpty() && !title.equals("null")) {
            viewHolder.wishListTitle.setText(title);
        }
        if (adrz != null && !adrz.isEmpty() && !adrz.equals("null")) {
            viewHolder.wishListAdrz.setText(adrz);
        }
        if (imgUrl != null && !imgUrl.isEmpty() && !imgUrl.equals("null")) {
            Glide.with(mContext).load(imgUrl).into(holder.imgWishList);
        }

//        if (rating != null && !rating.isEmpty() && !rating.equals("null")) {
//            holder.simpleRatingBar.setRating(Float.parseFloat(rating));
//        }
        if (rating != null && !rating.isEmpty() && !rating.equals("null")) {

            char first = rating.charAt(0);
            String exactRating=""+first;



        }
//        holder.imgWishList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Fragment fragment = new SingleItemFragment();
////                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                fragmentTransaction.replace(R.id.fragment_container, fragment);
////                fragmentTransaction.addToBackStack(null);
////                Bundle bundle = new Bundle();
////                bundle.putString("title", "Click");
////                fragment.setArguments(bundle);
////                fragmentTransaction.commit();
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return wishListModel.size();
    }

    public void removeItem(int position) {
        wishListModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, wishListModel.size());
    }
}
