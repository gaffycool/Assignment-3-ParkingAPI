package com.example.tae.myparkingapp.locations;

import com.example.tae.myparkingapp.data.network.DataManager;
import com.example.tae.myparkingapp.data.network.model.Location;

import com.example.tae.myparkingapp.ui.base.BasePresenter;
import com.example.tae.myparkingapp.ui.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by TAE on 18-Feb-18.
 */

public class LocationPresenter<V extends ILocationMvpView>
extends BasePresenter<V>
implements ILocationMvpPresenter<V> {


    public LocationPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadLocation(int id) {

        getCompositeDisposable().add(
                getDataManager().getLocations(id)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<Location>() {
                            @Override
                            public void accept(Location location) throws Exception {
                                getMvpView().onFetchDataSuccess(location);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpView().onFetchDataError(throwable.getMessage());
                            }
                        })
        );
    }
}
