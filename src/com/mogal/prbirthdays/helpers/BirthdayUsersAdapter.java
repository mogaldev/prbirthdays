package com.mogal.prbirthdays.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mogal.prbirthdays.R;
import com.mogal.prbirthdays.model.BirthdayUser;
import com.mogal.prbirthdays.util.FontTypeFaceManager;
import com.mogal.prbirthdays.util.ImageLoadingUtil;
import com.mogal.prbirthdays.util.FontTypeFaceManager.CustomFonts;

public class BirthdayUsersAdapter extends ArrayAdapter<BirthdayUser> {
	private final Context context;
	private final BirthdayUser[] values;

	public BirthdayUsersAdapter(Context context, BirthdayUser[] values) {
		super(context, R.layout.birthday_user_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.birthday_user_item, parent,
				false);
		TextView tvName = (TextView) rowView.findViewById(R.id.tvUserName);
		TextView tvBirthday = (TextView) rowView
				.findViewById(R.id.tvUserBirthday);
		ImageView imgProfilePic = (ImageView) rowView
				.findViewById(R.id.imgProfilePic);

		// change the fonts
		FontTypeFaceManager ftfm = FontTypeFaceManager.getInstance(context);
		ftfm.setFont(tvName, CustomFonts.RobotoLight);
		ftfm.setFont(tvBirthday, CustomFonts.RobotoThin);
		
		// populate the values in the fields
		BirthdayUser birthdayUser = values[position];

		tvName.setText(birthdayUser.getName());
		tvBirthday.setText(reverseDateFormat(birthdayUser.getBirthday()));
		ImageLoadingUtil.getInstance(getContext()).loadImage(
				birthdayUser.getProfilePicUrl(), imgProfilePic);

		return rowView;
	}

	// convert MM/dd to dd/MM
	private String reverseDateFormat(String dateMonthFirst) {
		String[] parts = dateMonthFirst.split("/");
		return parts[1] + "/" + parts[0];
	}

}