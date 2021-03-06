package com.mphrx.android.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.mphrx.android.R;
import com.mphrx.android.adapters.GridAdapter;
import com.mphrx.android.constants.BundleKeys;
import com.mphrx.android.constants.Constants;
import com.mphrx.android.datasources.GoDetailDataSource;
import com.mphrx.android.listeners.HttpDataListener;
import com.mphrx.android.network.HttpDataClient;
import com.mphrx.android.utils.Utils;
import com.mphrx.android.vo.GoDetailVO;
import com.mphrx.android.vo.GoVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * Activity to show data in gridview
 */
public class GridActivity extends ParentActivity {
    private GridAdapter gridAdapter;
    private List<GoVO> goData;
    private GoDetailDataSource goDetailDataSource;

    private HttpDataClient<GoDetailVO[]> getGoDetailClient;
    private int selectedIndex = -1;
    @Bind(R.id.gridView)
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);
        goDetailDataSource = new GoDetailDataSource(this);
        goDetailDataSource.open();
        titlePadding(Utils.convertDpToPixel(Constants.deviceDensityFactor, 24), 0, 0, 0);
        gridAdapter = new GridAdapter(this);
        gridView.setOnItemClickListener(onListItemClick);
        goData = (List<GoVO>)getDataBundle().getSerializable(BundleKeys.GO_DATA_BUNDLE);
        gridView.setAdapter(gridAdapter);

        gridAdapter.setGridData(goData);
    }

    /**
     * navigate to detail activity
     *
     */
    private void showDetails (GoDetailVO goDetailVO) {
        Bundle goDetailBundle = new Bundle();
        goDetailBundle.putSerializable(BundleKeys.GO_DETAIL_BUNDLE, goDetailVO);
        startActivity(GoDetailActivity.class, goDetailBundle);
    }
    /**
     * list item click handler to select the data
     */
    private AdapterView.OnItemClickListener onListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedIndex = position;
            //first check if data is availabel in our local db
            GoDetailVO goDetailVO = goDetailDataSource.getGoDetail(selectedIndex);
            // if no request data from server
            if (goDetailVO == null) {
                fetchGoDetail (selectedIndex);
            } else {
                // if yes directly navigate to detail
                showDetails (goDetailVO);
            }
        }
    };
    /**
     * request data based on index
     * @param index
     */
    private void fetchGoDetail (int index) {
        if (getGoDetailClient == null) {
            getGoDetailClient = getHttpClient(GoDetailVO[].class);
        }
        getGoDetailClient.setOnHttpDataListener(onGoDetail);

        if (index%2 == 0) {
            //even
            getGoDetailClient.execute(Constants.URL.DetailUrl_1);
        } else {
            //odd
            getGoDetailClient.execute(Constants.URL.DetailUrl_2);
        }
    }
    /**
     * receive data and navigate to detail
     */
    private HttpDataListener<GoDetailVO[]> onGoDetail = new HttpDataListener<GoDetailVO[]>() {
        @Override
        public void onHttpData(GoDetailVO[] data) {
            if (selectedIndex != -1) {
                goDetailDataSource.insertDetail(selectedIndex, data[0]);
                showDetails (data[0]);
            }
        }

        @Override
        public void onHttpError(VolleyError error) {
            fetchGoDetail(selectedIndex);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.overflow_menu_at_grid_icon:
                //switch to list view
                startActivity(ListActivity.class, getDataBundle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
