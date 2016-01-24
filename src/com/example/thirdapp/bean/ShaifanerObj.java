package com.example.thirdapp.bean;

/**
 * Created by Administrator on 2016/1/24.
 "user_name": "13266816551",
 "cover": "/Uploads/2014-10-09/5436ac5e9d7dd.jpg",
 "nick_name": "MarksChan",
 "count": "2",
 "image_str": "/Uploads/2014-10-10/5438042fc1e83.jpg,/Uploads/2016-01-24/56a4af5207c42.jpg"

 */
public class ShaifanerObj {
    private String id;
    private String count;
    private String cover;
    private String user_id;
    private String image;
    private String register_date;
    private String user_name;
    private String nick_name;
    private String image_str;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImage_str() {
        return image_str;
    }

    public void setImage_str(String image_str) {
        this.image_str = image_str;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
