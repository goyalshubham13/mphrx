package com.mphrx.android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.VolleyError;
import com.mphrx.android.R;
import com.mphrx.android.listeners.HttpDataListener;
/**
 * Created by Shubham Goyal on 12-12-2015.
 */
public class HttpDataClient<T> extends
com.mphrx.android.network.HttpDataClient<T> implements
		HttpDataListener<T> {

	private HttpDataListener<T> mListener;
	private Activity activityContext;
	public HttpDataClient(Class<T> clazz, Activity activityContext) {
		super(clazz, activityContext);
		this.activityContext = activityContext;  
	}

	@Override
	public void onHttpData(T data) {
		mListener.onHttpData(data);
	}

	/**
	 * if there is any error in case of no internet or any other server side error
	 * app shows dialog by itself to ask if user want to retry the request or exit the app
	 * @param error
	 */
	@Override
	public void onHttpError(final VolleyError error) {
		AlertDialog alertDialog = new AlertDialog.Builder(activityContext).create();
		// Setting Dialog Title
		alertDialog.setTitle(activityContext.getResources().getString(R.string.connection_error_title));
		// Setting Dialog Message
		alertDialog.setMessage(activityContext.getResources().getString(R.string.connection_error_txt));
		
		alertDialog.setCancelable(false);
		alertDialog.setCanceledOnTouchOutside(false);
		
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, activityContext.getResources().getString(R.string.retry_btn), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mListener.onHttpError(error);
			}
		});
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE , activityContext.getResources().getString(R.string.exit_btn), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				activityContext.finish();
			}
		});
		alertDialog.show();
	}

	@Override
	public void setOnHttpDataListener(HttpDataListener<T> mListener) {
		super.setOnHttpDataListener(this);
		this.mListener = mListener;
	}
}
