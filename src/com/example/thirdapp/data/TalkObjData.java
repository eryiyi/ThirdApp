package com.example.thirdapp.data;

import com.example.thirdapp.module.TalkObj;

import java.util.List;

/**
 * Created by Administrator on 2015/11/23.
 */
public class TalkObjData extends Data {
    private List<TalkObj> data;

    public List<TalkObj> getData() {
        return data;
    }

    public void setData(List<TalkObj> data) {
        this.data = data;
    }
}
