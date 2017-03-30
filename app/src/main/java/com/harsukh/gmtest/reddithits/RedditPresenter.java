package com.harsukh.gmtest.reddithits;

import com.harsukh.gmtest.RecyclerAdapter;
import com.harsukh.gmtest.retrofit.Titles;


import javax.inject.Inject;

import rx.Observable;
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

    @Inject
    public RedditPresenter(View view, Observable<Titles> subscription) {
        this.view = view;
        this.subscription = subscription;

    }

    @Override
    public void subscribe() {
        titlesSubscription = subscription.subscribe(new Action1<Titles>() {
            @Override
            public void call(Titles titles) {
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
    public void setAdapter(Titles titles) {
        recyclerAdapter = new RecyclerAdapter(titles.getData().getChildren());
    }

    @Override
    public RecyclerAdapter getAdapter() {
        return recyclerAdapter;
    }
}
