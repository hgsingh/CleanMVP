package com.harsukh.gmtest.reddithits;


import javax.inject.Inject;

/**
 * Created by harsukh on 3/29/17.
 */

public class RedditPresenter implements Contract.Presenter {

    private Contract.View view;

    @Inject
    public RedditPresenter(Contract.View view) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void startTask() {

    }

    @Override
    public void stopTask() {

    }

    @Override
    public void resumeTask() {

    }

    @Override
    public void deleteTask() {

    }
}
