package com.kwanggoo.searchword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by josunghwan on 15. 4. 18..
 */
public class Word {
    @SerializedName("english")
    String mEnglish;

    @SerializedName("mean")
    String mMean;

    public Word(String english, String mean) {
        this.mEnglish = english;
        this.mMean = mean;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public String getMean() {
        return mMean;
    }
}
