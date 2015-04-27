package com.kwanggoo.searchword.bus.event;

/**
 * Created by josunghwan on 15. 4. 25..
 */
public class GetKnownWordsEvent {
    private String mEmail;

    public GetKnownWordsEvent(String email) {
        this.mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }
}
