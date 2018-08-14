package com.example.android.bakingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.network.GetDataService;
import com.example.android.bakingapp.network.RetrofitClient;
import com.example.android.bakingapp.pojos.Recipe;
import com.example.android.bakingapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This fragment is used to display the list of recipes. The information
 * is displayed in CardViews. The Recipes are handled and displayed using a RecyclerView
 *
 * @author brandonwatkins
 */
public class RecipeListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private List<Recipe> mRecipes;

    // Mandatory empty constructor for the fragment manager
    public RecipeListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_recipe_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.main_recipe_rv);
        mRecipeAdapter = new RecipeAdapter((MainActivity)getActivity());
        mRecyclerView.setAdapter(mRecipeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create handle for the RetrofitInstance interface
        GetDataService service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipe>> call = service.getAllRecipes();

        call.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipes = response.body();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants.CHOSEN_RECIPE, (ArrayList) mRecipes);

                mRecipeAdapter.setData(mRecipes, getContext());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("RecipeListFragment", "ERROR: RetroFit failure");
            }
        });

        return view;
    }
}
