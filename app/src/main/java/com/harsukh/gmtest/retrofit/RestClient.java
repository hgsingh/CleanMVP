package com.harsukh.gmtest.retrofit;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by harsukh on 3/22/17.
 */

public class RestClient {
    public static final String BASE_URL = "https://www.reddit.com/";
    private static HostSelectInterceptor host_interceptor = null;

    private static OkHttpClient buildLogger() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        host_interceptor = new HostSelectInterceptor();
        return new OkHttpClient.Builder().addInterceptor(interceptor).
                addInterceptor(host_interceptor).build();
    }

    private static Retrofit retrofit = null;

    public static <S> S createService(String url_resource, Class<S> serviceClass) {
        Retrofit.Builder builder;
        if (retrofit == null) {
            builder = new Retrofit.Builder().baseUrl(url_resource)
                    .addConverterFactory(GsonConverterFactory.create()).
            addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            retrofit = builder.client(new OkHttpClient()).build();
        }
        return retrofit.create(serviceClass);
    }

    private final static class HostSelectInterceptor implements Interceptor {
        private volatile String host;

        public void setHost(String host) {
            this.host = host;
        }


        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String host = this.host;
            if (host != null && !request.url().host().equalsIgnoreCase(host)) {
                HttpUrl newUrl = request.url().newBuilder().host(host).build();
                request.newBuilder().url(newUrl).build();
            }
            return chain.proceed(request);
        }
    }

    public static void setUrl(String url) {
        host_interceptor.setHost(url);
    }


    public interface RedditServiceInterface {
        //defines the http method we want to use using retrofit's handy syntax
        @GET("/r/aww/.json")
        Observable<Titles> getTitles();
    }

}
