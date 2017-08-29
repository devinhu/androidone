package com.sd.one.utils.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by devin on 17/8/29.
 */
@Entity
public class Order {

    @Id(autoincrement = true)
    private Long orderId;

    @NotNull
    private Long customerId;

    private String name;
    private String finalPrice;
    private String price;
    private String status;
    private String desc;
    private String creteTime;
    public String getCreteTime() {
        return this.creteTime;
    }
    public void setCreteTime(String creteTime) {
        this.creteTime = creteTime;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getFinalPrice() {
        return this.finalPrice;
    }
    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getOrderId() {
        return this.orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    @Generated(hash = 505498130)
    public Order(Long orderId, @NotNull Long customerId, String name,
            String finalPrice, String price, String status, String desc,
            String creteTime) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.name = name;
        this.finalPrice = finalPrice;
        this.price = price;
        this.status = status;
        this.desc = desc;
        this.creteTime = creteTime;
    }
    @Generated(hash = 1105174599)
    public Order() {
    }
}
