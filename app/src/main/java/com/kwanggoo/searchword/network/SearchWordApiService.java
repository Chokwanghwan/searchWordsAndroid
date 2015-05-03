package com.kwanggoo.searchword.network;

import com.kwanggoo.searchword.UpdateWord;
import com.kwanggoo.searchword.UserInfo;
import com.kwanggoo.searchword.Word;
import com.kwanggoo.searchword.bus.event.AddUrlEvent;
import com.kwanggoo.searchword.bus.event.AddedUrlEvent;
import com.kwanggoo.searchword.bus.event.GetKnownWordsEvent;
import com.kwanggoo.searchword.bus.event.GetUserInfo;
import com.kwanggoo.searchword.bus.event.GetWordsEvent;
import com.kwanggoo.searchword.bus.event.LoadKnownWordsEvent;
import com.kwanggoo.searchword.bus.event.LoadUserInfo;
import com.kwanggoo.searchword.bus.event.LoadWordsEvent;
import com.kwanggoo.searchword.bus.event.UpdateWordsEvent;
import com.kwanggoo.searchword.bus.event.UpdatedWordsEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

    @Subscribe
    public void onGetWords(GetWordsEvent event){
        String userEmail = event.getEmail();
        mApi.getWords(userEmail, new Callback<Word[]>() {
            @Override
            public void success(Word[] words, Response response) {
                mBus.post(new LoadWordsEvent(words));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onGetKnownWords(GetKnownWordsEvent event){
        String userEmail = event.getEmail();
        mApi.getKnownWords(userEmail, new Callback<Word[]>() {
            @Override
            public void success(Word[] words, Response response) {
                mBus.post(new LoadKnownWordsEvent(words));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onUpdateWords(UpdateWordsEvent event){
        String userEmail = event.getEmail();
        String english = event.getEnglish();
        Boolean isDeleted = event.isDeleted();
        HashMap<String, String> params = new HashMap<>();
        params.put("email", userEmail);
        params.put("english", english);
        String deleted = isDeleted? "true":"false";
        params.put("is_deleted", deleted);
        mApi.updateWord(params, new Callback<String>() {
            @Override
            public void success(String o, Response response) {
                mBus.post(new UpdatedWordsEvent());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onGetUserInfo(GetUserInfo event){
        String userEmail = event.getEmail();
        mApi.getUserInfo(userEmail, new Callback<UserInfo>() {
            @Override
            public void success(UserInfo userInfo, Response response) {
                UserInfo.setUserInfo(userInfo);
                mBus.post(new LoadUserInfo());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    public void onAddUrl(AddUrlEvent event){
        String url = event.getUrl();
        String email = event.getEmail();
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("url", url);
        mApi.addUrl(params, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                mBus.post(new AddedUrlEvent(s));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
