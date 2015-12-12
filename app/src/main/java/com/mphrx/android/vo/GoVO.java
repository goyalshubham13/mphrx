package com.mphrx.android.vo;

import java.io.Serializable;

/**
 * Created by Shubham Goyal on 12-12-2015.
 * value object class for list data
 */
public class GoVO implements Serializable{
    private String image;
    private String description;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
