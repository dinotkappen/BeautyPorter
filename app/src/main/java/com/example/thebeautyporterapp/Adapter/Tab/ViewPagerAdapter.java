package com.example.thebeautyporterapp.Adapter.Tab;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import com.example.thebeautyporterapp.R;


import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    LayoutInflater inflater;
    int position = 3;
//    List<Banner_> banner_list;
    ArrayList banner_list;
    FragmentActivity activity;
    ProgressBar progressloading;
    ArrayList images = new ArrayList();


    public ViewPagerAdapter(FragmentActivity activity, ArrayList banner_list) {
        this.activity = activity;
        this.banner_list = banner_list;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return banner_list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imgBanner;
        final FrameLayout linearImg;


        View imageLayout = inflater.inflate(R.layout.tab_pager_item, container, false);

        assert imageLayout != null;

        imgBanner = imageLayout.findViewById(R.id.img_banner);
        Glide.with(activity)
                .load(banner_list.get(position))
                .into(imgBanner);
//        imgBanner.setAdjustViewBounds(true);

//        progressloading.setVisibility(View.VISIBLE);



//        Glide.with(activity)
//                .load(banner_list.get(position))
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        progressloading.setVisibility(View.GONE);
//                        return false;
//                    }
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        progressloading.setVisibility(View.GONE);
//                        return false;
//                    }
//                })
//                .into(imgBanner);

        container.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        container.removeView((View) object);
    }
}
