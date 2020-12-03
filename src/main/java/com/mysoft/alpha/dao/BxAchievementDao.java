package com.mysoft.alpha.dao;

import com.mysoft.alpha.entity.BxAchievement;
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

    @Query(value = "SELECT SUM(amount) FROM bx.bx_achievement  WHERE promotionid = :promotionId ",
           nativeQuery = true)
    Long findAmountByParamsAndSort(@Param(value = "promotionId") Integer promotionId);

}