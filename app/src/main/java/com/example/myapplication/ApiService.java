package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("everything")
    Call<NewsResponse> getlistNews(@Query("q") String query,
                                     @Query("apiKey") String apiKey);
}
