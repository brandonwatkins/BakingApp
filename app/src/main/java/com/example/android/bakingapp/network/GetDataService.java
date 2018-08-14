package com.example.android.bakingapp.network;

import com.example.android.bakingapp.pojos.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Defines endpoint for RetroFit to use
 * Help from: https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23
 *
 * @author brandonwatkins
 */
public interface GetDataService {

    @GET("baking.json")
    Call<List<Recipe>> getAllRecipes();
}
