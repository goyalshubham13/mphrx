package com.mphrx.android.listeners;

import com.android.volley.VolleyError;

/**
 * Created by Shubham Goyal on 12-12-2015.
 * event handler for http client
 */
public interface HttpDataListener<T> {
	public void onHttpData(T data);
	public void onHttpError(VolleyError error);
}
