package com.example.thirdapp.data;

import com.example.thirdapp.module.OrderMine;

import java.util.List;

/**
 * Created by Administrator on 2015/12/19.
 */
public class OrderMineData extends Data {
    private List<OrderMine> data;

    public List<OrderMine> getData() {
        return data;
    }

    public void setData(List<OrderMine> data) {
        this.data = data;
    }
}
