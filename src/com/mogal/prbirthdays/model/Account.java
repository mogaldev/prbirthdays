package com.mogal.prbirthdays.model;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
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


// TODO: use this request me?fields=picture.type(small),name
// Choose proper size: https://developers.facebook.com/docs/reference/api/using-pictures/#sizes

@DatabaseTable(tableName = Account.TABLE_NAME)
public class Account extends BaseModel{

	private static final String TAG = Account.class.getName();
	public static final String TABLE_NAME = "Accounts";
	public static final String FIELD_ID = "id";
	public static final String FIELD_FACEBOOK_ID = "facebook_id";
    public static final String FIELD_NAME = "name";
	public static final String FIELD_ACCESS_TOKEN = "access_token";
	public static final String FIELD_ACCESS_EXPIRES = "access_expires";
	public static final String FIELD_MAIN_ACCOUNT = "is_main_account";

	@DatabaseField(generatedId = true, columnName = FIELD_ID)
	private long mId;
	@DatabaseField(columnName = FIELD_FACEBOOK_ID)
	private String mFacebookId;
    @DatabaseField(columnName = FIELD_NAME)
    private String mName;
	@DatabaseField(columnName = FIELD_ACCESS_TOKEN)
	private String mAccessToken;
	@DatabaseField(columnName = FIELD_ACCESS_EXPIRES)
	private long mAccessExpires;

	Account() {
		// No-ARG for Ormlite
	}

	public Account(String facebookId, String name, String accessToken, long accessExpires) {
		mFacebookId = facebookId;
		mName = name;
		mAccessToken = accessToken;
		mAccessExpires = accessExpires;
	}

//	public Account(JSONObject object) throws JSONException {
//		this(object.getString("id"))
//		super(object, null);
//        mId = object.getString("id");
//        mName = object.getString("name");
//		mAccessToken = object.optString("access_token", null);
//		mAccessExpires = 0;
//		mIsMainAccount = false;
//	}

	public long getmId() {
		return mId;
	}

	public void setmId(long mId) {
		this.mId = mId;
	}

	public String getmFacebookId() {
		return mFacebookId;
	}

	public void setmFacebookId(String mFacebookId) {
		this.mFacebookId = mFacebookId;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmAccessToken() {
		return mAccessToken;
	}

	public void setmAccessToken(String mAccessToken) {
		this.mAccessToken = mAccessToken;
	}

	public long getmAccessExpires() {
		return mAccessExpires;
	}

	public void setmAccessExpires(long mAccessExpires) {
		this.mAccessExpires = mAccessExpires;
	}
	
	// //////////////////////////////////////////////////////////////////////////////////
	// Those functions are from other project. probably we won't need them all
	// /////////////////////////////////////////////////////////////////////////////////
	
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

					Log.d(TAG, "Deleted " + removed + " from database");

					for (Account item : items) {
						dao.create(item);
					}

					Log.d(TAG, "Inserted " + items.size()
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
	
	// //////////////////////////////////////////////////////////////////////////////////
	// Those functions are from other project. probably we won't need them all
	// /////////////////////////////////////////////////////////////////////////////////
}
