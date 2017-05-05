package com.harsukh.gmtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harsukh.gmtest.reddithits.Contract;
import com.harsukh.gmtest.reddithits.Contract.IAdapterInterface;
import com.harsukh.gmtest.retrofit.Titles;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsukh on 3/22/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Titles.Data.ChildrenBean> titles;
    private IAdapterInterface activityInterface;
    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    private static final int IMAGE_TYPE = 1;
    private static final int REDDIT_TYPE = 2;
    private RecycledTarget recycledTarget;
    private ItemMuxer<Titles.Data.ChildrenBean> muxer;

    public RecyclerAdapter(List<Titles.Data.ChildrenBean> titles, IAdapterInterface activityInterface) {
        this.titles = titles;
        this.activityInterface = activityInterface;
        recycledTarget = new RecycledTarget();
        muxer = new ItemMuxer<>(titles);
    }


    private final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView url;
        private IAdapterInterface activityInterface;
        private View view;
        private int position = -1;

        public ViewHolder(View v, IAdapterInterface activityInterface) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            url = (TextView) v.findViewById(R.id.url);
            this.activityInterface = activityInterface;
            this.view = v;
        }

        @Override
        public void onClick(View v) {
            Picasso.with(activityInterface.getContext()).cancelRequest(recycledTarget);
            Picasso.with(activityInterface.getContext()).load(url.getText().toString()).into(recycledTarget);
            recycledTarget.setTargetPosition(position);
        }

        public void setTitle(String ptitle) {
            title.setText(ptitle);
        }

        public void setUrl(String purl) {
            url.setText(purl);
        }

        public void setClickListeners(View.OnClickListener listener) {
            view.setOnClickListener(listener);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    private final class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        int position = -1;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgurImage);
        }

        public void setImageForImageView(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        if (viewType == REDDIT_TYPE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v, activityInterface);
            return vh;
        }
        if (viewType == IMAGE_TYPE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.childrow, parent, false);
            ImageViewHolder ih = new ImageViewHolder(v);
            return ih;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setTitle(titles.get(position).getData().getTitle());
            ((ViewHolder) holder).setUrl(titles.get(position).getData().getUrl());
            ((ViewHolder) holder).setClickListeners((ViewHolder) holder);
            ((ViewHolder) holder).setPosition(position);
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).setImageForImageView(recycledTarget.getBitmap());
            ((ImageViewHolder) holder).setPosition(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return muxer.getType(position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    private final class RecycledTarget implements Target {
        private int current_position = -1;
        Bitmap bitmapReference;

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            bitmapReference = bitmap;
            muxer.bitMapAdded(getPosition());
            notifyItemInserted(getPosition());
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.e(TAG, "onBitmapFailed: was not able to retrieve data");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.i(TAG, "Preparing drawable");
        }

        public void setTargetPosition(int position) {
            current_position = position;
        }

        public int getPosition() {
            return current_position;
        }

        public Bitmap getBitmap() {
            if (bitmapReference != null) {
                return bitmapReference;
            } else {
                return BitmapFactory.decodeResource(activityInterface.getContext().getResources(),
                        R.mipmap.ic_launcher);
            }
        }

    }

    private final class ItemMuxer<S extends Titles.Data.ChildrenBean> {
        private List<Pair<S, Integer>> input;

        public ItemMuxer(List<S> items) {
            this.input = copyItems(items);
        }

        private List<Pair<S, Integer>> copyItems(List<S> items) {
            List<Pair<S, Integer>> itemList = new ArrayList<>(items.size());
            for (S item : items) {
                itemList.add(new Pair<>(item, items.indexOf(item)));
            }
            return itemList;
        }

        public int getType(int position) {
            return input.get(position).second.compareTo(position) == 0 ? REDDIT_TYPE : IMAGE_TYPE;
        }

        public void bitMapAdded(int position) {
            int integer = input.get(position).second.intValue();
            S first = input.get(position).first;
            integer = Integer.valueOf(++integer);
            input.remove(position);
            input.add(position, new Pair<>(first, integer));
        }

    }


}
