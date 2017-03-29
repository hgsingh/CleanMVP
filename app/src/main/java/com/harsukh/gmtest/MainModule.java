package com.harsukh.gmtest;


import com.harsukh.gmtest.reddithits.Contract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by harsukh on 3/29/17.
 */
@Module
public class MainModule {

    private Contract.View view;

    public MainModule(Contract.View view) {
        this.view = view;
    }

    @Provides
    public Contract.View provideView() {
        return view;
    }
}
