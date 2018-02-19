package com.example.tae.myparkingapp.parking;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.tae.myparkingapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by TAE on 18-Feb-18.
 */

public class MapInfoAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context = null;

    public MapInfoAdapter(Context context) {
        this.context = context;
    }

    // Hack to prevent info window from displaying: use a 0dp/0dp frame
    @Override
    public View getInfoWindow(Marker marker) {
        View v = ((Activity) context).getLayoutInflater().inflate(R.layout.no_window, null);
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}