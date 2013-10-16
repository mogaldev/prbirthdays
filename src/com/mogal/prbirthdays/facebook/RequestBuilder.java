package com.mogal.prbirthdays.facebook;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class RequestBuilder {

	public static Date dateFrom = new Date();
	public static Date dateTo = new Date();

	@SuppressWarnings("deprecation")
	private static String getQuery() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		String query = String
				.format("SELECT uid, name, substr(birthday_date, 0,5), pic_square "
						+ "FROM user "
						+ "WHERE uid IN (SELECT uid2 FROM friend WHERE uid1=me()) "
						+ "AND birthday_date >= \"%s\" AND birthday_date <= \"%s\" "
						+ "ORDER BY birthday_date ASC", sdf.format(dateFrom),
						sdf.format(dateTo));

		Log.v("query", query);
		return URLEncoder.encode(query);
	}

	public static String getRequestUrl(String accessToken) {
		String queryUrl = "https://graph.facebook.com/fql?q=" + getQuery()
				+ "&access_token=" + accessToken;

		Log.v("query", queryUrl);
		return queryUrl;
	}
	
	public static String shared_access_token = "X";
	public static String getRequestUrl() {
		Log.e("access_token", shared_access_token);
		return getRequestUrl(shared_access_token);
	}

}
