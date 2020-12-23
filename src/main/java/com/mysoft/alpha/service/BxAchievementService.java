package com.mysoft.alpha.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mysoft.alpha.entity.BxAchievement;

/**
 * (BxAchievement)表服务接口
 * 保险业绩
 * @author makejava
 * @since 2020-11-29 14:13:13
 */
public interface BxAchievementService {
    Long findAmountByUserId(Integer userId);
    
    List<BxAchievement> findAllByPromotionId(Integer promotionId);
    
    Page<BxAchievement> findPageByPromotionId(Integer promotionId, Pageable pageable);

}