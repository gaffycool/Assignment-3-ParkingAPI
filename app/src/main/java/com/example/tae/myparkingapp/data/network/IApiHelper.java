package com.example.tae.myparkingapp.data.network;

import com.example.tae.myparkingapp.data.network.model.Location;
import com.example.tae.myparkingapp.data.network.model.Parking;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by TAE on 18-Feb-18.
 */

public interface IApiHelper {

   // Observable<List<Parking>> getMarkers();

    Observable<List<Parking>> getLocations();

    Observable<Parking> postReservation(int id);
}
