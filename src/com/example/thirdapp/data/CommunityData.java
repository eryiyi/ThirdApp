package com.example.thirdapp.data;


import com.example.thirdapp.module.Community;

import java.util.List;

/**
 * Created by Administrator on 2015/9/2.
 */
public class CommunityData extends Data {
    private List<Community> data;

    public List<Community> getData() {
        return data;
    }

    public void setData(List<Community> data) {
        this.data = data;
    }
}
