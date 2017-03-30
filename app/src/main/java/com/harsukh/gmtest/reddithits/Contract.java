package com.harsukh.gmtest.reddithits;


import com.harsukh.gmtest.BasePresenter;
import com.harsukh.gmtest.BaseView;
import com.harsukh.gmtest.RecyclerAdapter;
import com.harsukh.gmtest.retrofit.Titles;

/**
 * Created by harsukh on 3/29/17.
 */

public interface Contract {

    interface Presenter extends BasePresenter {
        void setAdapter(Titles titles);
        RecyclerAdapter getAdapter();
    }

    interface View extends BaseView<Presenter> {
        void showView(Titles titles);
    }
}
