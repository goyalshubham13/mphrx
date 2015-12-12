package com.mphrx.android.vo;

import java.io.Serializable;
/**
 * Created by Shubham Goyal on 12-12-2015.
 * value object class for detail data
 */
public class GoDetailVO implements Serializable{
    private String webUrl;
    private String description;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
