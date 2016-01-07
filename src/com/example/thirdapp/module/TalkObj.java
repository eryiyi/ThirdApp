package com.example.thirdapp.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/23.
 *  "id": "2",
 "images": "",
 "title": "艾弗森",
 "content": "发烧反复",
 "hit": "0",
 "user_name": "13181038186",
 "cat_id": "6",
 "register_date": "1448293367",
 "update_date": "0",
 "is_del": "0"
 */
public class TalkObj implements Serializable {
    private String id;
    private String images;
    private String title;
    private String content;
    private String hit;
    private String user_name;
    private String cat_id;
    private String register_date;
    private String update_date;
    private String is_del;

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

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
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

    public String getImages() {

        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
