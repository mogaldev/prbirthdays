package com.mogal.prbirthdays.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mogal.prbirthdays.R;
import com.mogal.prbirthdays.model.Account;
import com.mogal.prbirthdays.util.FontTypeFaceManager;
import com.mogal.prbirthdays.util.FontTypeFaceManager.CustomFonts;
import com.mogal.prbirthdays.util.ImageLoadingUtil;

public class AccountsAdapter extends ArrayAdapter<Account> {
	private final Context context;
	private final Account[] values;

	public AccountsAdapter(Context context, Account[] values) {
		super(context, R.layout.account_list_item, values); // TODO: check the
															// R.layout
															// parameter that
															// was passed
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.account_list_item, parent,
				false);
		TextView tvName = (TextView) rowView.findViewById(R.id.tvAccountName);
		TextView tvExpireTime = (TextView) rowView
				.findViewById(R.id.tvAccountExpireTime);
		ImageView imgProfilePic = (ImageView) rowView
				.findViewById(R.id.imgAccountProfilePic);

		// change the fonts
		FontTypeFaceManager ftfm = FontTypeFaceManager.getInstance(context);
		ftfm.setFont(tvName, CustomFonts.RobotoLight);
		ftfm.setFont(tvExpireTime, CustomFonts.RobotoThin);

		// populate the values in the fields
		Account account = values[position];

		tvName.setText(account.getmName());
		tvExpireTime.setText("Expire At: " + new SimpleDateFormat("dd/MM/yyyy")
				.format(new Date(account.getmAccessExpires())));
		ImageLoadingUtil
				.getInstance(getContext())
				.loadImage(
						"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/369220_1179961901_2065757134_n.jpg",
						imgProfilePic);

		return rowView;
	}


}