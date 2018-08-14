package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojos.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * RecipeAdapter for MainActivity. Creates a the necessary CardViews for the RecyclerView
 *
 * @author brandonwatkins
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private Context mContext;
    private recipeClickListener mOnClickListener;

    public RecipeAdapter(recipeClickListener recipeClickListener) {
        this.mOnClickListener = recipeClickListener;
    }

    public void setData(List<Recipe> mRecipes, Context mContext) {
        this.mRecipes = mRecipes;
        this.mContext= mContext;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_list_content, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mId.setText(String.valueOf(mRecipes.get(position).getmId()));
        viewHolder.mContent.setText(mRecipes.get(position).getmName());
        String recipeImageUrl = mRecipes.get(position).getmImage();

        // Handle situation where an image is available
        if(recipeImageUrl.equals("")) {
            viewHolder.mRecipeImage.setVisibility(View.GONE);
        } else {
            // Load the recipe image if the url is present using Picasso
            viewHolder.mRecipeImage.setVisibility(View.VISIBLE);
            Picasso.with(mContext)
                    .load(mRecipes.get(position).getmImage())
                    .into(viewHolder.mRecipeImage);
        }

    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) {
            return 0;
        } else {
            return mRecipes.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mId;
        TextView mContent;
        ImageView mRecipeImage;

        public ViewHolder(View view) {
            super(view);

            mId = view.findViewById(R.id.id_tv);
            mContent = view.findViewById(R.id.content_tv);
            mRecipeImage = view.findViewById(R.id.recipe_image_iv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnClickListener.onRecipeClick(mRecipes.get(position));
        }

    }

    public interface recipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

}
