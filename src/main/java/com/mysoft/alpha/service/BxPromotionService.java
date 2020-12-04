package com.mysoft.alpha.service;

import com.mysoft.alpha.entity.BxPromotion;

/**
 * (BxPromotion)表服务接口
 *
 * @author makejava
 * @since 2020-11-29 14:13:08
 */
public interface BxPromotionService {

    BxPromotion findByUserId(Integer userId);

    BxPromotion createByUserId(Integer userId);


}