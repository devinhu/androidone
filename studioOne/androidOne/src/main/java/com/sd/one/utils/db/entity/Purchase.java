package com.sd.one.utils.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by devin on 17/8/29.
 */
@Entity
public class Purchase {

    @Id(autoincrement = true)
    private Long purchaseId;

    @NotNull
    private Long productId;

    @Index
    private String porductName;

    @Index
    private boolean planflag;

    private String basePrice;
    private String price;
    private String image;
    private String desc;
    private String number;
    private String status;
    private String creteTime;
    public String getCreteTime() {
        return this.creteTime;
    }
    public void setCreteTime(String creteTime) {
        this.creteTime = creteTime;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getBasePrice() {
        return this.basePrice;
    }
    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }
    public boolean getPlanflag() {
        return this.planflag;
    }
    public void setPlanflag(boolean planflag) {
        this.planflag = planflag;
    }
    public String getPorductName() {
        return this.porductName;
    }
    public void setPorductName(String porductName) {
        this.porductName = porductName;
    }
    public Long getProductId() {
        return this.productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getPurchaseId() {
        return this.purchaseId;
    }
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }
    @Generated(hash = 1638243775)
    public Purchase(Long purchaseId, @NotNull Long productId, String porductName,
            boolean planflag, String basePrice, String price, String image,
            String desc, String number, String status, String creteTime) {
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.porductName = porductName;
        this.planflag = planflag;
        this.basePrice = basePrice;
        this.price = price;
        this.image = image;
        this.desc = desc;
        this.number = number;
        this.status = status;
        this.creteTime = creteTime;
    }
    @Generated(hash = 1281646125)
    public Purchase() {
    }
}
