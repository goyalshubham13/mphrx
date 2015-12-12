package com.mphrx.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * utility class to save and get values form shared prefrences
 */
public class SharedPreferenceManager {
	SharedPreferences pref;
	
	public SharedPreferenceManager(Context mcontext, String prefName) {
		pref = mcontext.getSharedPreferences(prefName, 0);
	}
	
	public String getStringData(String key) {
		return pref.getString(key, "");
	}
	
	public int getIntData(String key) {
		return pref.getInt(key, 0);
	}
	
	public boolean getBooleanData(String key) {
		return pref.getBoolean(key, false);
	}
	
	public void setStringData(String key, String value) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public void setIntData(String key, int value) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void setBooleanData(String key, boolean value) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
		
	public void clearPreferences() {
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	public boolean contains(String key){
		if(pref.contains(key)){
			return true;
		}else{
			return false;
		}
	}
}
