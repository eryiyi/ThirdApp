package com.example.thirdapp.data;

import com.example.thirdapp.module.StudyObj;

import java.util.List;

/**
 * Created by Administrator on 2015/11/7.
 */
public class StudyObjData extends Data {
    private List<StudyObj> data;

    public List<StudyObj> getData() {
        return data;
    }

    public void setData(List<StudyObj> data) {
        this.data = data;
    }
}
