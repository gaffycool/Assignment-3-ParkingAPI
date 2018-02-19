package com.example.tae.myparkingapp.locations;


import com.example.tae.myparkingapp.data.network.model.Location;
import com.example.tae.myparkingapp.ui.base.MvpView;

/**
 * Created by TAE on 18-Feb-18.
 */

public interface ILocationMvpView extends MvpView {

    void onFetchDataProgress();
    void onFetchDataSuccess(Location location);
    void onFetchDataError(String error);

}
