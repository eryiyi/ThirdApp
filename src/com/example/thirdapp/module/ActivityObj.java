package com.example.thirdapp.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/7.
 "id": "226",
 "active_name": "田径运动",
 "active_rule": "短跑100米，跳远等",
 "join_time": "2月15日-2月21日",
 "active_time": "2月22日下午13：00-17：00",
 "active_place": "渝北两路阳光小区",
 "tel": "13838381438",
 "other_info": "报名需提前电话预约",
 "image": "",
 "title": "阳光小区第5届小区运动会",
 "user_name": "13266816551",
 "community_id": "1",
 "register_date": "0",
 "update_date": "0",
 "is_del": "0",
 "image2": "",
 "image3": ""
 */
public class ActivityObj implements Serializable {
    private String id;
    private String active_name;
    private String active_rule;
    private String join_time;
    private String active_time;
    private String active_place;
    private String tel;
    private String other_info;
    private String image;
    private String title;
    private String user_name;
    private String community_id;
    private String register_date;
    private String update_date;
    private String is_del;
    private String image2;
    private String image3;

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActive_name() {
        return active_name;
    }

    public void setActive_name(String active_name) {
        this.active_name = active_name;
    }

    public String getActive_rule() {
        return active_rule;
    }

    public void setActive_rule(String active_rule) {
        this.active_rule = active_rule;
    }

    public String getJoin_time() {
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
    }

    public String getActive_time() {
        return active_time;
    }

    public void setActive_time(String active_time) {
        this.active_time = active_time;
    }

    public String getActive_place() {
        return active_place;
    }

    public void setActive_place(String active_place) {
        this.active_place = active_place;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOther_info() {
        return other_info;
    }

    public void setOther_info(String other_info) {
        this.other_info = other_info;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
