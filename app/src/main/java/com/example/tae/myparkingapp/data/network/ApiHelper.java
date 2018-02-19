package com.example.tae.myparkingapp.data.network;

import com.example.tae.myparkingapp.data.network.model.Location;
import com.example.tae.myparkingapp.data.network.model.Parking;
import com.example.tae.myparkingapp.data.network.service.ApiList;
import com.example.tae.myparkingapp.data.network.service.IRequestInterface;
import com.example.tae.myparkingapp.data.network.service.ServiceConnection;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by TAE on 18-Feb-18.
 */

public class ApiHelper implements IApiHelper {

    private IRequestInterface iRequestInterface;

    public ApiHelper() {
        this.iRequestInterface = ServiceConnection.getConnection();
    }

    @Override
    public Observable<Location> getLocations(int id)
    {
        return iRequestInterface.getLocations(id);
    }
    @Override
    public Observable<List<Parking>> getMarkers()
    {
        return iRequestInterface.getMarkers();
    }

}
