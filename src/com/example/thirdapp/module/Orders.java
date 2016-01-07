package com.example.thirdapp.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/8/1.
 * 订单
 *   "": "1",
 "": "22",
 "": "鱼香肉丝饭",
 "": "6",
 "": "13181038186",
 "": "",
 "": "22",
 "": "0",
 "": "10000.00",
 "": null,
 "": "12",
 "": null,
 "": "admin",
 "": "厦门市思明区",
 "": "0",
 "": "1",
 "": "1434297600"
 */
public class Orders implements Serializable{
    private String order_id;
    private String product_name;
    private String uid;
    private String product_id;
    private String user_name;
    private String shop_name;
    private String product_type;
    private String total;
    private String price;
    private String address;
    private String dateline;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    //    private List<GoodsDat> goodsData;


}
