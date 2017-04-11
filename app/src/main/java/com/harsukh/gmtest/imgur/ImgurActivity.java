package com.harsukh.gmtest.imgur;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.harsukh.gmtest.DaggerMainComponent;
import com.harsukh.gmtest.MainModule;
import com.harsukh.gmtest.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.inject.Inject;

import static com.harsukh.gmtest.reddithits.RedditPresenter.URL_KEY;

/**
 * Created by harsukh on 4/4/17.
 */

public class ImgurActivity extends Activity implements ImgurContract.ImgurView {

    @Inject
    ImgurPresenter presenter;
    private ImageView customImgurView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgur_layout);
        customImgurView = (ImageView) findViewById(R.id.imgurView);
        DaggerMainComponent.builder().mainModule(new MainModule(this, getIntentData())).build().inject(this);
        presenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public void displayImages(Bitmap inputStream) {
        customImgurView.setImageBitmap(inputStream);
        //Picasso.with(this).load(getIntentData()).into(customImgurView);
    }

    @Override
    public String getIntentData() {
        return getIntent().getStringExtra(URL_KEY);
    }
}
