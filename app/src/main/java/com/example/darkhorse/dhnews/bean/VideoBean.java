package com.example.darkhorse.dhnews.bean;

/**
 * Created by DarkHorse on 2017/12/18.
 */

public class VideoBean {
    private String img;
    private String id;
    private String author_icon;
    private String author_name;
    private String praise;

    public VideoBean(String img, String id, String author_icon, String author_name, String praise) {
        this.img = img;
        this.id = id;
        this.author_icon = author_icon;
        this.author_name = author_name;
        this.praise = praise;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor_icon() {
        return author_icon;
    }

    public void setAuthor_icon(String author_icon) {
        this.author_icon = author_icon;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "img='" + img + '\'' +
                ", id='" + id + '\'' +
                ", author_icon='" + author_icon + '\'' +
                ", author_name='" + author_name + '\'' +
                ", praise='" + praise + '\'' +
                '}';
    }
}
