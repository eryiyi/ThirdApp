package com.example.thirdapp.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/7.
 * "id": "226",
 "content": "我有一个疑问",
 "user_name": "13266816551",
 "nick_name": "Marks",
 "cover": "",
 "community_id": "1",
 "reply_num": "0",
 "best_reply_id": "0",
 "register_date": "0",
 "update_date": "0",
 "is_del": "0",
 "answer_data": [


 "id": "226",
 "content": "我有一个疑问",
 "user_name": "13266816551",
 "nick_name": "Marks",
 "cover": "",
 "community_id": "1",
 "reply_num": "2",
 "best_reply_id": "0",
 "register_date": "0",
 "update_date": "0",
 "is_del": "0",
 "support_num": "0"
 */
public class StudyObj implements Serializable{
    private String id;
    private String content;
    private String user_name;
    private String nick_name;
    private String cover;
    private String community_id;
    private String reply_num;
    private String best_reply_id;
    private String register_date;
    private String update_date;
    private String is_del;
    private String support_num;
    private List<AnswerObj> answer_data;

    public String getSupport_num() {
        return support_num;
    }

    public void setSupport_num(String support_num) {
        this.support_num = support_num;
    }

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

    public String getReply_num() {
        return reply_num;
    }

    public void setReply_num(String reply_num) {
        this.reply_num = reply_num;
    }

    public String getBest_reply_id() {
        return best_reply_id;
    }

    public void setBest_reply_id(String best_reply_id) {
        this.best_reply_id = best_reply_id;
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

    public List<AnswerObj> getAnswer_data() {
        return answer_data;
    }

    public void setAnswer_data(List<AnswerObj> answer_data) {
        this.answer_data = answer_data;
    }
}
