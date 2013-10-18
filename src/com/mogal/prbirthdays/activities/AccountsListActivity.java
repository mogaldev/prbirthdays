package com.mogal.prbirthdays.activities;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.mogal.prbirthdays.R;
import com.mogal.prbirthdays.lists.AccountsAdapter;
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
		accounts.add(new Account("123456", "Moshe Beladev",
				"dsadasdasd323423oi4j23ioj4i23jio423jio423joi423jio23", c
						.getTimeInMillis()));
		c.add(Calendar.MONTH, -1);
		c.add(Calendar.HOUR, 45);
		accounts.add(new Account("654321", "Madar Gal",
				"dsadasdasd323423oi4j23ioj4i23jio423jio423joi423jio23", c
						.getTimeInMillis()));
		c.add(Calendar.HOUR, -354);
		accounts.add(new Account("123456",
				"Somelonglongname AndAlsolonglong Familyname",
				"dsadasdasd323423oi4j23ioj4i23jio423jio423joi423jio23", c
						.getTimeInMillis()));
		accounts.add(new Account("123456", "Victoria Avsadjanov",
				"dsadasdasd323423oi4j23ioj4i23jio423jio423joi423jio23", c
						.getTimeInMillis()));
		accounts.add(new Account("123456", "Shmuel Yehaezkel",
				"dsadasdasd323423oi4j23ioj4i23jio423jio423joi423jio23", c
						.getTimeInMillis()));
		accounts.add(new Account("123456", "Andrey Ostrovsky",
				"dsadasdasd323423oi4j23ioj4i23jio423jio423joi423jio23", c
						.getTimeInMillis()));

		Account[] arrAccounts = new Account[accounts.size()];
		arrAccounts = accounts.toArray(arrAccounts);

		lstAccounts.setAdapter(new AccountsAdapter(arrAccounts, this,
				R.layout.account_list_item));
	}

}
