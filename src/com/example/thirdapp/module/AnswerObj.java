package com.example.thirdapp.module;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/7.
 * "id": "1",
 "content": "我可以回答你",
 "user_name": "13266816551",
 "nick_name": "Jack",
 "cover": "",
 "community_id": "1",
 "register_date": "0",
 "update_date": "0",
 "is_del": "0",
 "ask_id": "226"
 */
public class AnswerObj  implements Serializable{
    private String id;
    private String content;
    private String user_name;
    private String nick_name;
    private String cover;
    private String community_id;
    private String register_date;
    private String update_date;
    private String is_del;
    private String ask_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getAsk_id() {
        return ask_id;
    }

    public void setAsk_id(String ask_id) {
        this.ask_id = ask_id;
    }
}
