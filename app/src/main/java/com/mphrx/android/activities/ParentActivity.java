package com.mphrx.android.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Window;
import android.widget.Button;

import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mphrx.android.R;
import com.mphrx.android.constants.BundleKeys;
import com.mphrx.android.constants.Constants;
import com.mphrx.android.network.HttpDataClient;
import com.mphrx.android.utils.JsonUtil;
import com.mphrx.android.utils.SharedPreferenceManager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * parent class for all activities
 */
public class ParentActivity extends AppCompatActivity {



    protected SharedPreferenceManager prefManager;
    protected JsonUtil jsonUtil;
    private Bundle dataBundle;
    protected String name;
    protected Activity self;
    protected ActionBar actionBar;
    protected float mActionBarHeight;
    private TextView actionbarView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        self = this;
        prefManager = new SharedPreferenceManager(this, Constants.PREF_NAME);
        jsonUtil = JsonUtil.getInstance();
        dataBundle = getIntent().getExtras();
        this.name = getComponentName().getShortClassName();

        setupActionBar();

        setupView();
        setupListeners();
    }

    /**
     * setup custom action bar
     */
    public void setupActionBar() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(0);
            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actionbarView = (TextView)inflater
                    .inflate(R.layout.actionbar_view, null);
            ActivityInfo activityInfo = null;
            try {
                activityInfo = getPackageManager().getActivityInfo(
                        getComponentName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {

            }
            String title = activityInfo.loadLabel(getPackageManager())
                    .toString();

            actionbarView.setText(title);

            actionBar.setCustomView(actionbarView, new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER));
            Toolbar parent = (Toolbar) actionbarView.getParent();
            parent.setContentInsetsAbsolute(0, 0);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }

    /**
     * getting actionbar height to be used for hidiing actionbar on scroll down
     */
    protected void hideActionbarOnScroll () {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        mActionBarHeight = styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
    }

    /**
     * title padding to adjust title in case action bar having menu icons
     */
    protected void titlePadding (int left, int top, int right, int bottom) {
        actionbarView.setPadding(left, top, right, bottom);
    }

    protected void setupView() {
    }

    protected void setupListeners() {

    }

    /**
     * return http client object
     */
    public <T> HttpDataClient<T> getHttpClient(Class<T> clazz) {
        return new com.mphrx.android.utils.HttpDataClient<T>(clazz, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * shortcut method to start activity with data bundle
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(BundleKeys.PARENT_ACTIVITY_BUNDLE, this.name);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * shortcut method to start activity without data bundle
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeys.PARENT_ACTIVITY_BUNDLE, this.name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * shortcut method to start activity if activity name is availabel
     */
    public void startActivity(String cls) {
        Intent intent = new Intent();
        intent.setClassName(getApplicationContext(), cls);
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeys.PARENT_ACTIVITY_BUNDLE, this.name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * shortcut method to start activity with data bundle if activity name is availabel
     */
    public void startActivity(String cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClassName(getApplicationContext(), cls);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(BundleKeys.PARENT_ACTIVITY_BUNDLE, this.name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private ProgressDialog progressDialog;

    public void showToast(String message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    public Bundle getDataBundle() {
        return dataBundle;
    }

    private AlertDialog alertDialog;

    public void alert(int titleResId, int messageResId,
                      final boolean shouldCapture) {
        alertDialog = new AlertDialog.Builder(this).create();
        // Setting Dialog Title
        alertDialog.setTitle(getString(titleResId));
        // Setting Dialog Message
        alertDialog.setMessage(getString(messageResId));
        //alertDialog.setIcon(R.drawable.ic_dialog_alert_holo_light);
        alertDialog.setIconAttribute(android.R.attr.alertDialogIcon);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources()
                        .getString(R.string.ok_btn),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        if (shouldCapture)
                            onAlertDismiss();
                    }
                });
        alertDialog.show();
    }

    public void alert(int titleResId, String message,
                      final boolean shouldCapture) {
        alertDialog = new AlertDialog.Builder(this).create();
        // Setting Dialog Title
        alertDialog.setTitle(getString(titleResId));
        // Setting Dialog Message
        alertDialog.setMessage(message);

        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources()
                        .getString(R.string.ok_btn),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        if (shouldCapture)
                            onAlertDismiss();
                    }
                });
        alertDialog.show();
    }


    public void onAlertDismiss() {

    }
}
