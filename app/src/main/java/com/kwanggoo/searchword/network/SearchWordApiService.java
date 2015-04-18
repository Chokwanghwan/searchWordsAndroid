package com.kwanggoo.searchword.network;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by josunghwan on 15. 4. 18..
 */
public class SearchWordApiService {

    private SearchWordApi mApi;
    private Bus mBus;

    public SearchWordApiService(SearchWordApi mApi, Bus mBus) {
        this.mApi = mApi;
        this.mBus = mBus;
    }
}
