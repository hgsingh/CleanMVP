package com.harsukh.gmtest;


import android.util.Log;

import com.harsukh.gmtest.imgur.ImgurContract;
import com.harsukh.gmtest.reddithits.Contract.View;
import com.harsukh.gmtest.retrofit.RestClient;
import com.harsukh.gmtest.retrofit.Titles;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    private ImgurContract.ImgurView imgurView;
    private String imgur_url;

    public MainModule(View view) {
        this.view = view;
    }

    public MainModule(ImgurContract.ImgurView imgurView, String url) {
        this.imgurView = imgurView;
        imgur_url = url;
    }


    @Provides
    public View provideView() {
        return view;
    }

    @Provides
    public ImgurContract.ImgurView provideImgurView() {
        return imgurView;
    }

    @Provides
    @Singleton
    public Observable<okhttp3.Call> provideImgurObservable() {
        return Observable.just(new OkHttpClient().newCall(new Request.Builder()
                .url(imgur_url)
                .build()));
    }


    @Provides
    @Singleton
    public Observable<Titles> provideSubscription() {
        return RestClient.createService(BASE_URL, RestClient.RedditServiceInterface.class).getTitles()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
