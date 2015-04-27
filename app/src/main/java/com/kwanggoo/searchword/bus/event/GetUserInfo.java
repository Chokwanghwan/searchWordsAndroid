package com.kwanggoo.searchword.bus.event;

/**
 * Created by josunghwan on 15. 4. 25..
 */
public class GetUserInfo {
    private String mEmail;

    public GetUserInfo(String email) {
        this.mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }
}
