package com.mogal.prbirthdays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.mogal.prbirthdays.facebook.RequestBuilder;
import com.mogal.prbirthdays.facebook.ResponseParser;
import com.mogal.prbirthdays.facebook.Session;
import com.mogal.prbirthdays.helpers.BirthdayUsersAdapter;
import com.mogal.prbirthdays.model.BirthdayUser;

public class BirthdayUsersListActivity extends Activity {
	private ListView lstBirthdayUsers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.birthday_users_list_activity);

		lstBirthdayUsers = (ListView) findViewById(R.id.lstvBirthdayUsersList);

		loadUsers();
	}

	private void loadUsers() {
		Session session = Session.restore(this);
		if (session != null)
			RequestBuilder.shared_access_token = session.getFb()
					.getAccessToken();

		new HttpGetter(this).execute(RequestBuilder.getRequestUrl());
	}

	private class HttpGetter extends AsyncTask<String, Void, String> {
		private ProgressDialog prgrsDialog;
		private Context context;

		public HttpGetter(Context context) {
			this.context = context;
		}

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urls[0]);

			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					Log.v("Getter", "Your data: " + builder.toString()); // response
				} else {
					Log.e("Getter", "Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return builder.toString();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 prgrsDialog = new ProgressDialog(context);
			 prgrsDialog.setMessage("Loading...");
			 prgrsDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			prgrsDialog.dismiss();

			List<BirthdayUser> birthdayUsers = ResponseParser
					.getBirthdayUsers(result);

			BirthdayUser[] birthdayUsersArr = new BirthdayUser[birthdayUsers
					.size()];
			birthdayUsersArr = birthdayUsers.toArray(birthdayUsersArr);

			lstBirthdayUsers.setAdapter(new BirthdayUsersAdapter(
					getApplicationContext(), birthdayUsersArr));
			Toast.makeText(getApplicationContext(), "" + birthdayUsers.size(),
					Toast.LENGTH_LONG).show();
		}

	}

}
