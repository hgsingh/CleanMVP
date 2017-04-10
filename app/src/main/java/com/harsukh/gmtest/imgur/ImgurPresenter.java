package com.harsukh.gmtest.imgur;

import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by harsukh on 4/4/17.
 */

public class ImgurPresenter implements ImgurContract.ImgurPresenterContract {

    private ImgurContract.ImgurView imgurView;
    private Observable<Call> imgurObservable;
    private Subscription subscription;
    private static final String TAG = ImgurPresenter.class.getSimpleName();

    @Inject
    public ImgurPresenter(ImgurContract.ImgurView imgurView, Observable<Call> observable) {
        this.imgurView = imgurView;
        imgurObservable = observable;
    }

    @Override
    public void subscribe() {
        String imgurUrl = imgurView.getIntentData();
        if (imgurUrl != null) {
            // make subscription object here
            loadImages();
        }
    }

    @Override
    public void unsubscribe() {
        subscription.unsubscribe();
    }

    @Override
    public void loadImages() {
        subscription = imgurObservable.subscribe(new Action1<Call>() {
            @Override
            public void call(Call call) {
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "onFailure: failure when calling", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        imgurView.displayImages(response.body().byteStream());
                    }
                });
            }
        });
    }
}
