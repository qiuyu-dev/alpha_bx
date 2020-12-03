package com.mysoft.alpha.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * (BxAchievement)实体类
 *
 * @author makejava
 * @since 2020-11-29 14:13:11
 */
@Entity
@Table(name = "bx_achievement")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class BxAchievement implements Serializable {
    private static final long serialVersionUID = 286081696281289041L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "promotionid")
    private Integer promotionid;
    @Column(name = "url")
    private String url;
    @Column(name = "amount")
    private Integer amount;
//    @Column(name = "product_code")
//    private String productCode;
    @Column(name = "goods_code")
    private String goodsCode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 备注，用于存来源文件名
     */
    @Column(name = "remark")
    private String remark;

    public BxAchievement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPromotionid() {
        return promotionid;
    }

    public void setPromotionid(Integer promotionid) {
        this.promotionid = promotionid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

//    public String getProductCode() {
//        return productCode;
//    }
//
//    public void setProductCode(String productCode) {
//        this.productCode = productCode;
//    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}