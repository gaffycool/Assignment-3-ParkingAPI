package com.example.tae.myparkingapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tae.myparkingapp.data.network.model.Parking;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParkingFragment extends DialogFragment {

    Parking parking;
    private static Bundle bundle = new Bundle();

    public ParkingFragment() {
        // Required empty public constructor
    }

    public static ParkingFragment newInstance(Parking parking) {

        ParkingFragment fragment = new ParkingFragment();
      //  bundle.putParcelable("parking", parking);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parking = getArguments().getParcelable("parkingLocation");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_parking, container, false);
       // ButterKnife.bind(this, v);

        return v;
    }

}
