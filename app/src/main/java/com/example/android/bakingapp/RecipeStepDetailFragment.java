package com.example.android.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.pojos.Recipe;
import com.example.android.bakingapp.pojos.Step;
import com.example.android.bakingapp.utils.Constants;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is used to display the SimpleExoPlayerView that shows the Recipes video
 * instruction.
 *
 * @author brandonwatkins
 */
public class RecipeStepDetailFragment extends Fragment {
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private ArrayList<Step> mStepsList = new ArrayList<>();
    private int selectedIndex;
    private ArrayList<Recipe> mRecipeList;
    private String mRecipeName;

    // Mandatory empty constructor for the fragment manager
    public RecipeStepDetailFragment() {}

   private ListItemClickListener itemClickListener;

    public interface ListItemClickListener {
        void onRecipeStepClick(List<Step> allSteps, int Index, String recipeName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView;

        itemClickListener = (RecipeDetailActivity) getActivity();

        mRecipeList = new ArrayList<>();

        if(savedInstanceState != null) {
            mStepsList = savedInstanceState.getParcelableArrayList(Constants.STEPS);
            selectedIndex = savedInstanceState.getInt(Constants.INDEX);
            mRecipeName = savedInstanceState.getString(Constants.ACTIVITY_TITLE);
        }
        else {
            mStepsList = getArguments().getParcelableArrayList(Constants.STEPS);
            if (mStepsList != null) {
                mStepsList = getArguments().getParcelableArrayList(Constants.STEPS);
                selectedIndex = getArguments().getInt(Constants.INDEX);
                mRecipeName = getArguments().getString(Constants.ACTIVITY_TITLE);
            }
            else {
                mRecipeList = getArguments().getParcelableArrayList(Constants.RECIPE);
                //casting List to ArrayList
                mStepsList = (ArrayList<Step>) mRecipeList.get(0).getmSteps();
                selectedIndex = 0;
            }

        }

        View rootView = inflater.inflate(R.layout.detail_recipe_step_fragment, container, false);
        textView = rootView.findViewById(R.id.step_instructions_tv);
        textView.setText(mStepsList.get(selectedIndex).getmDescription());
        textView.setVisibility(View.VISIBLE);

        mPlayerView = rootView.findViewById(R.id.exo_player);
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoURL = mStepsList.get(selectedIndex).getmVideoURL();

        String imageUrl = mStepsList.get(selectedIndex).getmThumbnailURL();
        if (imageUrl != "") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
        }

        if (!videoURL.isEmpty()) {
            initializePlayer(Uri.parse(mStepsList.get(selectedIndex).getmVideoURL()));

        } else {
            mExoPlayer =null;
        }


        Button mPrevStep = rootView.findViewById(R.id.previousStep);
        Button mNextstep = rootView.findViewById(R.id.nextStep);

        mPrevStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            if (mStepsList.get(selectedIndex).getmId() > 0) {
                if (mExoPlayer !=null){
                    mExoPlayer.stop();
                }
                itemClickListener.onRecipeStepClick(mStepsList, mStepsList.get(selectedIndex).getmId() - 1, mRecipeName);
            }
            else {
                Toast.makeText(getActivity(),"You already are in the First step of the mRecipeList", Toast.LENGTH_SHORT).show();

            }
        }});

        mNextstep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            int lastIndex = mStepsList.size()-1;
            if (mStepsList.get(selectedIndex).getmId() < mStepsList.get(lastIndex).getmId()) {
                if (mExoPlayer !=null){
                    mExoPlayer.stop();
                }
                itemClickListener.onRecipeStepClick(mStepsList, mStepsList.get(selectedIndex).getmId() + 1, mRecipeName);
            }
            else {
                Toast.makeText(getContext(),"You already are in the Last step of the mRecipeList", Toast.LENGTH_SHORT).show();

            }
        }});

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(Constants.STEPS, mStepsList);
        bundle.putInt(Constants.INDEX, selectedIndex);
        bundle.putString(Constants.ACTIVITY_TITLE, mRecipeName);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoPlayer !=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoPlayer !=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer =null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer !=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer !=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }

}
