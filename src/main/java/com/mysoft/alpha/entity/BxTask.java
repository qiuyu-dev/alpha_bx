package com.mysoft.alpha.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * (BxTask)实体类
 *
 * @author makejava
 * @since 2020-11-29 14:13:00
 */
@Entity
@Table(name = "bx_task")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class BxTask implements Serializable {
    private static final long serialVersionUID = 178035438753601195L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "userid")
    private Integer userid;
    /**
     * 总销售任务需要完成的数量
     */
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

    public BxTask() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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