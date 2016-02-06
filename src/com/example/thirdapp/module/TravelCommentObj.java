package com.example.thirdapp.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/6.
 * "id": "229",
 "trip_id": "229",
 "user_name": "13266816551",
 "content": "\u521a\u521a",
 "register_date": "1454672520",
 "update_date": "0",
 "is_del": "0"
 */
public class TravelCommentObj implements Serializable{
    private String id;
    private String trip_id;
    private String user_name;
    private String content;
    private String register_date;
    private String update_date;
    private String is_del;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
