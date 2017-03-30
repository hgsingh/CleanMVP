package com.harsukh.gmtest.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by harsukh on 3/22/17.
 */

public class RestClient {
    public static final String BASE_URL = "https://www.reddit.com/";

    private static OkHttpClient buildLogger() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    private static Retrofit retrofit = null;

    public static <S> S createService(String url_resource, Class<S> serviceClass) {
        Retrofit.Builder builder;
        if (retrofit == null) {
            builder = new Retrofit.Builder().baseUrl(url_resource)
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.client(buildLogger()).build();
        }
        return retrofit.create(serviceClass);
    }

    public interface RedditServiceInterface {
        //defines the http method we want to use using retrofit's handy syntax
        @GET("/r/aww/.json")
        Call<Titles> getTitles();
    }
}
