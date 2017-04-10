package com.harsukh.gmtest.imgur;

import android.util.Log;

import com.harsukh.gmtest.BasePresenter;
import com.harsukh.gmtest.BaseView;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by harsukh on 4/4/17.
 */

public class ImgurPresenter implements ImgurContract.ImgurPresenterContract {

    private ImgurContract.ImgurView imgurView;
    private Observable<ResponseBody> imgurObservable;
    private Subscription subscription;
    private static final String TAG = ImgurPresenter.class.getSimpleName();

    @Inject
    public ImgurPresenter(ImgurContract.ImgurView imgurView, Observable<ResponseBody> observable) {
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
        subscription = imgurObservable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: error when processing load images", e);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                imgurView.displayImages(responseBody.byteStream());
            }
        });
    }
}
