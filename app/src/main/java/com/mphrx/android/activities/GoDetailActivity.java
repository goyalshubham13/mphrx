package com.mphrx.android.activities;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mphrx.android.R;
import com.mphrx.android.constants.BundleKeys;
import com.mphrx.android.vo.GoDetailVO;
import com.mphrx.android.widgets.AutoResizeTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * Detail view activity
 */
public class GoDetailActivity extends ParentActivity implements ViewTreeObserver.OnScrollChangedListener{

    private GoDetailVO goDetailVO;

    //ui views

    // first row having fixed height and text should resize itself
    @Bind(R.id.goDetailRow1)
    AutoResizeTextView goDetailRow1;

    //dynamic height textview
    @Bind(R.id.goDetailRow2)
    TextView goDetailRow2;

    // webview with dynamic height
    @Bind(R.id.goDetailWebView)
    WebView goDetailWebView;

    @Bind(R.id.goDetailScroll)
    ScrollView goDetailScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideActionbarOnScroll();
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        setContentView(R.layout.activity_go_detail);
        ButterKnife.bind(this);
        goDetailVO = (GoDetailVO)getDataBundle().getSerializable(BundleKeys.GO_DETAIL_BUNDLE);

        goDetailWebView.getSettings().setJavaScriptEnabled(true);

        goDetailRow1.setText(goDetailVO.getDescription());
        goDetailRow2.setText(goDetailVO.getDescription());


        /**
         * registering for page finish event
         * so that we can get height of html page to set webview's height
         */
        goDetailWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //calls javascript function
                goDetailWebView.loadUrl("javascript:mphrx.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
            }
        });
        //registering for javascript interface
        goDetailWebView.addJavascriptInterface(this, "mphrx");

        // given url has iframe and I am not able to get html page height to adjust webview
        // below hardcoded url is the url given in that iframe.
        // please uncomment the line to see the webview with dynamic height
        goDetailWebView.loadUrl(goDetailVO.getWebUrl());
        //goDetailWebView.loadUrl("http://templated.co/items/demos/retrospect/");

        //adding listener for scroll event
        goDetailScroll.getViewTreeObserver().addOnScrollChangedListener(this);
    }

    private float initialY= 0;
    @Override
    public void onScrollChanged() {
        float y = goDetailScroll.getScrollY();
        if (y >= mActionBarHeight && actionBar.isShowing() && y >= initialY) {
            //hide action bar if page scroll down
            actionBar.hide();
        } else if ( !actionBar.isShowing() && y < initialY) {
            // show actionbar if page scroll ups
            actionBar.show();
        }
        initialY = y;
    }


    @JavascriptInterface
    public void resize(final float height) {
        GoDetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //get height of html page and adjust height of webview
                goDetailWebView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }
}
