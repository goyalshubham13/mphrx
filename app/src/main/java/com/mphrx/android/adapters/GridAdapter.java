package com.mphrx.android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mphrx.android.R;
import com.mphrx.android.network.RequestProcessor;
import com.mphrx.android.vo.GoVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Goyal on 12-12-2015.
 * adapter for gridview
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoVO> govoData = new ArrayList<>();
    private ImageLoader mImageLoader;

    public GridAdapter(Context c) {
        mContext = c;
        mImageLoader = RequestProcessor.getInstance(c).getImageLoader();
    }

    public List<GoVO> getGridData() {
        return this.govoData;
    }

    public void setGridData(List<GoVO> govoData) {
        this.govoData = govoData;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.govoData.size();
    }

    public Object getItem(int position) {
        return this.govoData.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final GoVO goData = this.govoData.get(position);
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_layout, null);

            holder = new ViewHolder();

            holder.gridItemImageView = (NetworkImageView) convertView.findViewById(R.id.gridItemImageView);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();

        holder.gridItemImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.gridItemImageView.setImageUrl(goData.getImage(), mImageLoader);
        return convertView;
    }

    // view holder
    private static class ViewHolder {
        NetworkImageView gridItemImageView;
    }
}
