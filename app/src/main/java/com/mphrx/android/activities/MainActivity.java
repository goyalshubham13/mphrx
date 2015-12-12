package com.mphrx.android.activities;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.mphrx.android.R;
import com.mphrx.android.constants.BundleKeys;
import com.mphrx.android.constants.Constants;
import com.mphrx.android.listeners.HttpDataListener;
import com.mphrx.android.network.HttpDataClient;
import com.mphrx.android.vo.GoVO;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * Main activity with go btn
 */
public class MainActivity extends ParentActivity {

    private HttpDataClient<GoVO[]> getGoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * Click handler for go btn
     */
    @OnClick(R.id.goButton)
    public void onGoClick () {
        getGoDataClient = getHttpClient(GoVO[].class);
        getGoDataClient.setOnHttpDataListener(onGoData);
        getGoDataClient.execute(Constants.URL.GoUrl);
    }

    /**
     * network request call back
     */
    private HttpDataListener<GoVO[]> onGoData = new HttpDataListener<GoVO[]>() {
        @Override
        public void onHttpData(GoVO[] data) {
            Bundle goBundle = new Bundle();
            goBundle.putSerializable(BundleKeys.GO_DATA_BUNDLE, new ArrayList<>(Arrays.asList(data)));
            startActivity(ListActivity.class, goBundle);
        }

        @Override
        public void onHttpError(VolleyError error) {
            onGoClick ();
        }
    };
}
