package com.mogal.prbirthdays.model;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mogal.prbirthdays.facebook.Session;
import com.mogal.prbirthdays.helpers.Constants;
import com.mogal.prbirthdays.helpers.DatabaseHelper;

@DatabaseTable(tableName = "account")
public class Account extends AbstractFacebookObject {

	static final String LOG_TAG = "Account";

	public static final String FIELD_ACCESS_TOKEN = "access_token";
	public static final String FIELD_ACCESS_EXPIRES = "access_expires";
	public static final String FIELD_MAIN_ACCOUNT = "is_main_account";

	@DatabaseField(columnName = FIELD_ACCESS_TOKEN)
	private String mAccessToken;
	@DatabaseField(columnName = FIELD_ACCESS_EXPIRES)
	private long mAccessExpires;
	@DatabaseField(columnName = FIELD_MAIN_ACCOUNT)
	private boolean mIsMainAccount;

	Account() {
		// No-ARG for Ormlite
	}

	private Account(String id, String name, String accessToken,
			long accessExpires) {
		super(id, name, null);
		mAccessToken = accessToken;
		mAccessExpires = accessExpires;
		mIsMainAccount = true;
	}

	public Account(JSONObject object) throws JSONException {
		super(object, null);
		mAccessToken = object.optString("access_token", null);
		mAccessExpires = 0;
		mIsMainAccount = false;
	}

	public String getAccessToken() {
		return mAccessToken;
	}

	public boolean isMainAccount() {
		return mIsMainAccount;
	}

	public boolean hasAccessToken() {
		return !TextUtils.isEmpty(mAccessToken);
	}

	public static Account getMeFromSession(Session session) {
		if (null != session) {
			final Facebook fb = session.getFb();
			return new Account(session.getUid(), session.getName(),
					fb.getAccessToken(), fb.getAccessExpires());
		}
		return null;
	}

	public static Account getAccountFromSession(Context context) {
		return getMeFromSession(Session.restore(context));
	}

	public Facebook getFacebook() {
		Facebook facebook = new Facebook(Constants.FACEBOOK_APP_ID);
		facebook.setAccessToken(mAccessToken);
		facebook.setAccessExpires(mAccessExpires);
		return facebook;
	}

	public void onFacebookError(FacebookError e) {
		// NO-OP
	}

	public void preload(Context context) {
	}

	public static List<Account> getFromDatabase(Context context) {
		final DatabaseHelper helper = OpenHelperManager.getHelper(context,
				DatabaseHelper.class);
		List<Account> items = null;

		try {
			final Dao<Account, String> dao = helper.getAccountDao();
			items = dao.query(dao.queryBuilder().prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OpenHelperManager.releaseHelper();
		}

		return items;
	}

	public static void saveToDatabase(Context context, final List<Account> items) {
		final DatabaseHelper helper = OpenHelperManager.getHelper(context,
				DatabaseHelper.class);

		try {
			final Dao<Account, String> dao = helper.getAccountDao();
			dao.callBatchTasks(new Callable<Void>() {

				public Void call() throws Exception {
					// Delete all
					int removed = dao.delete(dao.deleteBuilder().prepare());

					Log.d(LOG_TAG, "Deleted " + removed + " from database");

					for (Account item : items) {
						dao.create(item);
					}

					Log.d(LOG_TAG, "Inserted " + items.size()
							+ " into database");

					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OpenHelperManager.releaseHelper();
		}
	}

}
