package com.example.android.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojos.Recipe;
import com.example.android.bakingapp.pojos.Step;

import java.util.List;

/**
 * RecipeDetailAdapter for RecipeDetailActivity. Creates a the necessary CardViews for the RecyclerView
 *
 * @author brandonwatkins
 */
public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder> {

    private List<Step> mSteps;
    private String mRecipeName;
    private recipeStepClickListener mOnClickListener;

    public RecipeDetailAdapter(recipeStepClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setData(List<Recipe> recipes) {
        mSteps = recipes.get(0).getmSteps();
        mRecipeName = recipes.get(0).getmName();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.detail_recipe_list_content, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Adds bullet point to "Recipe Introduction" card
        if(mSteps.get(position).getmId() == 0) {
            viewHolder.mId.setText("•");
            viewHolder.mContent.setText(mSteps.get(position).getmShortDescription());
        } else {
            viewHolder.mId.setText((mSteps.get(position).getmId()) + ". ");
            viewHolder.mContent.setText(mSteps.get(position).getmShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) {
            return 0;
        } else {
            return mSteps.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mId;
        TextView mContent;

        public ViewHolder(View view) {
            super(view);

            mId = view.findViewById(R.id.step_id_tv);
            mContent = view.findViewById(R.id.description_tv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onRecipeStepClick(mSteps, position, mRecipeName);
        }
    }

    public interface recipeStepClickListener {
        void onRecipeStepClick(List<Step> step, int index, String recipeName);
    }

}
