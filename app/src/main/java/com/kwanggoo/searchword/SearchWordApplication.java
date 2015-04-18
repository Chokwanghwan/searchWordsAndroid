package com.kwanggoo.searchword;

import android.app.Application;
import android.util.Log;

import com.kwanggoo.searchword.bus.BusProvider;
import com.kwanggoo.searchword.network.SearchWordApi;
import com.kwanggoo.searchword.network.SearchWordApiService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by josunghwan on 15. 4. 18..
 */
public class SearchWordApplication extends Application {
    private Bus mBus = BusProvider.getBus();
    private SearchWordApiService mApiService;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiService = new SearchWordApiService(buildSearchWordApi(getString(R.string.api_host)), mBus);
        mBus.register(mApiService);
    }

    private SearchWordApi buildSearchWordApi(String apiUrl) {
        return new RestAdapter.Builder()
                .setEndpoint(apiUrl)
                .setClient(new OkClient(new OkHttpClient()))
                .build()
                .create(SearchWordApi.class);
    }
}
