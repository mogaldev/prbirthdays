package com.mogal.prbirthdays.model;

import org.json.JSONException;
import org.json.JSONObject;

public class BirthdayUser {
	
	long uid;
	String name;
	String birthday_date;
	String pic_sqaure;
	
	
	public BirthdayUser(JSONObject jsonObj) throws JSONException {
		uid = jsonObj.getLong("uid");
		name = jsonObj.getString("name");
		birthday_date = jsonObj.getString("anon"); //birthday_date
		pic_sqaure = jsonObj.getString("pic_square");		
	}
	
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBirthday() {
		return birthday_date;
	}


	public void setBirthday(String birthday_date) {
		this.birthday_date = birthday_date;
	}


	public String getProfilePicUrl() {
		return pic_sqaure;
	}


	public void setProfilePicUrl(String pic_sqaure) {
		this.pic_sqaure = pic_sqaure;
	}

	
	/*
	 * Example:
      "uid": 1170949169, 
      "name": "Nathaniel Naim", 
      "anon": "05/09", 
      "pic_square": "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/1076746_1170949169_130465739_q.jpg"
	 */
	
}
