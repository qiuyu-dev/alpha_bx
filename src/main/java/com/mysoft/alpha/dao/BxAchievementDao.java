package com.mysoft.alpha.dao;

import com.mysoft.alpha.entity.BxAchievement;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * (BxAchievement)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-29 14:13:12
 */
public interface BxAchievementDao extends JpaRepository<BxAchievement, Integer> {

    @Query(value = "SELECT SUM(amount) FROM bx.bx_achievement  WHERE promotion_id = :promotionId ",
           nativeQuery = true)
    Long findAmountByParamsAndSort(@Param(value = "promotionId") Integer promotionId);
    
    List<BxAchievement> findAllByPromotionId(@Param(value = "promotionId") Integer promotionId);

    @Query(value = "select * from bx_achievement achievement where achievement.promotion_id =:promotionId"	, 
            countQuery = "select count(*) from bx_achievement achievement where achievement.promotion_id =:promotionId",
            nativeQuery = true)
    Page<BxAchievement> findPageByPromotionId(@Param(value = "promotionId") Integer promotionId, Pageable pageable);
    
}