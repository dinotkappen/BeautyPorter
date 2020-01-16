package com.example.thebeautyporterapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thebeautyporterapp.Fragment.AboutUsFragment;
import com.example.thebeautyporterapp.Fragment.AppoinmentFragment;
import com.example.thebeautyporterapp.Fragment.DateTimeFragment;
import com.example.thebeautyporterapp.Fragment.FeedBackFragment;
import com.example.thebeautyporterapp.Fragment.HomeFragment;
import com.example.thebeautyporterapp.Fragment.NotificationFragment;
import com.example.thebeautyporterapp.Fragment.OffersFragment;
import com.example.thebeautyporterapp.Fragment.ProfileFragment;
import com.example.thebeautyporterapp.Fragment.RatingFragment;
import com.example.thebeautyporterapp.Fragment.SearchFragment;
import com.example.thebeautyporterapp.Fragment.ServiceDetailFragment;
import com.example.thebeautyporterapp.Fragment.SettingsFragment;
import com.example.thebeautyporterapp.Fragment.SubMenuFragment;
import com.example.thebeautyporterapp.Fragment.VendorFragment;
import com.example.thebeautyporterapp.Fragment.WishListFragment;
import com.example.thebeautyporterapp.Other.NetworkChangeReceiver;
import com.example.thebeautyporterapp.R;
import com.example.thebeautyporterapp.Other.Utilities;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import static com.example.thebeautyporterapp.Activity.NoInternetActivity.closeActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    View view;
    LinearLayout linearVendor, linearSettings, linearAboutUS, linearFeedBack, linearOffer, nav_head_profile, linearLogOut;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_NAME = "PrefsFile";
    SharedPreferences preferences;
    String str_user_name, str_user_pic;
    public static ImageView profile_image_header;
    String refreshedToken;
    Utilities utilities;
    InputMethodManager imm;
    HomeFragment homeFragment;
    DrawerLayout drawer;
    static FragmentManager fm;
    static MainActivity mainActivity;
    static Boolean flagNoInternetActivity = false;
    int logedIn;
    public static FragmentTransaction transaction;
    public static ImageView imgMenu, backArrow, imgFilter, imgSearch, imgAdd;
    public static TextView txt_actionbar_Title;
    public static FrameLayout linearActionBar;
    public static String photoResponse;
    private NetworkChangeReceiver receiver;

    // private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (utilities.isOnline(this)) {
            setContentView(R.layout.activity_main);

            Hawk.init(this)
                    .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                    .setStorage(HawkBuilder.newSqliteStorage(this))
                    .setLogLevel(LogLevel.FULL)
                    .build();
            mainActivity = MainActivity.this;

            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            receiver = new NetworkChangeReceiver();
            registerReceiver(receiver, filter);


            preferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);

            logedIn = preferences.getInt("logedIn", 0);
            logedIn = 1;

            str_user_name = preferences.getString("customer_name", "");
            str_user_pic = preferences.getString("profile_pic", "");
            //str_user_pic = "http://serviceapp.whyteapps.com/storage/app/" + str_user_pic;
            Log.v("prof_pic_main", str_user_pic);
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            view = getWindow().getDecorView();
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


            NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(this);


            //getting bottom navigation view and attaching the listener
            final BottomNavigationView navigation = findViewById(R.id.navigation_bottom);
            navigation.setOnNavigationItemSelectedListener(this);

            Intent mIntent = getIntent();
            int intValue = mIntent.getIntExtra("from_maps", 0);
            String notificationId = preferences.getString("notificationId", "0");
            String myReceiptFragment = preferences.getString("myReceiptFragment", "0");
            String myReceiptFragmentID = preferences.getString("receiptId", "0");

            loadFragment(new HomeFragment());

            imgMenu = (ImageView) findViewById(R.id.imgMenu);
            backArrow = (ImageView) findViewById(R.id.backArrow);
            txt_actionbar_Title = (TextView) findViewById(R.id.txt_actionbar_Title);
            backArrow.setVisibility(View.INVISIBLE);
            imgFilter = (ImageView) findViewById(R.id.imgFilter);
            imgFilter.setVisibility(View.INVISIBLE);
            imgSearch = (ImageView) findViewById(R.id.imgSearch);
            imgAdd = (ImageView) findViewById(R.id.imgAdd);
            imgAdd.setVisibility(View.GONE);
            linearActionBar = (FrameLayout) findViewById(R.id.linearActionBar);
            side_hide(false);
            nav_head_profile = (LinearLayout) navigationView.findViewById(R.id.nav_head_profile);
            linearVendor = (LinearLayout) navigationView.findViewById(R.id.linearVendor);
            linearSettings = (LinearLayout) navigationView.findViewById(R.id.linearSettings);
            linearLogOut = (LinearLayout) navigationView.findViewById(R.id.linearLogOut);
            linearFeedBack = (LinearLayout) navigationView.findViewById(R.id.linearFeedBack);
            linearOffer = (LinearLayout) navigationView.findViewById(R.id.linearOffer);
            linearAboutUS = (LinearLayout) navigationView.findViewById(R.id.linearAboutUS);


            profile_image_header = (ImageView) navigationView.findViewById(R.id.profile_image_header);


            imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    toggle();

                }
            });
            backArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            photoResponse = Hawk.get("photo", photoResponse);
            if (photoResponse != null && !photoResponse.isEmpty() && !photoResponse.equals("")) {

                updateNavHeaderView(photoResponse);
            }


//            Glide.with(this)
//                    .load(str_user_pic)
//                    .placeholder(R.drawable.profile_sample)
//                    .into(profile_image_header);


            Log.v("prof_pic_drawer", str_user_pic);

//                nav_head_profile.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (drawer.isDrawerOpen(GravityCompat.START)) {
//                            drawer.closeDrawer(GravityCompat.START);
//                        }
//                        Fragment fragment = new ProfileFragment();
//                        fragmentManager = getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment_container, fragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                    }
//                });


        }


    }

    public static void updateNavHeaderView(String photoResponse) {
        try {

            if (photoResponse != null && !photoResponse.isEmpty() && !photoResponse.equals("")) {
                Glide.with(profile_image_header.getContext()).load(photoResponse).into(profile_image_header);

            }

        } catch (Exception ex) {
            Log.v("updateNavHeaderView", ex.getMessage().toString());
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment_back = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragment_back instanceof SubMenuFragment) {
                backMain();
            } else if (fragment_back instanceof OffersFragment) {
                backMain();
            } else if (fragment_back instanceof FeedBackFragment) {
                backMain();
            } else if (fragment_back instanceof AboutUsFragment) {
                backMain();
            } else if (fragment_back instanceof SettingsFragment) {
                backMain();
            } else if (fragment_back instanceof VendorFragment) {
                backMain();
            } else if (fragment_back instanceof WishListFragment) {
                backMain();
            } else if (fragment_back instanceof SearchFragment) {
                backMain();
            } else if (fragment_back instanceof NotificationFragment) {
                backMain();
            } else if (fragment_back instanceof AppoinmentFragment) {
                backMain();
            } else if (fragment_back instanceof ProfileFragment) {
                backMain();
            } else if (fragment_back instanceof DateTimeFragment) {

                Fragment fragment1 = new ServiceDetailFragment();
                backViewPager(fragment1);
            } else if (fragment_back instanceof ServiceDetailFragment) {
                Fragment fragment1 = new SubMenuFragment();
                backViewPager(fragment1);
            } else if (fragment_back instanceof RatingFragment) {
                Fragment fragment1 = new SubMenuFragment();
                backViewPager(fragment1);
            } else if (fragment_back instanceof HomeFragment) {

                moveTaskToBack(true);
            } else
                super.onBackPressed();
        }
    }

    public void backMain() {
        Fragment fragment1 = new HomeFragment();
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.fragment_container, fragment1).
                addToBackStack(null).
                commit();
    }

    public void backViewPager(Fragment fragment1) {
        // fragment1 = new ServiceDetailFragment();
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.fragment_container, fragment1).
                addToBackStack(null).
                commit();
    }

    public boolean onClick(View view) {

        Fragment fragment = null;
        try {
            switch (view.getId()) {
                case R.id.nav_head_profile: {
                    if (logedIn == 1) {
                        fragment = new ProfileFragment();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }


                }
                break;
                case R.id.linearOffer: {
                    if (logedIn == 1) {
                        fragment = new OffersFragment();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }


                }
                break;

                case R.id.linearFeedBack: {
                    if (logedIn == 1) {
                        fragment = new FeedBackFragment();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }
                    //your code.

                }
                break;

                case R.id.linearAboutUS: {
                    if (logedIn == 1) {
                        fragment = new AboutUsFragment();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }
                    //your code.

                }
                break;
                case R.id.linearSettings: {

                    fragment = new SettingsFragment();

                    //your code.

                }
                break;

                case R.id.linearVendor: {

                    fragment = new VendorFragment();

                    //your code.

                }
                break;

                case R.id.linearLogOut: {

                    Intent intent = new Intent(MainActivity.this, LogInMethodActivity.class);
                    Hawk.put("logedIn", 0);
                    startActivity(intent);
                    finish();
                }
                break;


            }
        } catch (Exception ex) {
            String msg = ex.getMessage().toString();
            String h = msg;
        }

        return loadFragment(fragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        //   displaySelectedScreen(item.getItemId());
        try {
            switch (item.getItemId()) {

                //*****Bottom menu**

                case R.id.navigation_wishList:
                    fragment = new WishListFragment();


                    break;

                case R.id.navigation_search:
                    fragment = new SearchFragment();
                    break;

                case R.id.navigation_home:
                    if (logedIn == 1) {
                        fragment = new HomeFragment();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }


                    break;

                case R.id.navigation_notifications:
                    if (logedIn == 1) {
                        fragment = new NotificationFragment();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }

                    break;
                case R.id.navigation_appoinment:
                    if (logedIn == 1) {
                        fragment = new AppoinmentFragment();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }

                    break;


            }
        } catch (Exception ex) {
            String msg = ex.getMessage().toString();
            String h = msg;
        }

        return loadFragment(fragment);
    }

    public static boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            fm = mainActivity.getSupportFragmentManager();
            transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

            mainActivity.drawer = mainActivity.findViewById(R.id.drawer_layout);
            mainActivity.drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    public static void side_hide(boolean status) {

        if (status) {
            imgMenu.setVisibility(View.GONE);
            backArrow.setVisibility(View.VISIBLE);
        } else {
            imgMenu.setVisibility(View.VISIBLE);
            backArrow.setVisibility(View.GONE);
        }

    }

    private void toggle() {
        if (drawer.isDrawerVisible(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    public static void no_internet(boolean status, Context context) {

        if (status) {
            if (flagNoInternetActivity == true) {
                flagNoInternetActivity = false;
                closeActivity();

            }

        } else {
            if (flagNoInternetActivity != true) {
                Intent intent = new Intent(context, NoInternetActivity.class);
                context.startActivity(intent);
                flagNoInternetActivity = true;
            }
        }


    }
}
