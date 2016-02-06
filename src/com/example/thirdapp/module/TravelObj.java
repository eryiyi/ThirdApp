package com.example.thirdapp.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/7.
 * "id": "226",
 "id": "229",
 "title": "\u51b0\u5ddd\u98ce\u666f\u56fe",
 "content": "
 \u51b0\u5ddd\u98ce\u666f\u56fe\u7247 \u51b0\u5ddd\u98ce\u666f\u56fe\u7247 \u7011\u5e03\u5c71\u6c34\u98ce\u666f\u56fe\u7247 \u7011\u5e03\u5c71\u6c34\u98ce\u666f\u56fe\u7247 \u5c71\u6c34\u7011\u5e03\u98ce\u666f\u56fe\u7247 \u5c71\u6c34\u7011\u5e03\u98ce\u666f\u56fe\u7247 \u6d77\u8fb9\u5c0f\u5c4b\u56fe\u7247 \u6d77\u8fb9\u5c0f\u5c4b\u56fe\u7247 \u7eff\u8272<\/p>",
 "image": "\/Uploads\/2016-02-05\/56b47ebc7de1f.jpg",
 "view_num": "0",
 "comment_num": "1",
 "collect_num": "1",
 "support_num": "9",
 "type_id": "0",
 "tel": "13266816551",
 "lng": "0.000000",
 "lat": "0.000000",
 "priority": "0",
 "is_top": "0",
 "register_date": "2016-02-05 18:51:40",
 "update_date": "0",
 "is_del": "0",
 "comment_data": [
 {
 "id": "229",
 "trip_id": "229",
 "user_name": "13266816551",
 "content": "\u521a\u521a",
 "register_date": "1454672520",
 "update_date": "0",
 "is_del": "0"
 }
 ]
 */
public class TravelObj implements Serializable {
    private String id;
    private String title;
    private String content;
    private String image;
    private String view_num;
    private String comment_num;
    private String collect_num;
    private String support_num;
    private String type_id;
    private String tel;
    private String lng;
    private String lat;
    private String priority;
    private String is_top;
    private String register_date;
    private String update_date;
    private String is_del;

    private List<TravelCommentObj> comment_data;

    public List<TravelCommentObj> getComment_data() {
        return comment_data;
    }

    public void setComment_data(List<TravelCommentObj> comment_data) {
        this.comment_data = comment_data;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }

    public String getSupport_num() {
        return support_num;
    }

    public void setSupport_num(String support_num) {
        this.support_num = support_num;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
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
