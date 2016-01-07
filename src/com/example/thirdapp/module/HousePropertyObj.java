package com.example.thirdapp.module;

/**
 * Created by Administrator on 2015/11/24.
 */
public class HousePropertyObj  {
    private int pic;
    private String title;

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HousePropertyObj(int pic, String title) {
        this.pic = pic;
        this.title = title;
    }
}
