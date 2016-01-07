package com.example.thirdapp.data;



import com.example.thirdapp.module.ShoppingAddress;

import java.util.List;

public class ShoppingAddressDATA extends Data {
    private List<ShoppingAddress> data;

    public List<ShoppingAddress> getData() {
        return data;
    }

    public void setData(List<ShoppingAddress> data) {
        this.data = data;
    }
}
