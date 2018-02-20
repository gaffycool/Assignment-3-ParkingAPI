package com.example.tae.myparkingapp;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tae.myparkingapp.data.network.model.Parking;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by TAE Consultant on 09/10/2017.
 */
public class CustomInfoWindowAdapter extends AppCompatActivity implements GoogleMap.InfoWindowAdapter {

    private final View customContentsView;

    private final List<Parking> parking;

    public CustomInfoWindowAdapter(List<Parking> parking) {
        customContentsView = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.info_window,null);
        this.parking = parking;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView tvTitle = (TextView)customContentsView.findViewById(R.id.mName);
        TextView tvReserved = (TextView)customContentsView.findViewById(R.id.mAvailability);
        TextView tvMin = (TextView)customContentsView.findViewById(R.id.mMinLength);
        TextView tvMax = (TextView)customContentsView.findViewById(R.id.mMaxResLength);
        TextView tvCost = (TextView)customContentsView.findViewById(R.id.mCostPerMin);

        tvTitle.setText(parking.get(Integer.parseInt(marker.getTitle())).getName() + " (" + parking.get(Integer.parseInt(marker.getTitle())).getId() + ")");
        if(parking.get(Integer.parseInt(marker.getTitle())).getIsReserved()) {
            tvReserved.setText("Reserved Until " + parking.get(Integer.parseInt(marker.getTitle())).getReservedUntil());
        } else {
            tvReserved.setText("Not Reserved, Tap to Reserve");
        }
        tvMin.setText(parking.get(Integer.parseInt(marker.getTitle())).getMinReserveTimeMins() + " minutes");
        tvMax.setText(parking.get(Integer.parseInt(marker.getTitle())).getMaxReserveTimeMins() + " minutes");
        tvCost.setText("$" + parking.get(Integer.parseInt(marker.getTitle())).getCostPerMinute() + " per minute");

        return customContentsView;
    }
}
