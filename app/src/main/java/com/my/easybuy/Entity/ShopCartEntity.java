package com.my.easybuy.Entity;

import com.avos.avoscloud.AVUser;

/**
 * Created by user999 on 2017/3/1.
 */

public class ShopCartEntity {
    private AVUser user;
    private String url;
    private String des;
    private String price;
    private String number;
    private String objId;
    private String myId;

    public ShopCartEntity(AVUser user, String url, String des, String price, String number, String objId, String myId) {
        this.user = user;
        this.url = url;
        this.des = des;
        this.price = price;
        this.number = number;
        this.objId = objId;
        this.myId=myId;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public AVUser getUser() {
        return user;
    }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
