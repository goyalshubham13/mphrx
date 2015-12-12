package com.mphrx.android.network;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * gson request uses volley library for network call
 * uses generic way to return result data in desired value object
 */
public class GsonRequest<T> extends Request<T> {
	
	private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Listener<T> listener;
    private HashMap<String, String> headers = new HashMap<String, String>();
    private HashMap<String, String> params = new HashMap<String, String>();

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers9
     */
    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.listener = listener;
    }
    
    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
    
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}
	
	public void addHeaders (HashMap<String, String> headers) {
		this.headers = headers;
	}

	@Override
	public HashMap<String, String> getParams() {
        System.out.println(this.params);return params;
	}
	public void addParams(HashMap<String, String> params) {
		this.params = params;
	}
}