package com.example.tae.myparkingapp.parking;

import com.example.tae.myparkingapp.ui.base.MvpPresenter;

/**
 * Created by TAE on 18-Feb-18.
 */

public interface IParkingMvpPresenter <V extends IParkingMvpView> extends MvpPresenter<V> {
    void loadParking();

    void onViewPrepared(int id);
}
