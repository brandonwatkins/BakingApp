package com.example.android.bakingapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Instantiates a RetroFit client and defines url for baking recipe info
 * Help from: https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23
 *
 * @author brandonwatkins
 */
public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
