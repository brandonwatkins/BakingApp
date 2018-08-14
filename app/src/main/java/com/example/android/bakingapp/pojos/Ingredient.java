package com.example.android.bakingapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * POJO for an Ingredient.
 * Parcelable information created using online tool: http://www.parcelabler.com
 *
 * @author brandonwatkins
 */
public class Ingredient implements Parcelable {

    @SerializedName("quantity")
    private Double mQuantity;
    @SerializedName("measure")
    private String mMeasure;
    @SerializedName("ingredient")
    private String mIngredient;

    public Double getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(Double mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public void setmMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getmIngredient() {
        return mIngredient;
    }

    public void setmIngredient(String mIngredient) {
        this.mIngredient = mIngredient;
    }

    protected Ingredient(Parcel in) {
        mQuantity = in.readByte() == 0x00 ? null : in.readDouble();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mQuantity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(mQuantity);
        }
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }

    @SuppressWarnings("unused")
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
