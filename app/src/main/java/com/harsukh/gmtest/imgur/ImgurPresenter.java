package com.harsukh.gmtest.imgur;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;

import javax.inject.Inject;
import okhttp3.Response;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by harsukh on 4/4/17.
 */

public class ImgurPresenter implements ImgurContract.ImgurPresenterContract {

    private ImgurContract.ImgurView imgurView;
    private Observable<Response> imgurObservable;
    private Subscription subscription;
    private static final String TAG = ImgurPresenter.class.getSimpleName();

    @Inject
    public ImgurPresenter(ImgurContract.ImgurView imgurView, Observable<Response> observable) {
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
        subscription = imgurObservable.subscribe(new Action1<Response>() {
            @Override
            public void call(Response response) {
                imgurView.displayImages(BitmapFactory.decodeStream(response.body().byteStream()));
            }
        });
    }
}
