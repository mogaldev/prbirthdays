package com.mogal.prbirthdays.facebook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mogal.prbirthdays.model.BirthdayUser;

public class ResponseParser {

	/**
	 * Birthday User Parsing
	 */

	public static BirthdayUser[] parseBirthdayUsersResponse(String response) {
		BirthdayUser[] birthdayUsers = null;

		try {
			JSONObject jsonRoot = new JSONObject(response);
			JSONArray jsonData = jsonRoot.getJSONArray("data");
			int numOfUsers = jsonData.length();

			birthdayUsers = new BirthdayUser[numOfUsers];

			for (int i = 0; i < numOfUsers; i++) {
				birthdayUsers[i] = parseBirthdayUser(jsonData.getJSONObject(i));
			}

		} catch (JSONException e) {
			Log.e("ResponseParser", e.getMessage());
		}

		return birthdayUsers;
	}

	private static BirthdayUser parseBirthdayUser(JSONObject jsonObj)
			throws JSONException {
		BirthdayUser birthdayUser = new BirthdayUser();

		birthdayUser.setUid(jsonObj.getLong("uid"));
		birthdayUser.setName(jsonObj.getString("name"));
		birthdayUser.setBirthday(jsonObj.getString("anon"));// birthday_date
		birthdayUser.setProfilePicUrl(jsonObj.getString("pic_square"));

		return birthdayUser;
	}

	/**
	 * End of Birthday User Parsing
	 */
}
