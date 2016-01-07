package com.example.thirdapp.data;



import com.example.thirdapp.module.Orders;

import java.util.List;

public class OrdersDATA extends Data {
    private List<Orders> data;

    public List<Orders> getData() {
        return data;
    }

    public void setData(List<Orders> data) {
        this.data = data;
    }
}
