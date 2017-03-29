package com.harsukh.gmtest.reddithits;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.harsukh.gmtest.DaggerMainComponent;
import com.harsukh.gmtest.MainModule;

/**
 * Created by harsukh on 3/29/17.
 */

public class RedditActivity extends Activity implements Contract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {

    }

    @Override
    public void showView() {

    }
}
