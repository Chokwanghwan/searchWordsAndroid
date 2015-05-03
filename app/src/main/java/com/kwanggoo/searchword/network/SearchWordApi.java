package com.kwanggoo.searchword.network;

import com.kwanggoo.searchword.UserInfo;
import com.kwanggoo.searchword.Word;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by josunghwan on 15. 4. 18..
 */
public interface SearchWordApi {

    @FormUrlEncoded
    @POST("/searchWords/updateData")
    void updateWord(
            @FieldMap HashMap<String, String> body,
            Callback<String> callback
    );

    @GET("/searchWords/selectDataForMobile")
    void getWords(
            @Query("email") String email,
            Callback<Word[]> callBack
    );

    @GET("/searchWords/selectDeletedDataForMobile")
    void getKnownWords(
            @Query("email") String email,
            Callback<Word[]> callBack
    );

    @GET("/userInfo")
    void getUserInfo(
            @Query("email") String email,
            Callback<UserInfo> callBack
    );

    @FormUrlEncoded
    @POST("/searchWords/insertDataForMobile")
    void addUrl(
            @FieldMap HashMap<String, String> body,
            Callback<String> callback
    );
}
