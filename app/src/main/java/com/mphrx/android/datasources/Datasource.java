package com.mphrx.android.datasources;

import android.app.ProgressDialog;
import android.content.Context;

import com.mphrx.android.R;
import com.mphrx.android.utils.Utils;

/**
 * Created by Shubham Goyal on 12-12-2015.
 * parent datasource class
 */
public class Datasource {
    private ProgressDialog progressDialog;
    private Context context;
    public Datasource (Context context) {
        this.context = context;
    }

    protected void showProgressDialog () {
        progressDialog = ProgressDialog.show(context, "", context.getString(R.string.progress_txt));
    }

    protected void dismissProgressDialog () {
        if(progressDialog != null)
            progressDialog.dismiss();
    }
}
