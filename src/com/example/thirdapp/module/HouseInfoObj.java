package com.example.thirdapp.module;

import java.io.Serializable;

/**
 *  "id": "1",
 "title": "标题",
 "price": "",
 "pic1": "",
 "pic2": "",
 "pic3": "",
 "pic4": "",
 "pic5": "",
 "pic6": "",
 "pic7": "",
 "lng": null,
 "lat": null,
 "room_cat": "1",
 "city": "城市",
 "living_room": "客厅",
 "rent_price": "租金",
 "room_from": "来源",
 "decoration": "装修",
 "floor": "楼称",
 "limits": "限制",
 "area": "面积",
 "general": "概况",
 "bedroom": "卧室",
 "user_name": "13266816551",
 "community_id": "1",
 "register_date": "0",
 "update_date": "0",
 "is_del": "0",
 "view_num": "0",
 "address": "",
 "isKuanDai": "0",
 "isDianShi": "0",
 "isShaFa": "0",
 "isXiYiJi": "0",
 "isBed": "0",
 "isBingXiang": "0",
 "isKongTiao": "0",
 "isNuanQi": "0",
 "isYiGui": "0",
 "isReShui": "0",
 "detail": "",
 "kaopu_num": "-1",
 "manyi_num": "0",
 "buxing_num": "0",
 "chaping_num": "0",
 "nick_name": "昵称3",
 "community_name": "龙湖西苑"
 */
public class HouseInfoObj  implements Serializable{
    private String id;
    private String title;
    private String price;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;
    private String pic5;
    private String pic6;
    private String pic7;
    private String lng;
    private String lat;
    private String room_cat;
    private String city;
    private String living_room;
    private String rent_price;
    private String room_from;
    private String decoration;
    private String floor;
    private String limits;
    private String area;
    private String general;
    private String bedroom;
    private String user_name;
    private String community_id;
    private String register_date;
    private String update_date;
    private String nick_name;
    private String community_name;
    private String detail;
    private String isReShui;
    private String isYiGui;
    private String isNuanQi;
    private String isKongTiao;
    private String isBingXiang;
    private String isXiYiJi;
    private String isShaFa;
    private String isBed;
    private String isDianShi;
    private String isKuanDai;
    private String is_del;
    private String address;
    private String view_num;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIsReShui() {
        return isReShui;
    }

    public void setIsReShui(String isReShui) {
        this.isReShui = isReShui;
    }

    public String getIsYiGui() {
        return isYiGui;
    }

    public void setIsYiGui(String isYiGui) {
        this.isYiGui = isYiGui;
    }

    public String getIsNuanQi() {
        return isNuanQi;
    }

    public void setIsNuanQi(String isNuanQi) {
        this.isNuanQi = isNuanQi;
    }

    public String getIsKongTiao() {
        return isKongTiao;
    }

    public void setIsKongTiao(String isKongTiao) {
        this.isKongTiao = isKongTiao;
    }

    public String getIsBingXiang() {
        return isBingXiang;
    }

    public void setIsBingXiang(String isBingXiang) {
        this.isBingXiang = isBingXiang;
    }

    public String getIsXiYiJi() {
        return isXiYiJi;
    }

    public void setIsXiYiJi(String isXiYiJi) {
        this.isXiYiJi = isXiYiJi;
    }

    public String getIsShaFa() {
        return isShaFa;
    }

    public void setIsShaFa(String isShaFa) {
        this.isShaFa = isShaFa;
    }

    public String getIsBed() {
        return isBed;
    }

    public void setIsBed(String isBed) {
        this.isBed = isBed;
    }

    public String getIsDianShi() {
        return isDianShi;
    }

    public void setIsDianShi(String isDianShi) {
        this.isDianShi = isDianShi;
    }

    public String getIsKuanDai() {
        return isKuanDai;
    }

    public void setIsKuanDai(String isKuanDai) {
        this.isKuanDai = isKuanDai;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    public String getPic6() {
        return pic6;
    }

    public void setPic6(String pic6) {
        this.pic6 = pic6;
    }

    public String getPic7() {
        return pic7;
    }

    public void setPic7(String pic7) {
        this.pic7 = pic7;
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

    public String getRoom_cat() {
        return room_cat;
    }

    public void setRoom_cat(String room_cat) {
        this.room_cat = room_cat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLiving_room() {
        return living_room;
    }

    public void setLiving_room(String living_room) {
        this.living_room = living_room;
    }

    public String getRent_price() {
        return rent_price;
    }

    public void setRent_price(String rent_price) {
        this.rent_price = rent_price;
    }

    public String getRoom_from() {
        return room_from;
    }

    public void setRoom_from(String room_from) {
        this.room_from = room_from;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }
}
