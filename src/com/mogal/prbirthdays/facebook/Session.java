package com.mogal.prbirthdays.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.facebook.android.Facebook;
import com.mogal.prbirthdays.helpers.Constants;

/**
 * A utility class for storing and retrieving Facebook session data.
 */
public class Session {

	static final String TOKEN = "access_token";
	static final String EXPIRES = "expires_in";
	static final String KEY = "facebook-session";
	static final String UID = "uid";
	static final String NAME = "name";

	// The Facebook object
	private Facebook fb;

	// The user id of the logged in user
	private String uid;

	// The user name of the logged in user
	private String name;

	/**
	 * Constructor
	 */
	public Session(Facebook fb, String uid, String name) {
		this.fb = fb;
		this.uid = uid;
		this.name = name;
	}

	/**
	 * Returns the Facebook object
	 */
	public Facebook getFb() {
		return fb;
	}

	/**
	 * Returns the session user's id
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Returns the session user's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Stores the session data on disk.
	 */
	public void save(Context context) {

		Log.d(getClass().getSimpleName(),
				"Saving Session! Expires: " + fb.getAccessExpires());

		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
				.edit();
		editor.putString(TOKEN, fb.getAccessToken());
		editor.putLong(EXPIRES, fb.getAccessExpires());
		editor.putString(UID, uid);
		editor.putString(NAME, name);
		editor.commit();
	}

	/**
	 * Loads the session data from disk.
	 */
	public static Session restore(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(KEY,
				Context.MODE_PRIVATE);

		Facebook fb = new Facebook(Constants.FACEBOOK_APP_ID);
		fb.setAccessToken(prefs.getString(TOKEN, null));
		fb.setAccessExpires(prefs.getLong(EXPIRES, 0));

		String uid = prefs.getString(UID, null);
		String name = prefs.getString(NAME, null);

		if (fb.isSessionValid() && uid != null && name != null) {
			return new Session(fb, uid, name);
		}

		return null;
	}

	/**
	 * Clears the saved session data.
	 */
	public static void clearSavedSession(Context context) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
				.edit();
		editor.clear().commit();
	}

}