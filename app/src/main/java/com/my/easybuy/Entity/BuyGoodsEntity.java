package com.my.easybuy.Entity;

import com.avos.avoscloud.AVUser;

/**
 * Created by Administrator on 2017/3/5 0005.
 */

public class BuyGoodsEntity {
    private AVUser user;
    private String url;
    private String des;
    private String price;
    private String address;
    private String number;
    private String phone;
    private String name;
    private String objId;
    private String objectId;
    private String saleName;


    public BuyGoodsEntity(AVUser user, String url, String des, String price, String address, String number, String phone, String name, String objId,String saleName,String objectId) {
        this.user = user;
        this.url = url;
        this.des = des;
        this.price = price;
        this.address = address;
        this.number = number;
        this.phone = phone;
        this.name = name;
        this.objId = objId;
        this.saleName = saleName;
        this.objectId = objectId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
