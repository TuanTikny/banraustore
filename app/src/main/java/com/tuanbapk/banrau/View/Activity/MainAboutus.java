package com.tuanbapk.banrau.View.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tuanbapk.banrau.Map.DirectionFinder;
import com.tuanbapk.banrau.Map.DirectionFinderListener;
import com.tuanbapk.banrau.Map.Route;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.MyAnimation.CustomToast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by buituan on 2017-12-12.
 */
@SuppressWarnings("deprecation")
public class MainAboutus extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback,  GoogleMap.OnPolylineClickListener, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMyLocationButtonClickListener,DirectionFinderListener {

    private static final String TAG = MainAboutus.class.getSimpleName();

    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;

    private AppCompatTextView normalMap, hybridMap, satelliteMap, terrainMap, noneMap;
    private AppCompatTextView locationLabel;

    /*  Required for Google Maps  */
    private GoogleMap map;
    private LocationRequest mLocationRequest;
    private Marker mMarker;
    private Location mLocation;
    CustomToast customToast;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_abouts);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//        AdView adView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        adView.loadAd(adRequest);

        anhxa();

        customToast = new CustomToast(this);
        // set sự kiện onclick
        normalMap.setOnClickListener(this);
        hybridMap.setOnClickListener(this);
        satelliteMap.setOnClickListener(this);
        terrainMap.setOnClickListener(this);
        noneMap.setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //bật zoom controls
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //bật gestures
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        // hiển thị thanh điều hướng
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        // set kiểu hiển thị của map
        //Enable current Location buttons
        enableCurrentLocationButton();

        // set kiện MarkerClick
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMyLocationButtonClickListener(this);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // chưa cấp quyền thì xin quyền
        if (googleMap != null) {
            if (ContextCompat.checkSelfPermission(MainAboutus.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else {
                //cho phép hiển thị vị trí của bạn
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.setMyLocationEnabled(true);
            }
        }
        double Latitude=0;
        double Longitude = 0;
        // Xử lí lấy vị trí hiện tại của người dùng
        try {
            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(criteria, false));

            Latitude= location==null?0:location.getLatitude();

            Longitude = location==null?0:location.getLongitude();
            //add google Marker on map

            // customToast.Customtoastfail("Không Lấy được vị trí" + Latitude);
            if (Latitude!=0.0) {
          //      googleMap.addMarker(new MarkerOptions().position(new LatLng(Latitude, Longitude)).title("Vị Trí Của Bạn").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));// Point A=Customer
            }

          //  googleMap.addMarker(new MarkerOptions().position(new LatLng(12.681423, 108.071412)).title("Thực Phẩm Xanh Store").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            // Set sự kiện khi click vô đường chỉ dẫn
            googleMap.setOnPolylineClickListener(this);
        }catch (Exception e){
            // Debug nếu lỗi ==>
           // customToast.Customtoastfail("Không Lấy được vị trí=="+e.getMessage());
        }

        // Zoom về vị trí hiện tại của người dùng
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.681423, 108.071412), 2));


        String latitudeht = String.valueOf(Latitude);
        String longitudeht = String.valueOf(Longitude);

        // vẽ đường chỉ dẫn
        try {
            new DirectionFinder( this,latitudeht+", "+longitudeht,"12.681423, 108.071412").execute();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void enableCurrentLocationButton() {

        if (map != null) {
            if (ContextCompat.checkSelfPermission(MainAboutus.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else {
                //cho phép hiển thị buttonlocation vị trí của bạn
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.setMyLocationEnabled(true);

            }
        }
    }
    private void updateCurrentMarker(LatLng latLng) {
        customToast.Customtoastsuccess(getResources().getString(R.string.fcode_aboutus_newlocation));
        if (mMarker == null) {
            Log.e(TAG, "updateCurrentMarker marker null");
            if (map != null) {
                Log.e(TAG, "updateCurrentMarker google map not null");

                //Set vị trí Marker và Icon
                mMarker = map.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                //set tiêu đề cho vị trí của bạn
                mMarker.setTitle(getResources().getString(R.string.fcode_aboutus_youarehere));
                //hiệu ứng zoom của mapsCamera
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
            } else
                Log.e(TAG, "updateCurrentMarker google map null");

        } else {
            Log.e(TAG, "updateCurrentMarker marker not null");
            mMarker.setPosition(latLng);
        }
    }

    // ánh xạ các thành phần trong giao diện
    private void anhxa() {
        normalMap = (AppCompatTextView) findViewById(R.id.normal_map);
        hybridMap = (AppCompatTextView) findViewById(R.id.hybrid_map);
        satelliteMap = (AppCompatTextView) findViewById(R.id.satellite_map);
        terrainMap = (AppCompatTextView) findViewById(R.id.terrain_map);
        noneMap = (AppCompatTextView) findViewById(R.id.none_map);
        locationLabel = (AppCompatTextView) findViewById(R.id.current_location_label);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.normal_map:{
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                normalMap.setTextColor(getResources().getColor(R.color.selected));
                setDefaultColorBack(new AppCompatTextView[]{hybridMap, satelliteMap, terrainMap, noneMap});
            }
            break;
            case R.id.hybrid_map:
            {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                hybridMap.setTextColor(getResources().getColor(R.color.selected));
                setDefaultColorBack(new AppCompatTextView[]{normalMap, satelliteMap, terrainMap, noneMap});
            }
            break;
            case R.id.satellite_map:
            {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                satelliteMap.setTextColor(getResources().getColor(R.color.selected));
                setDefaultColorBack(new AppCompatTextView[]{hybridMap, normalMap, terrainMap, noneMap});
            }
            break;
            case R.id.terrain_map:
            {
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                terrainMap.setTextColor(getResources().getColor(R.color.selected));
                setDefaultColorBack(new AppCompatTextView[]{hybridMap, satelliteMap, normalMap, noneMap});
            }
            break;
            case R.id.none_map:
            {
                map.setMapType(GoogleMap.MAP_TYPE_NONE);
                noneMap.setTextColor(getResources().getColor(R.color.selected));
                setDefaultColorBack(new AppCompatTextView[]{hybridMap, satelliteMap, terrainMap, normalMap});
            }
            break;
        }
    }

    private void setDefaultColorBack(AppCompatTextView[] views) {
        for (AppCompatTextView v : views)
            v.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }
    private void initGoogleAPIClient() {
        Log.e(TAG, "Init google api client");
        // kiểm tra mapClient
        mGoogleApiClient = new GoogleApiClient.Builder(MainAboutus.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MainAboutus.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();

    }
    /*  hiện thị poppu xin quyền  */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainAboutus.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MainAboutus.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(MainAboutus.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }
    private void showSettingDialog() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {

                            status.startResolutionForResult(MainAboutus.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e(TAG, "Marker Click: " + marker.getTitle() + "\nLocation : " + marker.getPosition());
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "vị trí thay đổi: " + location.getLatitude() + ", " + location.getLongitude());

        mLocation = location;

        updateCurrentMarker(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //kiểm tra quyền
        if (ContextCompat.checkSelfPermission(MainAboutus.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            requestLocationPermission();
        else {
            //If granted then get last know location and update the location label if last know location is not null
            Log.e(TAG, "onConnected get last know location");
            mLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLocation != null) {
                Log.e(TAG, "onConnected get last know location not null");
                //       locationLabel.setText(String.format(getResources().getString(R.string.lat_lng), mLocation.getLatitude(), mLocation.getLongitude()));
            } else
                Log.e(TAG, "onConnected get last know location null");
        }

        //Start Location Update on successful connection
        startLocationUpdates();
    }
    /*   Update lại vị trí*/
    protected void startLocationUpdates() {
        Log.e(TAG, "startLocationUpdates");
        if (ContextCompat.checkSelfPermission(MainAboutus.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            requestLocationPermission();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        //nếu maps vẫn hoạt động
                        startLocationUpdates();
                        break;
                    case RESULT_CANCELED:
                        //else show toast
                        customToast.Customtoastfail(getResources().getString(R.string.fcode_aboutus_nopemissionlocation));
                        break;
                }
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //kiểm tra map còn connected không nếu không sẽ init google maps lại
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Log.e(TAG, "onResume api client connect");
            startLocationUpdates();
        } else {
            initGoogleAPIClient();
            Log.e(TAG, "onResume init api client");
        }

        //nếu chưa có vị trí hiện tại thì update lại vị trí
        if (mLocation != null) {
            Log.e(TAG, "onResume update Marker");
            updateCurrentMarker(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
        }

        //Check quyền truy cập
        //   checkPermissions();
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, getResources().getString(R.string.fcode_ahoutus_pleasewating),
                getResources().getString(R.string.fcode_loading_location), true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            originMarkers.add(map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .title(getResources().getString(R.string.frag_nav_title))
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.GREEN).
                    width(5);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(map.addPolyline(polylineOptions));
        }
    }
}
