package com.example.thirdapp.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/22.
 *  "id": "1",
 "cat_name": "整套出租",
 "register_date": "0",
 "update_date": "0",
 "is_del": "0"
 */
public class BianminTypeObj implements Serializable {
    private String id;
    private String cat_name;
    private String register_date;
    private String update_date;
    private String is_del;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
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
