package com.harsukh.gmtest.imgur;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.harsukh.gmtest.reddithits.Contract;

import java.io.InputStream;

/**
 * Created by harsukh on 4/4/17.
 */

public class CustomImgurView extends View {

    private static final int DEFAULT_MOVIE_DURATION = 1000;

    private int movieResourceID;
    private Movie movie;

    private long movieStart = 0;
    private int currentAnimationTime = 0;


    public CustomImgurView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    public void setImageResource(InputStream inputStream){
        movie = Movie.decodeStream(inputStream);
        requestLayout();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(movie != null){
            setMeasuredDimension(movie.width(), movie.height());
        }else{
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (movie != null){
            updateAnimationTime();
            drawGif(canvas);
            invalidate();
        }else{
            drawGif(canvas);
        }
    }

    private void updateAnimationTime() {
        long now = android.os.SystemClock.uptimeMillis();

        if (movieStart == 0) {
            movieStart = now;
        }
        int dur = movie.duration();
        if (dur == 0) {
            dur = DEFAULT_MOVIE_DURATION;
        }
        currentAnimationTime = (int) ((now - movieStart) % dur);
    }

    private void drawGif(Canvas canvas) {
        movie.setTime(currentAnimationTime);
        movie.draw(canvas, 0, 0);
        canvas.restore();
    }

}
