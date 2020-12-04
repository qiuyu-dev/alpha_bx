package com.mysoft.alpha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysoft.alpha.dao.BxAchievementDao;
import com.mysoft.alpha.dao.BxUserPromotionDao;
import com.mysoft.alpha.entity.BxUserPromotion;
import com.mysoft.alpha.service.BxAchievementService;


/**
 * (BxAchievement)表服务实现类
 *
 * @author makejava
 * @since 2020-11-29 14:13:14
 */
@Service
public class BxAchievementServiceImpl implements BxAchievementService {
    /**
     * 服务对象
     */
    @Autowired
    private BxAchievementDao bxAchievementDao;

    /**
     * 用户-推广服务对象
     */
    @Autowired
    private BxUserPromotionDao bxUserPromotionDao;


    @Override
    public Long findAmountByUserId(Integer userId) {
        BxUserPromotion bxUserPromotion = bxUserPromotionDao.findByUserId(userId);
        Long amount = new Long("0");
        if(bxUserPromotion !=null) {
            amount = bxAchievementDao.findAmountByParamsAndSort(bxUserPromotion.getPromotionId());           
        }
        return amount;
    }
}