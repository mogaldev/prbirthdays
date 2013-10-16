package com.mogal.prbirthdays.helpers;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mogal.prbirthdays.model.Account;

public class DataBaseWrapper {
	
	private static Account createAccount(Context context, Account newAccount) {
		DatabaseHelper databaseHelper = getDataBaseHelper(context);
		Account accountToReturn = newAccount;
		
		try {
			databaseHelper.getAccountDao().create(newAccount);
		} catch (SQLException e) {
			Log.e(DataBaseWrapper.class.getName(), e.getMessage(), e);
			accountToReturn = null;
		}
		
		return accountToReturn;
	}
	
	/**
	 * Create new {@link Account} in the local DB.</br>
	 * Return the new {@link Account} object that was created, if succeeded.
	 * in case the Account has not been created, return <code>null</code>
	 * @param context
	 * @param facebookId
	 * @param name
	 * @param accessToken
	 * @param accessExpires
	 * @return The new {@link Account} that was created if succeeded, <code>null</code> otherwise
	 */
	public static Account createAccount(Context context, String facebookId,
			String name, String accessToken, long accessExpires) {
		Account newAccount = new Account(facebookId, name, accessToken, accessExpires);
		return createAccount(context, newAccount);
	}
	
	private static DatabaseHelper getDataBaseHelper(Context context) {
		return OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}
}
