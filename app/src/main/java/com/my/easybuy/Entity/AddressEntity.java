package com.my.easybuy.Entity;

import com.avos.avoscloud.AVUser;

/**
 * Created by user999 on 2017/2/28.
 */

public class AddressEntity {
    private AVUser user;
    private String address;
    private String phone;
    private String name;

    public AddressEntity(AVUser user, String address, String phone, String name) {
        this.user = user;
        this.address = address;
        this.phone = phone;
        this.name = name;
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

    public AVUser getUser() {
        return user;
    }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
