package com.example.tae.myparkingapp.data.network.service;

import com.example.tae.myparkingapp.data.network.model.Location;
import com.example.tae.myparkingapp.data.network.model.Parking;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by TAE on 08-Feb-18.
 */

public interface IRequestInterface {

    //get request to get all the markers for parking
    @GET(ApiList.GET_MARKERS)
    Observable<List<Parking>> getMarkers();


    //request to get details from id
    //@GET("/{id}")
    //Observable<Parking> getParkingDetails(@Path("id") int id, @Query("api_key") String apiKey);
    @GET(ApiList.LOCATION_DETAILS)
    Observable<Location> getLocations(@Path("id") int id);

    @POST(ApiList.RESERVE_URL)
    Observable<Location> postReserveParking(@Path("id") int id);


}
