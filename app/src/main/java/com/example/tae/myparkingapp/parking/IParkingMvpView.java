package com.example.tae.myparkingapp.parking;

import com.example.tae.myparkingapp.data.network.model.Location;
import com.example.tae.myparkingapp.data.network.model.Parking;
import com.example.tae.myparkingapp.ui.base.MvpView;

import java.util.List;

/**
 * Created by TAE on 18-Feb-18.
 */

public interface IParkingMvpView extends MvpView {

    //for the parking locations
    void onFetchDataError(String error);

    void onFetchDataCompleted(List<Parking> parking);

    //for the post reservation
    void onFetchDataCompleted(Parking parking);

}
