package com.mogal.prbirthdays.facebook;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mogal.prbirthdays.model.BirthdayUser;

public class ResponseParser {

	public static List<BirthdayUser> getBirthdayUsers(String response) {
		ArrayList<BirthdayUser> birthdayUsers = new ArrayList<BirthdayUser>();

		try {
			JSONObject jsonRoot = new JSONObject(response);
			JSONArray jsonData = jsonRoot.getJSONArray("data");
			int numOfUsers = jsonData.length();

			for (int i = 0; i < numOfUsers; i++) {
				birthdayUsers.add(new BirthdayUser(jsonData.getJSONObject(i)));
			}

		} catch (JSONException e) {
			Log.e("ResponseParser", e.getMessage());
		}

		return birthdayUsers;
	}

}
