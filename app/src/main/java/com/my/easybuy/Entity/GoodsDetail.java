package com.my.easybuy.Entity;

import com.avos.avoscloud.AVFile;

/**
 * Created by user999 on 2017/2/27.
 */

public class GoodsDetail {
    private AVFile pic;
    private String des;
    private String price;
    private String type;
    private String objId;
    private String objectId;



    public GoodsDetail(AVFile pic, String des, String price, String type , String objId, String objectId) {
        this.pic = pic;
        this.des = des;
        this.price = price;
        this.type=type;
        this.objId = objId;
        this.objectId = objectId;

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AVFile getPic() {
        return pic;
    }

    public void setPic(AVFile pic) {
        this.pic = pic;
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

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
