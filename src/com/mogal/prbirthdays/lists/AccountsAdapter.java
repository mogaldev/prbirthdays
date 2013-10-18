package com.mogal.prbirthdays.lists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mogal.prbirthdays.R;
import com.mogal.prbirthdays.model.Account;
import com.mogal.prbirthdays.model.BaseModel;
import com.mogal.prbirthdays.utils.FontTypeFaceManager;
import com.mogal.prbirthdays.utils.FontTypeFaceManager.CustomFonts;
import com.mogal.prbirthdays.utils.ImageLoadingUtil;

public class AccountsAdapter extends BaseModelAdapter {

	static class ViewHolder {
		TextView tvAccountName;
		TextView tvAccountExpireTime;
		ImageView imgAccountProfilePic;
	}

	/**
	 * Construct the model adapter
	 * 
	 * @param data
	 *            - the records to be displayed
	 * @param context
	 *            - context for inflating and font managing
	 * @param resIdLayoutItem
	 *            - the resource id of the layout represents a single record
	 */
	public AccountsAdapter(BaseModel[] data, Context context,
			int resIdLayoutItem) {
		super(data, context);
		// set the single item layout
		super.resIdLayoutItem = resIdLayoutItem;
	}

	/**
	 * Getting a view that represents the model. if necessary inflates XML file,
	 * set the suitable fields and returns view that is ready to be displayed.
	 */
	@Override
	protected View getViewFromModel(BaseModel model, View convertView,
			ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(resIdLayoutItem, parent, false);
			holder = new ViewHolder();
			holder.tvAccountName = (TextView) convertView
					.findViewById(R.id.tvAccountName);
			holder.tvAccountExpireTime = (TextView) convertView
					.findViewById(R.id.tvAccountExpireTime);
			holder.imgAccountProfilePic = (ImageView) convertView
					.findViewById(R.id.imgAccountProfilePic);

			// set custom fonts
			FontTypeFaceManager ftfm = FontTypeFaceManager.getInstance(context);
			ftfm.setFont(holder.tvAccountName, CustomFonts.RobotoLight);
			ftfm.setFont(holder.tvAccountExpireTime, CustomFonts.RobotoThin);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// populate the item with values
		Account account = (Account) model;
		holder.tvAccountName.setText(account.getmName());
		// TODO add member to the account to hold the profile pic
		ImageLoadingUtil
				.getInstance(context)
				.loadImage(
						"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/369220_1179961901_2065757134_n.jpg",
						holder.imgAccountProfilePic);

		return convertView;
	}
}
