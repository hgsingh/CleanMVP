package com.harsukh.gmtest.reddithits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.harsukh.gmtest.DaggerMainComponent;
import com.harsukh.gmtest.MainModule;
import com.harsukh.gmtest.R;
import com.harsukh.gmtest.retrofit.Titles;

import javax.inject.Inject;

/**
 * Created by harsukh on 3/29/17.
 */

public class RedditActivity extends AppCompatActivity implements Contract.View, Contract.IAdapterInterface {

    @Inject
    RedditPresenter redditPresenter;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_layout);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        redditPresenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        redditPresenter.unsubscribe();
    }

    @Override
    public void showView(Titles titles) {
        recyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        redditPresenter.setAdapter(titles, this);
        recyclerView.setAdapter(redditPresenter.getAdapter());
    }

    @Override
    public void startSlideShow(String url) {
        redditPresenter.startSlideShow(url, this);
    }
}
