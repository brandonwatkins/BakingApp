package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.adapters.RecipeDetailAdapter;
import com.example.android.bakingapp.pojos.Ingredient;
import com.example.android.bakingapp.pojos.Recipe;
import com.example.android.bakingapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is used to display the details about the recipe selected. The information
 * is displayed in CardViews. The Steps are handled using a RecyclerView
 *
 * @author brandonwatkins
 */
public class RecipeDetailFragment extends Fragment {

    private ArrayList<Recipe> mRecipe;
    private TextView mIngredients;
    private RecyclerView mRecyclerView;
    private String mRecipeName;

    // Mandatory empty constructor for the fragment manager
    public RecipeDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_recipe_fragment, viewGroup, false);
        mIngredients = view.findViewById(R.id.ingredients_tv);
        mRecyclerView = view.findViewById(R.id.recipe_detail_rv);
        mRecipe = new ArrayList<>();

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        RecipeDetailAdapter mRecipeDetailAdapter = new RecipeDetailAdapter((RecipeDetailActivity)getActivity());

        if(savedInstanceState == null) mRecipe = getArguments().getParcelableArrayList(Constants.RECIPE);
        else mRecipe = savedInstanceState.getParcelableArrayList(Constants.RECIPE);

        mRecipeName = mRecipe.get(0).getmName();

        // Add ingredients
        List<Ingredient> ingredientsList = mRecipe.get(0).getmIngredients();
        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : ingredientsList) {
            builder.append("•" + " " + ingredient.getmIngredient() + " "
                    + ingredient.getmQuantity() + " " + ingredient.getmMeasure() + "\n");
        }

        ArrayList<String> widgetIngredients = new ArrayList<>();
        widgetIngredients.add(builder.toString());

        //update widget
//        BakingWidgetUpdateService.startBakingService(getContext(), widgetIngredients);

        mIngredients.setText(builder.toString());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRecipeDetailAdapter);

        mRecipeDetailAdapter.setData(mRecipe);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.ACTIVITY_TITLE, mRecipeName);
        bundle.putParcelableArrayList(Constants.RECIPE, mRecipe);
    }
}


