package com.sd.one.utils.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by devin on 17/8/29.
 */
@Entity
public class Customer {

    @Id(autoincrement = true)
    private Long customerId;

    private String name;
    private String phone;
    private String creteTime;
    public String getCreteTime() {
        return this.creteTime;
    }
    public void setCreteTime(String creteTime) {
        this.creteTime = creteTime;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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
    @Generated(hash = 1137716255)
    public Customer(Long customerId, String name, String phone, String creteTime) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.creteTime = creteTime;
    }
    @Generated(hash = 60841032)
    public Customer() {
    }
}
