package com.mphrx.android.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mphrx.android.R;
import com.mphrx.android.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * Launcher activity to display the logo for 2 seconds
 */
public class SplashActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /**
         * Setting app context to use in other classes where app context can not be directly available
         */
        Utils.setContext(getApplicationContext());
        /**
         * Initializing the device dimensions and density factor in case we need them for ex. for converting dp to pixel
         */
        Utils.initializeScreenDimensions();

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
            }
        }, 2000);

    }

}
