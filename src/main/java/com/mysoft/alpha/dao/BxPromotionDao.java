package com.mysoft.alpha.dao;

import com.mysoft.alpha.entity.BxPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * (BxPromotion)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-29 14:13:07
 */
public interface BxPromotionDao extends JpaRepository<BxPromotion, Integer> {

    BxPromotion findFirstByStatusAndIdNotIn(Integer status, List<Integer> idList);
    BxPromotion findFirstByStatus(Integer status);

}