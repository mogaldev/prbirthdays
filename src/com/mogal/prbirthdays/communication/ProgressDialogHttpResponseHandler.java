package com.mogal.prbirthdays.communication;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class ProgressDialogHttpResponseHandler extends AsyncHttpResponseHandler {
	private ProgressDialog prgrsDialog;
	private Context context;

	public ProgressDialogHttpResponseHandler(Context context) {
		this.context = context;
	}

	@Override
	public void onFinish() {
		super.onFinish();
		// stop the progress dialog
		prgrsDialog.dismiss();
	}

	@Override
	public void onStart() {
		super.onStart();
		// start the progress dialog
		prgrsDialog = new ProgressDialog(context);
		prgrsDialog.setMessage("Loading...");
		prgrsDialog.show();

	}


}
