package com.example.tetiana.popularmovies.Service;

import com.example.tetiana.popularmovies.BuildConfig;

import retrofit.RequestInterceptor;

public class RestAdapter {

    static String key = BuildConfig.MY_MOVIE_DB_API_KEY;

    public static MoviesApiService getService(){
        retrofit.RestAdapter restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", key);

                    }
                })
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter.create(MoviesApiService.class);
    }
}
