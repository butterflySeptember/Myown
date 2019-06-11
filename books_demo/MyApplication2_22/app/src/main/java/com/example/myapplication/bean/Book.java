package com.example.myapplication.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 图书
 */
public class Book extends LitePalSupport implements Serializable{
    private  int id;
    private String name;//书名
    private String auth;//作者
    private String price;//价格

    //为每个字段名创建get和set方法，并返回该内容。

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
