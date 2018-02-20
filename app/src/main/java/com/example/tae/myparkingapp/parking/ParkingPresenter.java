package com.example.tae.myparkingapp.parking;

import com.example.tae.myparkingapp.data.network.DataManager;
import com.example.tae.myparkingapp.data.network.model.Location;
import com.example.tae.myparkingapp.data.network.model.Parking;
import com.example.tae.myparkingapp.ui.base.BasePresenter;
import com.example.tae.myparkingapp.ui.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by TAE on 18-Feb-18.
 */

public class ParkingPresenter <V extends IParkingMvpView>
        extends BasePresenter<V>
        implements IParkingMvpPresenter<V> {


    public ParkingPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadParking() {
       getCompositeDisposable().add(
                getDataManager().getMarkers()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<Parking>>() {
                                       @Override
                                       public void accept(List<Parking> parking) throws Exception {
                                           getMvpView().onFetchDataSuccess(parking);
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        getMvpView().onFetchDataError(throwable.getMessage());
                                    }
                                })
        );
    }

    @Override
    public void onViewPrepared(int id) {

        getCompositeDisposable()
                .add(getDataManager().postReservation(id)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<Location>() {
                            @Override
                            public void accept(Location location) throws Exception {

                                getMvpView().onFetchDataCompleted(location);

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }));
    }
}