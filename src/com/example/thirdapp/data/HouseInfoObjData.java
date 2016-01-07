package com.example.thirdapp.data;

import com.example.thirdapp.module.HouseInfoObj;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public class HouseInfoObjData extends Data {
    private List<HouseInfoObj> data;

    public List<HouseInfoObj> getData() {
        return data;
    }

    public void setData(List<HouseInfoObj> data) {
        this.data = data;
    }
}
