package com.kwanggoo.searchword.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by josunghwan on 15. 5. 3..
 */
public class UserManager {
    private static final String SP_NAME = "mywords_sp";

    private static final String SP_EMAIL_KEY = "email";
    private static UserManager ourInstance = new UserManager();
    private Context mContext;

    public static UserManager getInstance(Context context) {
        ourInstance.mContext = context;
        return ourInstance;
    }

    private UserManager() {

    }

    public void saveUserEmail(String email) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_EMAIL_KEY, email);
        editor.apply();
    }

    public String getUserEmail() {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(SP_EMAIL_KEY, "");
    }

    public void logout(){
        saveUserEmail("");
    }
}
