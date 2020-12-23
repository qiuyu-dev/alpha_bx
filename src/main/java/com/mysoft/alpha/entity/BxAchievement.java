package com.mysoft.alpha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class BxAchievement implements Serializable {
	private static final long serialVersionUID = 286081696281289041L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "promotion_id")
	private Integer promotionId;
	
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
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 备注，用于存来源文件名
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 曝光人数
	 */
	@Column(name = "exposure_num")
	private Integer exposureNum;
	
	/**
	 * 关注数量
	 */
	@Column(name = "follow_num")
	private Integer followNum;
	
	/**
	 * 保险费
	 */
	@Column(name = "premium")
	private Integer premium;
	
	/**
	 * 统计标识，1为直接投保，2为公众号
	 */
	@Column(name = "flag")
	private Integer flag;
	
	/**
	 * 成单客户
	 */
	@Column(name = "customers")
	private String customers;
	
	public BxAchievement() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
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

	public Integer getExposureNum() {
		return exposureNum;
	}

	public void setExposureNum(Integer exposureNum) {
		this.exposureNum = exposureNum;
	}

	public Integer getFollowNum() {
		return followNum;
	}

	public void setFollowNum(Integer followNum) {
		this.followNum = followNum;
	}

	public Integer getPremium() {
		return premium;
	}

	public void setPremium(Integer premium) {
		this.premium = premium;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getCustomers() {
		return customers;
	}

	public void setCustomers(String customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "BxAchievement [id=" + id + ", promotionId=" + promotionId + ", url=" + url + ", amount=" + amount
				+ ", goodsCode=" + goodsCode + ", createTime=" + createTime + ", remark=" + remark + ", exposureNum="
				+ exposureNum + ", followNum=" + followNum + ", premium=" + premium + ", flag=" + flag + ", customers="
				+ customers + "]";
	}

}