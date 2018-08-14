package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.pojos.Recipe;
import com.example.android.bakingapp.utils.Constants;

import java.util.ArrayList;

/**
 * MainActivity is used to display the list of Recipes in a RecyclerView. The Activity layout is the
 * same for both Phone and Tablet.
 *
 * @author brandonwatkins
 */
public class MainActivity extends AppCompatActivity implements RecipeAdapter.recipeClickListener {

    private ArrayList<Recipe> mClickedRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        mClickedRecipeList = new ArrayList<>();
        mClickedRecipeList.add(recipe);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.RECIPE, mClickedRecipeList);

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
}
