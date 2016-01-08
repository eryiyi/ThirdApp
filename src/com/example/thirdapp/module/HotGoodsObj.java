package com.example.thirdapp.module;

import java.util.List;

/**
 * "": "34",
 "product_name": "热门的手机",
 "community_id": "1",
 "shop_id": "31",
 "type_id": "28",
 "content": null,
 "product_pic": "/Uploads/2015-11-22/5651376ad87e2.png",
 "product_pic3": null,
 "product_pic2": null,
 "product_pic1": null,
 "buy_numbers": "0",
 "is_discount": "1",
 "is_tuangou": null,
 "price_tuangou": "8.00",
 "price": "10.00",
 "info": "<p><span style=\"color: rgb(51, 51, 51); font-family: &#39;Microsoft Jhenghei&#39;, Verdana, Arial, PMingLiU, sans-serif; font-size: 18px; line-height: 30.6px; background-color: rgb(255, 255, 255);\">為了挽救公司，黑莓(BlackBerry)在擁有自家手機作業系統的前提之下，還是推出了一款Android手機，期待收到奇效，顯見Android平台的魅力。不過，就算如此，還是有廠商反其道而行，不願在Android手機的紅海中競爭，寧願開拓Windows 10 Mobile手機這塊有潛力的藍海。</span></p>",
 "discount": "0",
 "tuangou_numbers": null,
 "unit": "个",
 "dateline": "1448294775",
 "is_open": "1",
 "release_state": "1",
 "delivery_type": "3",
 "pay_type": "1",
 "origin": "广东",
 "inventory": "1000",
 "sale_num": "6",
 "comment_data": [
 {
 "id": "227",
 "product_id": "34",
 "user_name": "13181038186",
 "content": "事发地时",
 "register_date": "1449283699",
 "update_date": "0",
 "is_del": "0"
 }
 ]
 * */
public class HotGoodsObj {
    private String product_id;
    private String product_name;
    private String community_id;
    private String shop_id;
    private String type_id;
    private String content;
    private String product_pic;
    private String product_pic3;
    private String product_pic2;
    private String product_pic1;
    private String buy_numbers;
    private String is_discount;
    private String is_tuangou;
    private String price_tuangou;
    private String price;
    private String info;
    private String discount;
    private String tuangou_numbers;
    private String unit;
    private String dateline;
    private String is_open;
    private String release_state;
    private String delivery_type;
    private String pay_type;
    private String origin;
    private String inventory;
    private String sale_num;
    //------------------
    private String type_name;
    private String product_fid;
    private String category_id;
    private String uid;
    private String shop_name;
    private String logo;
    private String business_time;
    private String mobile;
    private String phone;
    private String address;
    private String lng;
    private String lat;
    private String product_numbers;
    private String tuijian_numbers;
    private String call_numbers;
    private String shop_type_id;
    private String community_name;
    private String area;
    private String province_id;
    private String city_id;
    private String area_id;
    private List<CommentObj> comment_data;

    public List<CommentObj> getComment_data() {
        return comment_data;
    }

    public void setComment_data(List<CommentObj> comment_data) {
        this.comment_data = comment_data;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(String product_pic) {
        this.product_pic = product_pic;
    }

    public String getProduct_pic3() {
        return product_pic3;
    }

    public void setProduct_pic3(String product_pic3) {
        this.product_pic3 = product_pic3;
    }

    public String getProduct_pic2() {
        return product_pic2;
    }

    public void setProduct_pic2(String product_pic2) {
        this.product_pic2 = product_pic2;
    }

    public String getProduct_pic1() {
        return product_pic1;
    }

    public void setProduct_pic1(String product_pic1) {
        this.product_pic1 = product_pic1;
    }

    public String getBuy_numbers() {
        return buy_numbers;
    }

    public void setBuy_numbers(String buy_numbers) {
        this.buy_numbers = buy_numbers;
    }

    public String getIs_discount() {
        return is_discount;
    }

    public void setIs_discount(String is_discount) {
        this.is_discount = is_discount;
    }

    public String getIs_tuangou() {
        return is_tuangou;
    }

    public void setIs_tuangou(String is_tuangou) {
        this.is_tuangou = is_tuangou;
    }

    public String getPrice_tuangou() {
        return price_tuangou;
    }

    public void setPrice_tuangou(String price_tuangou) {
        this.price_tuangou = price_tuangou;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTuangou_numbers() {
        return tuangou_numbers;
    }

    public void setTuangou_numbers(String tuangou_numbers) {
        this.tuangou_numbers = tuangou_numbers;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getRelease_state() {
        return release_state;
    }

    public void setRelease_state(String release_state) {
        this.release_state = release_state;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getProduct_fid() {
        return product_fid;
    }

    public void setProduct_fid(String product_fid) {
        this.product_fid = product_fid;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBusiness_time() {
        return business_time;
    }

    public void setBusiness_time(String business_time) {
        this.business_time = business_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getProduct_numbers() {
        return product_numbers;
    }

    public void setProduct_numbers(String product_numbers) {
        this.product_numbers = product_numbers;
    }

    public String getTuijian_numbers() {
        return tuijian_numbers;
    }

    public void setTuijian_numbers(String tuijian_numbers) {
        this.tuijian_numbers = tuijian_numbers;
    }

    public String getCall_numbers() {
        return call_numbers;
    }

    public void setCall_numbers(String call_numbers) {
        this.call_numbers = call_numbers;
    }

    public String getShop_type_id() {
        return shop_type_id;
    }

    public void setShop_type_id(String shop_type_id) {
        this.shop_type_id = shop_type_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }
}
