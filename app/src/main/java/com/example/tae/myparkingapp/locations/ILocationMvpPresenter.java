package com.example.tae.myparkingapp.locations;

import com.example.tae.myparkingapp.ui.base.MvpPresenter;

/**
 * Created by TAE on 18-Feb-18.
 */

public interface ILocationMvpPresenter<V extends ILocationMvpView> extends MvpPresenter<V> {

void loadLocation(int id);
}
