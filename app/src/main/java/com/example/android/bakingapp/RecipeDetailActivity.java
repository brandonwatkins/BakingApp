package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.bakingapp.adapters.RecipeDetailAdapter;
import com.example.android.bakingapp.pojos.Recipe;
import com.example.android.bakingapp.pojos.Step;
import com.example.android.bakingapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * RecipeDetailActivity is used to display the detailed view of a Recipe. This includes Ingredients
 * and Steps that are loaded into a RecyclerView, that is nested inside a NestedScrollView.
 * This Activity layout changes when viewed on either Phone or Tablet
 *
 * @author brandonwatkins
 */
public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.recipeStepClickListener, RecipeStepDetailFragment.ListItemClickListener {

    private ArrayList<Recipe> mRecipe;
    private String mRecipeName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            mRecipeName = savedInstanceState.getString(Constants.ACTIVITY_TITLE);
            getSupportActionBar().setTitle(mRecipeName);
        }

        Bundle bundle = getIntent().getExtras();

        mRecipe = new ArrayList<>();
        mRecipe = bundle.getParcelableArrayList(Constants.RECIPE);
        mRecipeName = mRecipe.get(0).getmName();
        getSupportActionBar().setTitle(mRecipeName);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.detail_fragment, recipeDetailFragment)
                .addToBackStack(Constants.BACK_STACK_RECIPE)
                .commit();

        //https://stackoverflow.com/questions/9279111/determine-if-the-device-is-a-smartphone-or-tablet
        if (findViewById(R.id.recipe_linear_layout).getTag()!= null &&
                findViewById(R.id.recipe_linear_layout).getTag().equals("tablet")) {
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            recipeStepDetailFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.video_fragment, recipeStepDetailFragment)
                    .addToBackStack(Constants.BACK_STACK_RECIPE_STEP)
                    .commit();
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                // https://www.codota.com/code/java/methods/android.app.FragmentManager/getBackStackEntryCount
                // https://stackoverflow.com/questions/7992216/android-fragment-handle-back-button-press
                // Checks to see if the video_fragment is present which means it is on a Tablet.
                // If it is, we can call finish() and return to the MainActivity. If it isnt,
                // we need to check the backstack count to see which screen to return too.
                if (findViewById(R.id.video_fragment) != null) {
                    // Return to MainActivity
                    finish();
                } else {
                    if (fragmentManager.getBackStackEntryCount() > 1) {
                        // Return to
                        fragmentManager.popBackStack(Constants.BACK_STACK_RECIPE, 0);
                    } else if (fragmentManager.getBackStackEntryCount() > 0) {
                        //go back to "Recipe" screen
                        finish();
                    }
                }
            }
        });
    }


    @Override
    public void onRecipeStepClick(List<Step> steps, int index, String recipeName) {

        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportActionBar().setTitle(recipeName);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.STEPS, (ArrayList<Step>) steps);
        bundle.putString(Constants.ACTIVITY_TITLE, recipeName);
        bundle.putInt(Constants.INDEX, index);
        fragment.setArguments(bundle);

        //https://stackoverflow.com/questions/9279111/determine-if-the-device-is-a-smartphone-or-tablet
        if (findViewById(R.id.recipe_linear_layout).getTag()!= null &&
                findViewById(R.id.recipe_linear_layout).getTag().equals("tablet")) {
            fragmentManager.beginTransaction().replace(R.id.video_fragment, fragment)
                    .addToBackStack(Constants.BACK_STACK_RECIPE_STEP)
                    .commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.detail_fragment, fragment)
                    .addToBackStack(Constants.BACK_STACK_RECIPE_STEP)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.ACTIVITY_TITLE, mRecipeName);
    }
}
