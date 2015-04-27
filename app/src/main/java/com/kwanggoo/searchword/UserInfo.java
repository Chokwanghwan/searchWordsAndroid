package com.kwanggoo.searchword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by josunghwan on 15. 4. 25..
 */
public class UserInfo {
//    {"all_word_count": 0, "deleted_word_count": 0, "url_count": 0}
    private static UserInfo mInstance;

    @SerializedName("all_word_count")
    private int mAllWordCount;

    @SerializedName("deleted_word_count")
    private int mKnownWordCount;

    @SerializedName("url_count")
    private int mUrlCount;

    public static UserInfo getInstance(){
        if(mInstance == null)
            mInstance = new UserInfo();
        return mInstance;
    }

    public static void setUserInfo(UserInfo ui){
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setAllWordCount(ui.getAllWordCount());
        userInfo.setUrlCount(ui.getUrlCount());
        userInfo.setKnownWordCount(ui.getKnownWordCount());
    }

    public void minusKnownWordCount(){
        mKnownWordCount--;
    }

    public void plusKnownWordCount(){
        mKnownWordCount++;
    }


    public int getAllWordCount() {
        return mAllWordCount;
    }

    private void setAllWordCount(int mAllWordCount) {
        this.mAllWordCount = mAllWordCount;
    }

    public int getKnownWordCount() {
        return mKnownWordCount;
    }

    private void setKnownWordCount(int mKnownWordCount) {
        this.mKnownWordCount = mKnownWordCount;
    }

    public int getUrlCount() {
        return mUrlCount;
    }

    private void setUrlCount(int mUrlCount) {
        this.mUrlCount = mUrlCount;
    }
}
