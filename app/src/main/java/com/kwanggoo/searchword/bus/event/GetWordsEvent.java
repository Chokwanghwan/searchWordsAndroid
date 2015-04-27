package com.kwanggoo.searchword.bus.event;

/**
 * Created by josunghwan on 15. 4. 19..
 */
public class GetWordsEvent {
    private String mEmail;

    public GetWordsEvent(String email) {
        this.mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }
}
