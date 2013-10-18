package com.mogal.prbirthdays.lists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mogal.prbirthdays.R;
import com.mogal.prbirthdays.model.BaseModel;
import com.mogal.prbirthdays.model.BirthdayUser;
import com.mogal.prbirthdays.utils.FontTypeFaceManager;
import com.mogal.prbirthdays.utils.ImageLoadingUtil;
import com.mogal.prbirthdays.utils.FontTypeFaceManager.CustomFonts;

public class BirthdayUserAdapter extends BaseModelAdapter {

	static class ViewHolder {
		TextView tvName;
		TextView tvBirthday;
		ImageView imgProfilePic;
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
	public BirthdayUserAdapter(BaseModel[] data, Context context,
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
			holder.tvName = (TextView) convertView
					.findViewById(R.id.tvUserName);
			holder.tvBirthday = (TextView) convertView
					.findViewById(R.id.tvUserBirthday);
			holder.imgProfilePic = (ImageView) convertView
					.findViewById(R.id.imgProfilePic);

			// set custom fonts
			FontTypeFaceManager ftfm = FontTypeFaceManager.getInstance(context);
			ftfm.setFont(holder.tvName, CustomFonts.RobotoLight);
			ftfm.setFont(holder.tvBirthday, CustomFonts.RobotoThin);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// populate the item with values
		BirthdayUser birthdayUser = (BirthdayUser) model;
		holder.tvName.setText(birthdayUser.getName());
		ImageLoadingUtil.getInstance(context).loadImage(
				birthdayUser.getProfilePicUrl(), holder.imgProfilePic);
		// convert the MM/dd format to dd/MM
		String[] parts = birthdayUser.getBirthday().split("/");
		holder.tvBirthday.setText(parts[1] + "/" + parts[0]);

		return convertView;
	}
}
