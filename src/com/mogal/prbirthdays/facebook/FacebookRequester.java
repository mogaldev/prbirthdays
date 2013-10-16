package com.mogal.prbirthdays.facebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;

import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.mogal.prbirthdays.model.Account;

/*public class FacebookRequester {

    // private final Context mContext;
    private final Account mAccount;
    private final Facebook mFacebook;

    public FacebookRequester(Account account) {
        mAccount = account;
        mFacebook = mAccount.getFacebook();
    }

    public List<Account> getAccounts() throws FacebookError, JSONException {
        Bundle b = new Bundle();
        b.putString("date_format", "U");
        b.putString("limit", "3000");

        String response = null;
        try {
            response = mFacebook.request("me/accounts", b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null == response) {
            return null;
        }

        JSONObject document = Util.parseJson(response);

        JSONArray data = document.getJSONArray("data");
        ArrayList<Account> accounts = new ArrayList<Account>(data.length() * 2);
        accounts.add(mAccount);

        JSONObject object;
        Account account;
        for (int i = 0, z = data.length(); i < z; i++) {
            try {
                object = data.getJSONObject(i);
                account = new Account(object);
                if (account.hasAccessToken()) {
                    accounts.add(account);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return accounts;

    }

    public String createNewAlbum(String albumName, String description, String privacy) {
        Bundle b = new Bundle();
        b.putString("name", albumName);

        if (!TextUtils.isEmpty(description)) {
            b.putString("message", description);
        }

        if (!TextUtils.isEmpty(privacy)) {
            try {
                JSONObject object = new JSONObject();
                object.put("value", privacy);
                b.putString("privacy", object.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String response = null;
        try {
            response = mFacebook.request("me/albums", b, "POST");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null == response) {
            return null;
        }

        try {
            JSONObject document = Util.parseJson(response);
            return document.getString("id");
        } catch (FacebookError e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
*/