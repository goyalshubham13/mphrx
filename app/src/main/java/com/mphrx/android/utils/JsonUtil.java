package com.mphrx.android.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * json utility class for shortcut methods to and from conversion
 */
public class JsonUtil {
	
	private JsonUtil () {}
	private static Gson gson;
	private static JsonUtil instance;
	
	public static JsonUtil getInstance() {
		if(instance == null) {
			instance = new JsonUtil();
		}
		instance.gson = new GsonBuilder().setDateFormat("MMM dd yyyy HH:mm").create();
		return instance;
	}
	public String toJson(Object src) {
		return gson.toJson(src);
	}
	public <T> T fromJson (String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
	public <T> T listFromJson (String json, Type listType) {
		return gson.fromJson(json, listType);
	}
}
