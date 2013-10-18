package com.mogal.prbirthdays.activities;

import android.app.ListActivity;
import android.os.Bundle;

import com.mogal.prbirthdays.R;
import com.mogal.prbirthdays.communication.ProgressDialogHttpResponseHandler;
import com.mogal.prbirthdays.facebook.RequestBuilder;
import com.mogal.prbirthdays.facebook.ResponseParser;
import com.mogal.prbirthdays.facebook.Session;
import com.mogal.prbirthdays.lists.BirthdayUserAdapter;

public class BirthdayUsersListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadUsers();
	}

	private void loadUsers() {
		Session session = Session.restore(this);
		if (session != null)
			RequestBuilder.shared_access_token = session.getFb()
					.getAccessToken();

		RequestBuilder.queryBirthdayUsers(RequestBuilder.dateFrom,
				RequestBuilder.dateTo, RequestBuilder.shared_access_token,
				new ProgressDialogHttpResponseHandler(this) {
					@Override
					public void onSuccess(String response) {
						super.onSuccess(response);
						BirthdayUsersListActivity.this.setListAdapter(new BirthdayUserAdapter(
								ResponseParser
										.parseBirthdayUsersResponse(response),
								BirthdayUsersListActivity.this,
								R.layout.birthday_user_item));
					}
				});

	}

}
