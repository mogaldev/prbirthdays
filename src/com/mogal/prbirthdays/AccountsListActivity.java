package com.mogal.prbirthdays;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.mogal.prbirthdays.helpers.AccountsAdapter;
import com.mogal.prbirthdays.model.Account;

public class AccountsListActivity extends Activity {
	private ListView lstAccounts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accounts_list_activity);
		
		lstAccounts = (ListView) findViewById(R.id.lstAccounts);

		ArrayList<Account> accounts = new ArrayList<Account>();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		accounts.add(new Account("123456", "Moshe Beladev", "dsadasdasd323423oi4j23ioj4i23jio423jio423joi423jio23", c.getTimeInMillis()));
		c.add(Calendar.MONTH, -1);
		c.add(Calendar.HOUR, 45);
		accounts.add(new Account("654321", "Madar Gal", "dsadasdasd323423oi4j23ioj4i23jio423jio423joi423jio23", c.getTimeInMillis()));
		
		Account[] arrAccounts = new Account[accounts.size()];
		arrAccounts = accounts.toArray(arrAccounts);
		
		lstAccounts.setAdapter(new AccountsAdapter(
				getApplicationContext(),arrAccounts));
	}


}
