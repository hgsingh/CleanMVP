package com.harsukh.gmtest.imgur;

import android.graphics.Bitmap;

import com.harsukh.gmtest.BasePresenter;
import com.harsukh.gmtest.BaseView;

import java.io.InputStream;

/**
 * Created by harsukh on 4/4/17.
 */

public interface ImgurContract {

    interface ImgurView extends BaseView<ImgurPresenter> {
        void displayImages(Bitmap inputStream);

        String getIntentData();
    }

    interface ImgurPresenterContract extends BasePresenter {
        void loadImages();
    }
}
