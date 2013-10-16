package com.mogal.prbirthdays.helpers;

import java.sql.SQLException;
import java.util.List;

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
	
	private static Account updateAccount(Context context, Account newAccount) {
		DatabaseHelper databaseHelper = getDataBaseHelper(context);
		Account accountToReturn = newAccount;
		
		try {
			databaseHelper.getAccountDao().update(newAccount);
		} catch (SQLException e) {
			Log.e(DataBaseWrapper.class.getName(), e.getMessage(), e);
			accountToReturn = null;
		}
		
		return accountToReturn;
	}
	
	/**
	 * Create new {@link Account} in the local DB.</br>
	 * If the account is already exists (facebookId exists), only update the {@link Account}</br>
	 * Return the new {@link Account} object that was created, if succeeded.
	 * in case the Account has not been created, return <code>null</code>
	 * @param context
	 * @param facebookId
	 * @param name
	 * @param accessToken
	 * @param accessExpires
	 * @return The new {@link Account} that was created if succeeded, <code>null</code> otherwise
	 */
	public static Account createOrUpdateAccount(Context context, String facebookId,
			String name, String accessToken, long accessExpires) {
		Account newAccount = new Account(facebookId, name, accessToken, accessExpires);
		Account accountToReturn;
		List<Account> accountByFacebookId = null;
		
		try {
			// Check if the account already exists in the DB
			accountByFacebookId= getDataBaseHelper(context)
					.getAccountDao().queryBuilder().where()
					.eq(Account.FIELD_FACEBOOK_ID, facebookId).query();
			
		} catch (SQLException e) {
		}
			
		// If so, only update the Account
		if (accountByFacebookId != null && !accountByFacebookId.isEmpty()) {
			accountToReturn = updateAccount(context, accountByFacebookId.get(0));
		} else {
			// Else, create new account in the DB
			accountToReturn = createAccount(context, newAccount);
		}
		
		return accountToReturn;
	}
	
	private static DatabaseHelper getDataBaseHelper(Context context) {
		return OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}
}
