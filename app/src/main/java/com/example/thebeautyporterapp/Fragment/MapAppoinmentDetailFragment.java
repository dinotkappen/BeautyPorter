package com.example.thebeautyporterapp.Fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thebeautyporterapp.Model.ServicesModel.Content;
import com.example.thebeautyporterapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapAppoinmentDetailFragment extends Fragment {
View rootView;
    MapView mMapView;
    private GoogleMap googleMap;
    List<Content> list_id = new ArrayList<>();
    String selectedBusinessID;
    String matchedLat,matchedLon;
    String selected_lat,selectedLong;
    public MapAppoinmentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_map_appoinment_detail, container, false);

        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setText("Location");
        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);


        Bundle bundle = this.getArguments();
        if (bundle != null) {

            selected_lat = bundle.getString("selectedLat", "");
            selectedLong = bundle.getString("selectedLong", "");
        }


        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    showMap();
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                showMap();

                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//                LatLng near = new LatLng(42.67924958203057, Double.parseDouble(longitude));
//                googleMap.addMarker(new MarkerOptions().position(near).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker


//                CameraPosition cameraPositionNew = new CameraPosition.Builder().target(near).zoom(5).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionNew));
            }
        });        return rootView;
    }
    public void showMap() {


        if (selected_lat != null && !selected_lat.isEmpty() && !selected_lat.equals("null")) {
            if (selectedLong != null && !selectedLong.isEmpty() && !selectedLong.equals("null")) {
                Double markerLat = Double.parseDouble(selected_lat);
                Double markLon = Double.parseDouble(selectedLong);

                LatLng markerLocation = new LatLng(markerLat, markLon);
                googleMap.addMarker(new MarkerOptions().position(markerLocation).title("").snippet(""));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(markerLocation).zoom(10).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            else
            {
                LatLng markerLocation = new LatLng(Double.parseDouble("25.30"), Double.parseDouble("51.15"));
                googleMap.addMarker(new MarkerOptions().position(markerLocation).title("").snippet(""));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(markerLocation).zoom(10).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

        }
        else
        {
            LatLng markerLocation = new LatLng(Double.parseDouble("25.30"), Double.parseDouble("51.15"));
            googleMap.addMarker(new MarkerOptions().position(markerLocation).title("").snippet(""));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(markerLocation).zoom(10).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

}