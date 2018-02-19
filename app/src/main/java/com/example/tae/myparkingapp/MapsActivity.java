package com.example.tae.myparkingapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tae.myparkingapp.data.network.DataManager;
import com.example.tae.myparkingapp.data.network.model.Location;
import com.example.tae.myparkingapp.data.network.model.Parking;
import com.example.tae.myparkingapp.data.network.service.IRequestInterface;
import com.example.tae.myparkingapp.data.network.service.ServiceConnection;
import com.example.tae.myparkingapp.locations.ILocationMvpPresenter;
import com.example.tae.myparkingapp.locations.ILocationMvpView;
import com.example.tae.myparkingapp.locations.LocationPresenter;
import com.example.tae.myparkingapp.parking.IParkingMvpPresenter;
import com.example.tae.myparkingapp.parking.IParkingMvpView;
import com.example.tae.myparkingapp.parking.MapInfoAdapter;
import com.example.tae.myparkingapp.parking.ParkingPresenter;
import com.example.tae.myparkingapp.ui.utils.rx.AppSchedulerProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, IParkingMvpView, ILocationMvpView,
        GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private IParkingMvpPresenter<MapsActivity> iParkingMvpPresenter;
    private ILocationMvpPresenter<MapsActivity> iLocationMvpPresenter;
    BitmapDescriptor defaultMarker;
    private String reserved;

    //butterKnife
  //  @BindView(R.id.mName) TextView mName;
   // @BindView(R.id.mId) TextView mId;
  //  @BindView(R.id.mMin) TextView mMin;
  //  @BindView(R.id.mMax) TextView mMax;
  //  @BindView(R.id.mCost) TextView mCost;
    // @BindView(R.id.mAvailability) TextView mAvailability;
//   @BindView(R.id.btnSendRes) Button btnSendRes;
  //  @BindView(R.id.mTimeBook) EditText mTimeBook;
    TextView mName, mId, mMin, mMax, mCost, mAvailability;
    Button btnSendRes;
    EditText mTimeBook;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private IRequestInterface iRequestInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ButterKnife Binding
      //  ButterKnife.bind(R.layout.info_window, this);

        //presenter for the markers
        this.iParkingMvpPresenter = new ParkingPresenter<>(new DataManager(),
                new AppSchedulerProvider(), new CompositeDisposable());
        this.iParkingMvpPresenter.onAttach(this);

        //presenter for the location returned using id passed
        this.iLocationMvpPresenter = new LocationPresenter<>(new DataManager(),
                new AppSchedulerProvider(), new CompositeDisposable());
        this.iLocationMvpPresenter.onAttach(this);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        iParkingMvpPresenter.loadParking();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMarkerClickListener(this);
       // mMap.setOnMyLocationClickListener((GoogleMap.OnMyLocationClickListener) this);
        //mMap.setOnMarkerClickListener(this::onMarkerClick);
        enableMyLocation();
        getAllParking(); //to get all the parking available


    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    public void getAllParking() {

        iRequestInterface = ServiceConnection.getConnection();


        iRequestInterface.getMarkers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Parking>>() {
                    @Override
                    public void accept(List<Parking> parking) throws Exception {

                        for (int i = 0; i < parking.size(); i++) {
                            Parking parkingCheck = parking.get(i);

                            LatLng temp_ = new LatLng(37.773972, -122.431297);

                            LatLng pos = new LatLng(Double.valueOf(parkingCheck.getLat()), Double.valueOf(parkingCheck.getLng()));
                            mMap.addMarker(new MarkerOptions()
                                    .icon(defaultMarker)
                                    .position(pos)
                                    .zIndex(parkingCheck.getId()));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp_, 12), 5000, null);

                            if (parkingCheck.getIsReserved() == false) {
                                defaultMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

                            } else {
                                defaultMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MapsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(cameraUpdate);

        iLocationMvpPresenter.loadLocation((int)marker.getZIndex());
        return false;
    }

    @Override
    public void onFetchDataProgress() {

    }

    //get the location details using the id passed
    @Override
    public void onFetchDataSuccess(Location location) {

         //if map exists
        if (mMap != null) {
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);

                    //ButterKnife Binding
                    //ButterKnife.bind(R.layout.info_window, v);


                    mName = v.findViewById(R.id.mName);
                    mId = v.findViewById(R.id.mId);
                    mMin = v.findViewById(R.id.mMinLength);
                    mMax = v.findViewById(R.id.mMaxResLength);
                    mCost = v.findViewById(R.id.mCostPerMin);
                    mAvailability = v.findViewById(R.id.mAvailability);
                    btnSendRes = v.findViewById(R.id.btnSendRes);
                    mTimeBook = v.findViewById(R.id.mTimeBook);

                    if (location.getIsReserved()==false)
                    {
                        reserved = "Space Available";
                        btnSendRes.setVisibility(View.VISIBLE);
                        mTimeBook.setVisibility(View.VISIBLE);
                    }
                    else if (location.getIsReserved()==true)
                    {
                        reserved = "Space Taken";
                        btnSendRes.setVisibility(View.GONE);
                        mTimeBook.setVisibility(View.GONE);
                    }

                    mName.setText(location.getName());
                    mId.setText(location.getId().toString());
                    mMin.setText(location.getMinReserveTimeMins().toString() + " Minutes");
                    mMax.setText(location.getMaxReserveTimeMins().toString() + "Minutes");
                    mCost.setText("$" + location.getCostPerMinute().toString());
                    mAvailability.setText(reserved);

                    return v;
                }
            });
        }
    }

    //for the markers success
    @Override
    public void onFetchDataSuccess(List<Parking> parking) {

        getAllParking();
    }

    @Override
    public void onFetchDataError(String error) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }
}