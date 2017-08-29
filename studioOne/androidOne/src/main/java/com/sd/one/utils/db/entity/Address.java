package com.sd.one.utils.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by devin on 17/8/29.
 */
@Entity
public class Address {

    @Id(autoincrement = true)
    private Long addressId;

    @NotNull
    private Long customerId;

    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String area;
    private String address;
    private String zipcode;
    private String creteTime;
    public String getCreteTime() {
        return this.creteTime;
    }
    public void setCreteTime(String creteTime) {
        this.creteTime = creteTime;
    }
    public String getZipcode() {
        return this.zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getReceiver() {
        return this.receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public Long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getAddressId() {
        return this.addressId;
    }
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    @Generated(hash = 1650495768)
    public Address(Long addressId, @NotNull Long customerId, String receiver,
            String mobile, String province, String city, String area,
            String address, String zipcode, String creteTime) {
        this.addressId = addressId;
        this.customerId = customerId;
        this.receiver = receiver;
        this.mobile = mobile;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.zipcode = zipcode;
        this.creteTime = creteTime;
    }
    @Generated(hash = 388317431)
    public Address() {
    }
}
