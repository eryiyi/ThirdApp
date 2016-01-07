package com.example.thirdapp.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/26.
 */
public class Photo_data_obj implements Serializable {
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
