package com.example.thebeautyporterapp.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.thebeautyporterapp.Model.ServicesModel.Content;
import com.example.thebeautyporterapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;


public class TabMapFragment extends Fragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {


    View rootView;
    MapView mMapView;
    private GoogleMap googleMap;
    List<Content> list_id = new ArrayList<>();
    String selectedBusinessID;
    String matchedLat, matchedLon;
    ImageView imgNavigate;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    SupportMapFragment mapFragment;

    public TabMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.tab_map, container, false);

        imgNavigate = (ImageView) rootView.findViewById(R.id.imgNavigate);
        list_id = Hawk.get("searchBusineess", list_id);
        selectedBusinessID = Hawk.get("selectedBusinessID", "");
        Log.v("list_idAbout", "" + list_id.size());
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

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


            }
        });

        imgNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(matchedLat, "" + matchedLat);
                Log.v(matchedLon, "" + matchedLon);
                String latLong = "geo:" + matchedLat + "," + matchedLon;
//                Uri gmmIntentUri = Uri.parse(latLong);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + "" + "" + "" + "&daddr="+matchedLat+","+matchedLon));
                startActivity(intent);
            }
        });
        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void showMap() {
        if (list_id.size() > 0) {

            for (int i = 0; i < list_id.size(); i++) {
                int bussinessID = list_id.get(i).getId();
                if (selectedBusinessID.equals("" + bussinessID)) {
//                    matchedLat="25.30";
//                    matchedLon="51.15";
                    try {
                        matchedLat = list_id.get(i).getLatitude().toString();
                        matchedLon = list_id.get(i).getLongitude().toString();
                        if (matchedLat != null && !matchedLat.isEmpty() && !matchedLat.equals("null")) {
                            if (matchedLon != null && !matchedLon.isEmpty() && !matchedLon.equals("null")) {
                                Double markerLat = Double.parseDouble(matchedLat);
                                Double markLon = Double.parseDouble(matchedLon);

                                LatLng markerLocation = new LatLng(markerLat, markLon);
                                googleMap.addMarker(new MarkerOptions().position(markerLocation).title("Marker Title").snippet(""));
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(markerLocation).zoom(12).build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                Log.v("matchedLat", matchedLat);
                                Log.v("matchedLon", matchedLon);
                                Log.v("Ok", "OKOK");
                            }

                        }
                        Log.v(matchedLat, "" + matchedLat);
                        Log.v(matchedLon, "" + matchedLon);
                    } catch (Exception ex) {
                        String message = ex.toString();
                        Log.v("me", message);
                    }
                }
            }

        }
    }
}