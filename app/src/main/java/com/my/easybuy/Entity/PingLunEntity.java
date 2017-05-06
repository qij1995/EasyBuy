package com.my.easybuy.Entity;

import com.avos.avoscloud.AVUser;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class PingLunEntity {
    private String objId;
    private AVUser user;
    private String content;
    private String saleName;
    private String saleContent;
    private Date createDate;

    public PingLunEntity(String objId, AVUser user, String content, String saleName, String saleContent,Date createDate) {
        this.objId = objId;
        this.user = user;
        this.content = content;
        this.saleName = saleName;
        this.saleContent = saleContent;
        this.createDate = createDate;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public AVUser getUser() {
        return user;
    }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleContent() {
        return saleContent;
    }

    public void setSaleContent(String saleContent) {
        this.saleContent = saleContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
