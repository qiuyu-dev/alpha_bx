package com.mysoft.alpha.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * (BxPromotion)实体类
 *
 * @author makejava
 * @since 2020-11-29 14:13:06
 */
@Entity
@Table(name = "bx_promotion")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class BxPromotion implements Serializable {
    private static final long serialVersionUID = -66309286470850951L;
    /**
     * 推广URL主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 长链接channel
     */
    @Column(name = "channel")
    private String channel;
    /**
     * 渠道
     */
    @Column(name = "ch")
    private String ch;
    /**
     * 产品代码
     */
    @Column(name = "product_code")
    private String productCode;
    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;
    /**
     * 长链接参数goods_code
     */
    @Column(name = "goods_code")
    private String goodsCode;
    /**
     * 长链接fr的编码
     */
    @Column(name = "fr_code")
    private String frCode;
    /**
     * 推广来源序号
     */
    @Column(name = "fr")
    private String fr;
    /**
     * 访问url
     */
    @Column(name = "url")
    private String url;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 状态 1是可用 0 不可用
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 备注用于存来源文件名
     */
    @Column(name = "remark")
    private String remark;

    public BxPromotion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getFrCode() {
        return frCode;
    }

    public void setFrCode(String frCode) {
        this.frCode = frCode;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


	@Override
	public String toString() {
		return "BxPromotion [id=" + id + ", channel=" + channel + ", ch=" + ch + ", productCode=" + productCode
				+ ", productName=" + productName + ", goodsCode=" + goodsCode + ", frCode=" + frCode + ", fr=" + fr
				+ ", url=" + url + ", createTime=" + createTime + ", status=" + status + ", remark=" + remark + "]";
	}
    
    

}