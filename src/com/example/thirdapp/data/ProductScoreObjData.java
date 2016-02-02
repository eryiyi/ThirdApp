package com.example.thirdapp.data;

import com.example.thirdapp.module.ProductScoreObj;

import java.util.List;

/**
 * Created by Administrator on 2016/2/1.
 */
public class ProductScoreObjData extends Data {
    private List<ProductScoreObj> data;

    public List<ProductScoreObj> getData() {
        return data;
    }

    public void setData(List<ProductScoreObj> data) {
        this.data = data;
    }
}
