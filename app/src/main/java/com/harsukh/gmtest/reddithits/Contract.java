package com.harsukh.gmtest.reddithits;


import android.content.Context;

import com.harsukh.gmtest.BasePresenter;
import com.harsukh.gmtest.BaseView;
import com.harsukh.gmtest.RecyclerAdapter;
import com.harsukh.gmtest.retrofit.Titles;

/**
 * Created by harsukh on 3/29/17.
 */

public interface Contract {

    interface Presenter extends BasePresenter {
        void setAdapter(Titles titles, IAdapterInterface adapterInterface);
        RecyclerAdapter getAdapter();
        void startSlideShow(String url, Context context);
    }

    interface View extends BaseView<Presenter> {
        void showView(Titles titles);
    }

    interface IAdapterInterface {
        void startSlideShow(String url);
    }

}
