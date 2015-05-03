package com.kwanggoo.searchword.bus.event;

/**
 * Created by josunghwan on 15. 5. 3..
 */
public class AddUrlEvent {
    private String mUrl;
    private String mEmail;
    public AddUrlEvent(String email, String url) {
        mUrl = url;
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getUrl() {
        return mUrl;
    }
}
