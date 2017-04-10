package com.harsukh.gmtest.reddithits;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.harsukh.gmtest.RecyclerAdapter;
import com.harsukh.gmtest.imgur.ImgurActivity;
import com.harsukh.gmtest.retrofit.CollectionsUtil;
import com.harsukh.gmtest.retrofit.Titles;


import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

import static com.harsukh.gmtest.reddithits.Contract.*;

/**
 * Created by harsukh on 3/29/17.
 */

public class RedditPresenter implements Presenter {

    private View view;
    private Observable<Titles> subscription;
    private Subscription titlesSubscription;
    private RecyclerAdapter recyclerAdapter;
    private static final String TAG = RedditPresenter.class.getSimpleName();
    public static final String URL_KEY = "URL_KEY";

    @Inject
    public RedditPresenter(View view, Observable<Titles> subscription) {
        this.view = view;
        this.subscription = subscription;

    }

    @Override
    public void subscribe() {
        titlesSubscription = subscription.subscribe(new Subscriber<Titles>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: error when getting list", e);
            }

            @Override
            public void onNext(Titles titles) {
                view.showView(titles);
            }
        });
    }

    @Override
    public void unsubscribe() {
        if (!titlesSubscription.isUnsubscribed()) {
            titlesSubscription.unsubscribe();
        }
    }

    @Override
    public void setAdapter(Titles titles, IAdapterInterface adapterInterface) {
        List<Titles.Data.ChildrenBean> children = titles.getData().getChildren();
        recyclerAdapter = new RecyclerAdapter((List<Titles.Data.ChildrenBean>) CollectionsUtil.filter(children,
                new CollectionsUtil.IPredicate<Titles.Data.ChildrenBean>() {
                    @Override
                    public boolean apply(Titles.Data.ChildrenBean type) {
                        return type.getData().isImgur();
                    }
                }), adapterInterface);
    }

    @Override
    public RecyclerAdapter getAdapter() {
        return recyclerAdapter;
    }

    @Override
    public void startSlideShow(String url, Context context) {
        Intent intent = new Intent(context, ImgurActivity.class);
        intent.putExtra(URL_KEY, url);
        context.startActivity(new Intent(context, ImgurActivity.class));
    }
}
