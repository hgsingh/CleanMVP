package com.harsukh.gmtest;


import android.util.Log;

import com.harsukh.gmtest.reddithits.Contract.View;
import com.harsukh.gmtest.retrofit.RestClient;
import com.harsukh.gmtest.retrofit.Titles;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.harsukh.gmtest.retrofit.RestClient.BASE_URL;

/**
 * Created by harsukh on 3/29/17.
 */
@Module
public class MainModule {

    private View view;

    public MainModule(View view) {
        this.view = view;
    }

    @Provides
    public View provideView() {
        return view;
    }

    @Provides
    public Call<Titles> provideRestClient() {
        return RestClient.createService(BASE_URL, RestClient.RedditServiceInterface.class).getTitles();
    }

    @Provides
    @Singleton
    public Observable<Titles> provideSubscription(final Call<Titles> restCall) {
        return Observable.create(new Observable.OnSubscribe<Titles>() {
            @Override
            public void call(Subscriber<? super Titles> subscriber) {
                Response<Titles> titlesResponse = null;
                try {
                    titlesResponse = restCall.execute();
                } catch (IOException e) {
                    Log.e("Subscription","subscription failure",e);
                }
                subscriber.onNext(titlesResponse.body());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
