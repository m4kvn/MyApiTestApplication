package com.masahirosaito.myapitestapplication;

import com.masahirosaito.myapitestapplication.model.Shop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("shops/{id}.json")
    Call<Shop> getShop(@Path("id") int shopId);

    @GET("shops.json")
    Call<List<Shop>> getShops();
}
