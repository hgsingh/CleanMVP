package com.harsukh.gmtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harsukh.gmtest.retrofit.Titles;

/**
 * Created by harsukh on 3/22/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Titles.Data.ChildrenBean.DataBean[] titles;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView url;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            url = (TextView) v.findViewById(R.id.url);
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.title.setText(titles[position].getTitle());
        holder.url.setText(titles[position].getUrl());
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
