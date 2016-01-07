package com.example.thirdapp.data;


import com.example.thirdapp.module.Order;

/**
 * 短信验证码
 */
public class OrderDATA extends Data {
    private Order data;

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }
}
