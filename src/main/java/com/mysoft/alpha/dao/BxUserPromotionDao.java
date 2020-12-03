package com.mysoft.alpha.dao;

import com.mysoft.alpha.entity.BxUserPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * (BxUserPromotion)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-29 14:12:56
 */
public interface BxUserPromotionDao extends JpaRepository<BxUserPromotion, Integer> {

    BxUserPromotion findByUserId(Integer userid);

    List<BxUserPromotion> findAllByUserIdIsNotNull();




}