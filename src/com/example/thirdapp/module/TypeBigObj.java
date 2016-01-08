package com.example.thirdapp.module;

import java.util.List;

/**
 * Created by Administrator on 2016/1/8.
 */
public class TypeBigObj {
    private String type_id;
    private String type_name;
    private String shop_id;
    private String product_fid;
    private String community_id;
    private String image;

    private List<TypeObj> type_data;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getProduct_fid() {
        return product_fid;
    }

    public void setProduct_fid(String product_fid) {
        this.product_fid = product_fid;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<TypeObj> getType_data() {
        return type_data;
    }

    public void setType_data(List<TypeObj> type_data) {
        this.type_data = type_data;
    }
}
