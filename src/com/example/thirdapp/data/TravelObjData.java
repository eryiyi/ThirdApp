package com.example.thirdapp.data;

import com.example.thirdapp.module.TravelObj;

import java.util.List;

/**
 * Created by Administrator on 2015/11/7.
 */
public class TravelObjData extends Data {
    private List<TravelObj> data;

    public List<TravelObj> getData() {
        return data;
    }

    public void setData(List<TravelObj> data) {
        this.data = data;
    }
}
