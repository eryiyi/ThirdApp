package com.example.thirdapp.data;

import com.example.thirdapp.module.ActivityObj;

import java.util.List;

/**
 * Created by Administrator on 2015/11/7.
 */
public class ActivityObjData extends Data {
    private List<ActivityObj> data;

    public List<ActivityObj> getData() {
        return data;
    }

    public void setData(List<ActivityObj> data) {
        this.data = data;
    }
}
