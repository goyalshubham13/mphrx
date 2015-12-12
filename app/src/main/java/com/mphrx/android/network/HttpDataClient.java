package com.mphrx.android.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mphrx.android.R;
import com.mphrx.android.listeners.HttpDataListener;
import com.mphrx.android.utils.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * httpclient use gsonrequest class to process network request
 */
public class HttpDataClient<T> {

	private HttpDataListener<T> mListener;
	private Class<T> clazz;
	private Context appContext;
	private boolean showProgress = true;
	private HashMap<String, String> headers = new HashMap<String, String>();
	private HashMap<String, String> params = new HashMap<String, String>();
	private HashMap<String, File> files = new HashMap<String, File>();
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
	public HttpDataClient(Class<T> clazz, Context appContext) {
		this.clazz = clazz;
		this.appContext = appContext;
	}
	
	public void setOnHttpDataListener(HttpDataListener<T> mListener) {
		this.mListener = mListener;
	}

	//method to add headers
	public void addHeader (String key, String value) {
		headers.put(key, value);
	}

	// in case any file
	public void addFile (String key, File value) {
		files.put(key, value);
	}

	//params for post request
	public void addParam (String key, Object value) {
		if(value != null) {
			params.put(key, value.toString());
		}
	}

	//in case want to remove parameters
	public void removeParam (String key) {
		if(params.containsKey(key))
			params.remove(key);
	}

	// progress to show while request is in progress
	private ProgressDialog progressDialog;
	protected void showProgressDialog () {
		progressDialog = ProgressDialog.show(appContext, "", appContext.getString(R.string.progress_txt));
	}

	// dismiss the progress
	protected void dismissProgressDialog () {
		if(progressDialog != null)
			progressDialog.dismiss();
	}

	// processing get request
	public void execute(Object... params) {
		if(showProgress)
			showProgressDialog();
		RequestProcessor requestProcessor = RequestProcessor.getInstance(appContext);

		GsonRequest<T> request = new GsonRequest<T>(Request.Method.GET,
				makeHttpUrl(params), clazz, new Response.Listener<T>() {

					@Override
					public void onResponse(T response) {
						if(showProgress)
							dismissProgressDialog();
						mListener.onHttpData(response);
                        headers.clear();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						if(showProgress)
							dismissProgressDialog();
						Toast.makeText(appContext, appContext.getResources().getString(R.string.server_error_toast), Toast.LENGTH_LONG).show();
						mListener.onHttpError(error);
                        headers.clear();
					}
				});
		request.addHeaders(headers);
		requestProcessor.addToRequestQueue(request);
	}

	//processing post request
	public void executePost(Object... params) {
		if(showProgress)
			showProgressDialog();
		RequestProcessor requestProcessor = RequestProcessor.getInstance(appContext);

		GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST,
				params[0]+"", clazz, new Response.Listener<T>() {

					@Override
					public void onResponse(T response) {
						if(showProgress)
							dismissProgressDialog();
						mListener.onHttpData(response);
                        clearRequest ();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if(showProgress)
							dismissProgressDialog();

						Toast.makeText(appContext, appContext.getResources().getString(R.string.server_error_toast), Toast.LENGTH_LONG).show();
						mListener.onHttpError(error);
                        clearRequest();
                    }
				});
		request.addHeaders(headers);
		request.addParams(this.params);
		System.out.println(this.params);
		requestProcessor.addToRequestQueue(request);
	}

	//clear request
    private void clearRequest () {
        this.headers.clear();
        this.params.clear();
    }

	/**
	 * receives parameters in case of get request
	 * first param is URL
	 * other are key value pairs
	 */
	private String makeHttpUrl(Object... params) {
		if(params.length == 1)
			return params[0]+"";
		int paramLength = params.length;
		String url = params[0] + "?";
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		int i = 1;
		while (i < paramLength) {
			if (params[i + 1] != null)
				nameValuePairs.add(new BasicNameValuePair(params[i].toString(),
						params[i + 1].toString()));
			i = i + 2;
		}
		String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");

		url += paramString;
		System.out.println("url:- "+url);
		return url;
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}
}
