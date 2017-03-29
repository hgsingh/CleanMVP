package com.harsukh.gmtest;

import com.harsukh.gmtest.reddithits.RedditActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by harsukh on 3/29/17.
 */
@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(RedditActivity redditActivity);
}
