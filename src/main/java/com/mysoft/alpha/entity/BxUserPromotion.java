package com.mysoft.alpha.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * (BxUserPromotion)实体类
 *
 * @author makejava
 * @since 2020-11-29 14:12:55
 */
@Entity
@Table(name = "bx_user_promotion")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class BxUserPromotion implements Serializable {
    private static final long serialVersionUID = 753922110435402215L;
    /**
     * 我方用户-推广URL逐渐
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * 我方用户ID，对多个推广
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * 推广URL对应ID，对一个用户
     */
    @Column(name = "promotion_id")
    private Integer promotionId;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public BxUserPromotion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "BxUserPromotion [id=" + id + ", userId=" + userId + ", promotionId=" + promotionId + ", createTime="
				+ createTime + "]";
	}

}