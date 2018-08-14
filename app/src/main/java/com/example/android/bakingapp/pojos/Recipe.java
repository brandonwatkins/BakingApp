package com.example.android.bakingapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO for an Ingredient.
 * Parcelable information created using online tool: http://www.parcelabler.com
 *
 * @author brandonwatkins
 */
public class Recipe implements Parcelable{

    @SerializedName("id")
    private Integer mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("ingredients")
    private List<Ingredient> mIngredients;
    @SerializedName("steps")
    private List<Step> mSteps;
    @SerializedName("servings")
    private Integer mServings;
    @SerializedName("image")
    private String mImage;

    public Recipe(Integer id, String name, List<Ingredient> ingredients, List<Step> steps,
                  Integer servings, String mImage) {
        this.mId = id;
        this.mName = name;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.mServings = servings;
        this.mImage = mImage;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public List<Ingredient> getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public List<Step> getmSteps() {
        return mSteps;
    }

    public void setmSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public Integer getmServings() {
        return mServings;
    }

    public void setmServings(Integer mServings) {
        this.mServings = mServings;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }


    // http://www.parcelabler.com

    protected Recipe(Parcel in) {
        mId = in.readByte() == 0x00 ? null : in.readInt();
        mName = in.readString();
        if (in.readByte() == 0x01) {
            mIngredients = new ArrayList<>();
            in.readList(mIngredients, Ingredient.class.getClassLoader());
        } else {
            mIngredients = null;
        }
        if (in.readByte() == 0x01) {
            mSteps = new ArrayList<>();
            in.readList(mSteps, Step.class.getClassLoader());
        } else {
            mSteps = null;
        }
        mServings = in.readByte() == 0x00 ? null : in.readInt();
        mImage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mId);
        }
        dest.writeString(mName);
        if (mIngredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mIngredients);
        }
        if (mSteps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mSteps);
        }
        if (mServings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mServings);
        }
        dest.writeString(mImage);
    }

    @SuppressWarnings("unused")
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

}
