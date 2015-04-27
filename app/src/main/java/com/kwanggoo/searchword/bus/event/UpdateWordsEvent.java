package com.kwanggoo.searchword.bus.event;

/**
 * Created by josunghwan on 15. 4. 19..
 */
public class UpdateWordsEvent {
    private String mEmail;
    private String mEnglish;
    private Boolean isDeleted;

    public UpdateWordsEvent(String email, String english, Boolean isDeleted) {
        this.mEmail = email;
        this.mEnglish = english;
        this.isDeleted = isDeleted;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

}
