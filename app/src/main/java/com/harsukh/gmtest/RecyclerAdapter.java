package com.harsukh.gmtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harsukh.gmtest.reddithits.Contract;
import com.harsukh.gmtest.reddithits.Contract.IAdapterInterface;
import com.harsukh.gmtest.retrofit.Titles;

import java.util.List;

/**
 * Created by harsukh on 3/22/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Titles.Data.ChildrenBean> titles;
    private IAdapterInterface activityInterface;

    public RecyclerAdapter(List<Titles.Data.ChildrenBean> titles, IAdapterInterface activityInterface) {
        this.titles = titles;
        this.activityInterface = activityInterface;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private TextView title;
        private TextView url;
        private IAdapterInterface activityInterface;

        public ViewHolder(View v, IAdapterInterface activityInterface) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            url = (TextView) v.findViewById(R.id.url);
            this.activityInterface = activityInterface;
        }

        @Override
        public void onClick(View v) {
            activityInterface.startSlideShow(url.getText().toString());
        }

        @Override
        public boolean onLongClick(View v) {
            activityInterface.startSlideShow(url.getText().toString());
            return true;
        }

        public void setTitle(String ptitle) {
            title.setText(ptitle);
        }

        public void setUrl(String purl) {
            url.setText(purl);
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, activityInterface);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.setTitle(titles.get(position).getData().getTitle());
        holder.setUrl(titles.get(position).getData().getUrl());
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }


}
