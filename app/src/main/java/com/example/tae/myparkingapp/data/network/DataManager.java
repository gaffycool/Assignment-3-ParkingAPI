package com.example.tae.myparkingapp.data.network;

import com.example.tae.myparkingapp.data.network.model.Location;
import com.example.tae.myparkingapp.data.network.model.Parking;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by TAE on 18-Feb-18.
 */

public class DataManager implements IDataManager {


    private IApiHelper iApiHelper;

    public DataManager() {
        this.iApiHelper = new ApiHelper();
    }


    @Override
    public Observable<List<Parking>> getMarkers() {
        return iApiHelper.getMarkers();
    }

    @Override
    public Observable<Location> getLocations(int id) {
        return iApiHelper.getLocations(id);
    }

    @Override
    public Observable<Location> postReservation(int id) {
        return iApiHelper.postReservation(id);
    }
}

