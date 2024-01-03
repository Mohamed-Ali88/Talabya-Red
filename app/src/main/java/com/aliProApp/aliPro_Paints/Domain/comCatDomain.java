package com.aliProApp.aliPro_Paints.Domain;

import java.io.Serializable;

public class comCatDomain implements Serializable {
    private String title;
    private String pic;
    private int id;

    public comCatDomain(String title, String pic, int id) {
        this.title = title;
        this.pic = pic;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
