package com.kwanggoo.searchword.bus.event;

/**
 * Created by josunghwan on 15. 5. 3..
 */
public class AddedUrlEvent {
    private String mResponse;
    public AddedUrlEvent(String s) {
        mResponse = s;
    }

    public String getResponse() {
        return mResponse;
    }
}
