package com.sd.one.utils.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by devin on 17/8/29.
 */
@Entity
public class Product {

    @Id(autoincrement = true)
    private Long productId;

    @Index
    private String porductName;

    private String basePrice;
    private String image;
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
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getBasePrice() {
        return this.basePrice;
    }
    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
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
    @Generated(hash = 1007500402)
    public Product(Long productId, String porductName, String basePrice,
            String image, String desc, String creteTime) {
        this.productId = productId;
        this.porductName = porductName;
        this.basePrice = basePrice;
        this.image = image;
        this.desc = desc;
        this.creteTime = creteTime;
    }
    @Generated(hash = 1890278724)
    public Product() {
    }
}
