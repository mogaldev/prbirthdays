package com.mogal.prbirthdays;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.mogal.prbirthdays.facebook.Session;
import com.mogal.prbirthdays.helpers.Constants;
import com.mogal.prbirthdays.helpers.DataBaseWrapper;

public class LoginActivity extends Activity implements View.OnClickListener {

	static final int REQUEST_FACEBOOK_SSO = 100;

	private Facebook mFacebook;

	private Button mLoginBtn, mLogoutBtn;
	private TextView mMessageTv;
	
	public void onClick(View v) {
		if (v == mLoginBtn) {
			loginToFacebook();
		} else if (v == mLogoutBtn) {
			logoutOfFacebook();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null == mFacebook) {
			mFacebook = new Facebook(Constants.FACEBOOK_APP_ID);
			mFacebook.setAuthorizeParams(new DialogListener() {
				@Override
				public void onFacebookError(FacebookError arg0) {
					Log.e("onFacebookError Login", arg0.getMessage());
				}

				@Override
				public void onError(DialogError arg0) {
					Log.e("onError Login", arg0.getMessage());
				}

				@Override
				public void onComplete(Bundle arg0) {
					saveFacebookSession();
				}

				@Override
				public void onCancel() {
					Log.e("onCancel Login", "");
				}

			}, REQUEST_FACEBOOK_SSO);
		}
		mFacebook.authorizeCallback(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mLoginBtn = (Button) findViewById(R.id.btn_login);
		mLoginBtn.setOnClickListener(this);

		mLogoutBtn = (Button) findViewById(R.id.btn_logout);		
		mLogoutBtn.setOnClickListener(this);

		mMessageTv = (TextView) findViewById(R.id.tv_login_message);

		((Button)findViewById(R.id.btnMoveToAccounts)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), AccountsListActivity.class));
			}
		});
		
		
		((Button) findViewById(R.id.btnMoveNextActivity))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(v.getContext(),
								DatesSelectionActivity.class));
					}
				});

		final String action = getIntent().getAction();
		if (Constants.INTENT_NEW_PERMISSIONS.equals(action)) {
			loginToFacebook();
		} else if (Constants.INTENT_LOGOUT.equals(action)) {
			logoutOfFacebook();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		refreshUi();
	}

	private void loginToFacebook() {
		mFacebook = new Facebook(Constants.FACEBOOK_APP_ID);
		mFacebook.authorize(this, Constants.FACEBOOK_PERMISSIONS,
				BuildConfig.DEBUG ? Facebook.FORCE_DIALOG_AUTH : REQUEST_FACEBOOK_SSO, 
				new DialogListener() {
					@Override
					public void onFacebookError(FacebookError arg0) {
						Log.e("onFacebookError Login", arg0.getMessage());
					}

					@Override
					public void onError(DialogError arg0) {
						Log.e("onError Login", arg0.getMessage());
					}

					@Override
					public void onComplete(Bundle arg0) {
						saveFacebookSession();
					}

					@Override
					public void onCancel() {
						Log.e("onCancel Login", "");
					}

				});
	}

	private void logoutOfFacebook() {
		// Actual log out request
		Session session = Session.restore(this);
		if (null != session) {
			new AsyncFacebookRunner(session.getFb()).logout(
					getApplicationContext(),
					new AsyncFacebookRunner.SimpleRequestListener());
		}

		Session.clearSavedSession(this);

		refreshUi();
	}

	private void refreshUi() {
		Session session = Session.restore(this);
		if (session != null) {
			Log.e("refreshUi", "session != null");
			mMessageTv.setVisibility(View.GONE);
			mLoginBtn.setVisibility(View.GONE);
			mLogoutBtn.setText(String.format("Logout %1$s", session.getName()));
			mLogoutBtn.setVisibility(View.VISIBLE);
		} else {
			Log.e("refreshUi", "session == null");
			mMessageTv.setText("Welcome");
			mMessageTv.setVisibility(View.VISIBLE);
			mLoginBtn.setVisibility(View.VISIBLE);
			mLogoutBtn.setVisibility(View.GONE);
		}
	}

	private void saveFacebookSession() {
		AsyncFacebookRunner fbRunner = new AsyncFacebookRunner(mFacebook);
		fbRunner.request("me", new RequestListener() {
			public void onComplete(String response, Object state) {
				try {
					JSONObject object = new JSONObject(response);
					String facebookId = object.getString("id");
					String name = object.getString("name");
					
					final Session session = new Session(mFacebook, facebookId, name);
					session.save(getApplicationContext());
					//
					// Save the new account in the DB
					DataBaseWrapper.createOrUpdateAccount(getApplicationContext(),
							facebookId, name, mFacebook.getAccessToken(),
							mFacebook.getAccessExpires());

					setResult(RESULT_OK);

					startActivity(new Intent(getApplicationContext(),
							DatesSelectionActivity.class));

					// finish();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			public void onFacebookError(FacebookError e, Object state) {
				e.printStackTrace();
			}

			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
				e.printStackTrace();
			}

			public void onIOException(IOException e, Object state) {
				e.printStackTrace();
			}

			public void onMalformedURLException(MalformedURLException e,
					Object state) {
				e.printStackTrace();
			}
		});
	}

}
