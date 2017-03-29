package com.harsukh.gmtest.reddithits;

import com.harsukh.gmtest.BasePresenter;
import com.harsukh.gmtest.BaseView;

/**
 * Created by harsukh on 3/29/17.
 */

public interface Contract {

    interface Presenter extends BasePresenter {
        void startTask();

        void stopTask();

        void resumeTask();

        void deleteTask();
    }

    interface View extends BaseView<Presenter>
    {
        void showView();
    }
}
